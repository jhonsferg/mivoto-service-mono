package pe.com.mivoto.service.domain.ports.in;

import pe.com.mivoto.service.domain.model.Candidate;

import java.util.List;

public interface CandidateUseCase {
    Candidate registerCandidate(Candidate candidate);

    Candidate updateCandidate(Long candidateId, Candidate candidate);

    Candidate getCandidateById(Long candidateId);

    List<Candidate> getCandidatesByElection(Long electionId);

    Candidate findCandidateByNumber(Long electionId, Integer number);

    void activateCandidate(Long candidateId);

    void deactivateCandidate(Long candidateId);

    void deleteCandidate(Long candidateId);

    List<Candidate> getActiveCandidates(Long electionId);

    List<Candidate> findCandidatesByParty(String party);
}
