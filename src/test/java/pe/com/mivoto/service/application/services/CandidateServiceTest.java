package pe.com.mivoto.service.application.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.datastructures.implementations.CandidateSearchTree;
import pe.com.mivoto.service.domain.model.Candidate;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.infrastructure.persistence.redis.CandidateCacheRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private ElectionRepository electionRepository;
    @Mock
    private CandidateCacheRepository candidateCacheRepository;
    @Mock
    private AuditService auditService;
    @Mock
    private CandidateSearchTree candidateSearchTree;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    @DisplayName("Should return candidates from cache if available")
    void testGetCandidatesByElection_CacheHit() {
        Long electionId = 1L;
        List<Candidate> cachedList = Collections
                .singletonList(Candidate.builder().id(10L).electionId(electionId).build());

        when(candidateCacheRepository.findByElection(electionId)).thenReturn(Optional.of(cachedList));

        List<Candidate> result = candidateService.getCandidatesByElection(electionId);

        assertNotNull(result);
        assertEquals(cachedList, result);
        verify(candidateCacheRepository).findByElection(electionId);
        verify(candidateRepository, never()).findByElectionId(any());
    }

    @Test
    @DisplayName("Should fetch candidates from DB and cache if not in cache")
    void testGetCandidatesByElection_CacheMiss() {
        Long electionId = 1L;
        List<Candidate> dbList = Collections.singletonList(Candidate.builder().id(10L).electionId(electionId).build());

        when(candidateCacheRepository.findByElection(electionId)).thenReturn(Optional.empty());
        when(candidateRepository.findByElectionId(electionId)).thenReturn(dbList);

        List<Candidate> result = candidateService.getCandidatesByElection(electionId);

        assertNotNull(result);
        assertEquals(dbList, result);
        verify(candidateCacheRepository).findByElection(electionId);
        verify(candidateRepository).findByElectionId(electionId);
        verify(candidateCacheRepository).saveByElection(electionId, dbList);
    }

    @Test
    @DisplayName("Should invalidate cache on register candidate")
    void testRegisterCandidate_InvalidatesCache() {
        Long electionId = 1L;
        Candidate candidate = Candidate.builder().electionId(electionId).number(1).build();
        Election election = Election.builder()
                .id(electionId)
                .status(pe.com.mivoto.service.domain.enums.ElectionStatus.DRAFT)
                .startDate(java.time.LocalDateTime.now().plusDays(1))
                .endDate(java.time.LocalDateTime.now().plusDays(2))
                .build();

        when(electionRepository.findById(electionId)).thenReturn(Optional.of(election));
        when(candidateRepository.save(candidate))
                .thenReturn(Candidate.builder().id(100L).electionId(electionId).build());

        candidateService.registerCandidate(candidate);

        verify(candidateCacheRepository).deleteByElection(electionId);
    }
}
