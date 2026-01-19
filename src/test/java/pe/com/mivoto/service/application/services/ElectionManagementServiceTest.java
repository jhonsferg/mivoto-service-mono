package pe.com.mivoto.service.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.datastructures.implementations.ElectionGraph;
import pe.com.mivoto.service.domain.enums.ElectionStatus;
import pe.com.mivoto.service.domain.model.Election;
import pe.com.mivoto.service.domain.ports.out.CandidateRepository;
import pe.com.mivoto.service.domain.ports.out.ElectionRepository;
import pe.com.mivoto.service.domain.ports.out.VoteRepository;
import pe.com.mivoto.service.infrastructure.persistence.redis.ElectionCacheRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionManagementServiceTest {

    @Mock
    private ElectionRepository electionRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private ElectionCacheRepository electionCacheRepository;
    @Mock
    private AuditService auditService;
    @Mock
    private ElectionGraph electionGraph;

    @InjectMocks
    private ElectionManagementService electionManagementService;

    @Test
    @DisplayName("Should return election from cache if available")
    void testGetElectionById_CacheHit() {
        Long electionId = 1L;
        Election cachedElection = Election.builder().id(electionId).title("Cached Election").build();

        when(electionCacheRepository.findById(electionId)).thenReturn(Optional.of(cachedElection));

        Election result = electionManagementService.getElectionById(electionId);

        assertNotNull(result);
        assertEquals(cachedElection, result);
        verify(electionCacheRepository).findById(electionId);
        verify(electionRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should fetch from DB and cache if not in cache")
    void testGetElectionById_CacheMiss() {
        Long electionId = 1L;
        Election dbElection = Election.builder().id(electionId).title("DB Election").build();

        when(electionCacheRepository.findById(electionId)).thenReturn(Optional.empty());
        when(electionRepository.findById(electionId)).thenReturn(Optional.of(dbElection));

        Election result = electionManagementService.getElectionById(electionId);

        assertNotNull(result);
        assertEquals(dbElection, result);
        verify(electionCacheRepository).findById(electionId);
        verify(electionRepository).findById(electionId);
        verify(electionCacheRepository).save(dbElection);
    }

    @Test
    @DisplayName("Should invalidate cache on update")
    void testUpdateElection_InvalidatesCache() {
        Long electionId = 1L;
        Election updates = Election.builder().title("Updated Title").build();
        Election existing = Election.builder()
                .id(electionId)
                .status(ElectionStatus.DRAFT)
                .startDate(java.time.LocalDateTime.now().plusDays(1))
                .endDate(java.time.LocalDateTime.now().plusDays(2))
                .build();
        Election updated = Election.builder().id(electionId).title("Updated Title").status(ElectionStatus.DRAFT)
                .build();

        when(electionRepository.findById(electionId)).thenReturn(Optional.of(existing));
        when(electionRepository.update(any(Election.class))).thenReturn(updated);

        electionManagementService.updateElection(electionId, updates);

        verify(electionCacheRepository).save(updated);
        verify(electionCacheRepository).deleteActiveList();
    }
}
