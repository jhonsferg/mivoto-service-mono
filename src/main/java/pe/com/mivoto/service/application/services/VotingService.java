package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.mivoto.service.datastructures.implementations.VoteQueue;
import pe.com.mivoto.service.datastructures.implementations.VoteRecordList;
import pe.com.mivoto.service.domain.enums.VoteStatus;
import pe.com.mivoto.service.domain.exceptions.DuplicateVoteException;
import pe.com.mivoto.service.domain.exceptions.InvalidElectionException;
import pe.com.mivoto.service.domain.model.*;
import pe.com.mivoto.service.domain.ports.in.VotingUseCase;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Application service for voting operations.
 * Implements {@link VotingUseCase} to handle vote casting, verification, and
 * retrieval.
 * Uses {@link VoteQueue} for asynchronous processing and {@link VoteRecordList}
 * for history.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VotingService implements VotingUseCase {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    private final VoteQueue voteQueue;
    private final VoteRecordList voteRecordList;

    /**
     * Casts a vote for a candidate.
     * Delegates to {@link #castVote(Long, Long, Long, String, String)} with null
     * IP/User-Agent.
     *
     * @param userId      The user ID.
     * @param electionId  The election ID.
     * @param candidateId The candidate ID.
     * @return The created Vote.
     */
    @Override
    public Vote castVote(Long userId, Long electionId, Long candidateId) {
        return castVote(userId, electionId, candidateId, null, null);
    }

    /**
     * Casts a vote with full context (IP, User-Agent).
     * Validates eligibility, creates vote, enqueues for processing, and maintains
     * audit trail.
     *
     * @param userId      The user ID.
     * @param electionId  The election ID.
     * @param candidateId The candidate ID.
     * @param ipAddress   The IP address.
     * @param userAgent   The User-Agent string.
     * @return The processed Vote.
     * @throws pe.com.mivoto.service.domain.exceptions.InvalidElectionException if
     *                                                                          validation
     *                                                                          fails.
     * @throws pe.com.mivoto.service.domain.exceptions.DuplicateVoteException   if
     *                                                                          user
     *                                                                          already
     *                                                                          voted.
     */
    @Transactional
    public Vote castVote(Long userId, Long electionId, Long candidateId, String ipAddress, String userAgent) {
        log.info("Procesando voto - Usuario: {}, Elección: {}, Candidato: {}", userId, electionId, candidateId);

        validateVoteRequest(userId, electionId, candidateId);

        Vote vote = Vote.builder()
                .userId(userId)
                .electionId(electionId)
                .candidateId(candidateId)
                .status(VoteStatus.PENDING)
                .verified(false)
                .votedAt(LocalDateTime.now())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
        vote.setVoteHash(generateVoteHash(vote));
        this.voteQueue.enqueueVote(vote);
        Vote processedVote = processVote(vote);
        VoteRecord record = this.voteRecordList.createAndAddRecord(
                processedVote.getId(),
                userId,
                electionId,
                processedVote.getVoteHash());

        this.voteRepository.saveVoteRecord(record);
        this.auditService.logVoteCast(userId, electionId, candidateId);

        log.info("Voto procesado exitosamente - ID: {}, Hash: {}", processedVote.getId(), processedVote.getVoteHash());

        return processedVote;
    }

    /**
     * Internal validation for a vote request. Checks for user existence,
     * authorization,
     * election state, candidate validity, and prevents double voting.
     *
     * @param userId      The user ID.
     * @param electionId  The election ID.
     * @param candidateId The candidate ID.
     * @throws InvalidElectionException if any business rule is violated.
     * @throws DuplicateVoteException   if the user has already voted.
     */
    private void validateVoteRequest(Long userId, Long electionId, Long candidateId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new InvalidElectionException("Usuario no encontrado"));

        if (!user.canVote()) {
            throw new InvalidElectionException("Usuario no autorizado para votar");
        }

        Election election = this.electionRepository.findById(electionId)
                .orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));

        if (!election.canVote()) {
            throw new InvalidElectionException("La elección no está activa");
        }

        Candidate candidate = this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new InvalidElectionException("Candidato no encontrado"));

        if (!candidate.canReceiveVotes()) {
            throw new InvalidElectionException("El candidato no está activo");
        }

        if (!candidate.getElectionId().equals(electionId)) {
            throw new InvalidElectionException("El candidato no pertenece a esta elección");
        }

        if (this.voteRepository.existsByUserIdAndElectionId(userId, electionId)) {
            throw new DuplicateVoteException(userId, electionId);
        }
    }

    /**
     * Internal method to process a vote persistence. Saves the vote, confirms it,
     * and increments the candidate's vote count.
     *
     * @param vote The vote object to process.
     * @return The updated and confirmed Vote.
     */
    public Vote processVote(Vote vote) {
        Vote savedVote = this.voteRepository.save(vote);
        savedVote.confirm();

        Candidate candidate = this.candidateRepository.findById(vote.getCandidateId()).orElseThrow();
        candidate.incrementVoteCount();
        this.candidateRepository.update(candidate);

        return this.voteRepository.update(savedVote);
    }

    /**
     * Verifies vote integrity by hash.
     * Looks up in in-memory list first, then database.
     *
     * @param voteHash The vote hash.
     * @return The VoteRecord.
     * @throws InvalidElectionException if not found.
     */
    public VoteRecord verifyVote(String voteHash) {
        log.info("Verificando voto con hash: {}", voteHash);

        VoteRecord record = this.voteRecordList.findByHash(voteHash);

        if (record == null) {
            record = this.voteRepository.findVoteRecordByHash(voteHash)
                    .orElseThrow(() -> new InvalidElectionException("Voto no encontrado"));
        }

        this.auditService.logVoteVerification(record.getVoteId(), voteHash);

        return record;
    }

    /**
     * Checks if a user has cast a vote in a specific election.
     *
     * @param userId     The user ID.
     * @param electionId The election ID.
     * @return true if the user has voted.
     */
    public boolean hasVoted(Long userId, Long electionId) {
        return this.voteRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    /**
     * Retrieves the voting history for a user, checking both the in-memory
     * record list and the database repository.
     *
     * @param userId The user ID.
     * @return List of Vote Records for the user.
     */
    public List<VoteRecord> getVotingHistory(Long userId) {
        log.info("Obteniendo historial de votos para usuario: {}", userId);

        List<VoteRecord> records = this.voteRecordList.findByUser(userId);

        if (records.isEmpty()) {
            records = this.voteRepository.findVoteRecordsByUserId(userId);
        }

        return records;
    }

    /**
     * Retrieves all votes cast for a given election.
     *
     * @param electionId The election ID.
     * @return List of all votes in the election.
     */
    public List<Vote> getVotesByElection(Long electionId) {
        return this.voteRepository.findByElectionId(electionId);
    }

    /**
     * Retrieves the total count of votes received by a specific candidate.
     *
     * @param candidateId The candidate ID.
     * @return Total count of votes.
     */
    public Long countVotesByCandidate(Long candidateId) {
        return this.voteRepository.countByCandidateId(candidateId);
    }

    /**
     * Invalidates a vote (admin/system action).
     * Reverts vote count and status.
     *
     * @param voteId The vote ID.
     * @param reason The reason.
     */
    @Transactional
    public void invalidateVote(Long voteId, String reason) {
        log.warn("Invalidando voto: {} - Razón: {}", voteId, reason);

        Vote vote = this.voteRepository.findById(voteId)
                .orElseThrow(() -> new InvalidElectionException("Voto no encontrado"));

        vote.reject();
        this.voteRepository.update(vote);
        Candidate candidate = this.candidateRepository.findById(vote.getCandidateId()).orElseThrow();
        candidate.setVoteCount(candidate.getVoteCount() - 1);
        this.candidateRepository.update(candidate);
        this.auditService.logVoteInvalidation(voteId, reason);
    }

    /**
     * Generates a cryptographic hash for identifying a vote, ensuring its
     * integrity.
     *
     * @param vote The vote to hash.
     * @return Hexadecimal SHA-256 hash.
     */
    private String generateVoteHash(Vote vote) {
        String data = String.format("%d-%d-%d-%d",
                vote.getUserId(),
                vote.getElectionId(),
                vote.getCandidateId(),
                System.currentTimeMillis());

        return DigestUtils.sha256Hex(data);
    }

    /**
     * Background processing for vote queue.
     * Processes buffered votes.
     */
    @Transactional
    public void processPendingVotes() {
        log.info("Procesando votos pendientes en cola");

        int processed = 0;
        while (!this.voteQueue.isEmpty()) {
            Vote vote = this.voteQueue.dequeueVote();
            if (vote != null) {
                processVote(vote);
                processed++;
            }
        }

        log.info("Procesados {} votos de la cola", processed);
    }
}
