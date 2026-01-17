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
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingService {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    private final VoteQueue voteQueue;
    private final VoteRecordList voteRecordList;

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
                processedVote.getVoteHash()
        );

        this.voteRepository.saveVoteRecord(record);
        this.auditService.logVoteCast(userId, electionId, candidateId);

        log.info("Voto procesado exitosamente - ID: {}, Hash: {}", processedVote.getId(), processedVote.getVoteHash());

        return processedVote;
    }

    private void validateVoteRequest(Long userId, Long electionId, Long candidateId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidElectionException("Usuario no encontrado"));

        if (!user.canVote()) {
            throw new InvalidElectionException("Usuario no autorizado para votar");
        }

        Election election = this.electionRepository.findById(electionId).orElseThrow(() -> new InvalidElectionException("Elección no encontrada"));

        if (!election.canVote()) {
            throw new InvalidElectionException("La elección no está activa");
        }

        Candidate candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> new InvalidElectionException("Candidato no encontrado"));

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

    private Vote processVote(Vote vote) {
        Vote savedVote = this.voteRepository.save(vote);
        savedVote.confirm();

        Candidate candidate = this.candidateRepository.findById(vote.getCandidateId()).orElseThrow();
        candidate.incrementVoteCount();
        this.candidateRepository.update(candidate);

        return this.voteRepository.update(savedVote);
    }

    public VoteRecord verifyVote(String voteHash) {
        log.info("Verificando voto con hash: {}", voteHash);

        VoteRecord record = this.voteRecordList.findByHash(voteHash);

        if (record == null) {
            record = this.voteRepository.findVoteRecordByHash(voteHash).orElseThrow(() -> new InvalidElectionException("Voto no encontrado"));
        }

        this.auditService.logVoteVerification(record.getVoteId(), voteHash);

        return record;
    }

    public boolean hasVoted(Long userId, Long electionId) {
        return this.voteRepository.existsByUserIdAndElectionId(userId, electionId);
    }

    public List<VoteRecord> getVotingHistory(Long userId) {
        log.info("Obteniendo historial de votos para usuario: {}", userId);

        List<VoteRecord> records = this.voteRecordList.findByUser(userId);

        if (records.isEmpty()) {
            records = this.voteRepository.findVoteRecordsByUserId(userId);
        }

        return records;
    }

    public List<Vote> getVotesByElection(Long electionId) {
        return this.voteRepository.findByElectionId(electionId);
    }

    public Long countVotesByCandidate(Long candidateId) {
        return this.voteRepository.countByCandidateId(candidateId);
    }

    @Transactional
    public void invalidateVote(Long voteId, String reason) {
        log.warn("Invalidando voto: {} - Razón: {}", voteId, reason);

        Vote vote = this.voteRepository.findById(voteId).orElseThrow(() -> new InvalidElectionException("Voto no encontrado"));

        vote.reject();
        this.voteRepository.update(vote);
        Candidate candidate = this.candidateRepository.findById(vote.getCandidateId()).orElseThrow();
        candidate.setVoteCount(candidate.getVoteCount() - 1);
        this.candidateRepository.update(candidate);
        this.auditService.logVoteInvalidation(voteId, reason);
    }

    private String generateVoteHash(Vote vote) {
        String data = String.format("%d-%d-%d-%d",
                vote.getUserId(),
                vote.getElectionId(),
                vote.getCandidateId(),
                System.currentTimeMillis()
        );

        return DigestUtils.sha256Hex(data);
    }

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
