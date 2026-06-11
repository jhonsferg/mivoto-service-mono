package pe.com.mivoto.service.application.services;

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

import java.time.LocalDateTime;
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
    private AuditService auditService;
    @Mock
    private ElectionGraph electionGraph;

    @InjectMocks
    private ElectionManagementService electionManagementService;

    @Test
    @DisplayName("Should return election from DB")
    void testGetElectionById_ReturnsFromDB() {
        Long electionId = 1L;
        Election dbElection = Election.builder().id(electionId).title("DB Election").build();

        when(electionRepository.findById(electionId)).thenReturn(Optional.of(dbElection));

        Election result = electionManagementService.getElectionById(electionId);

        assertNotNull(result);
        assertEquals(dbElection, result);
        verify(electionRepository).findById(electionId);
    }

    @Test
    @DisplayName("Should update election and persist changes")
    void testUpdateElection_PersistsChanges() {
        Long electionId = 1L;
        Election updates = Election.builder().title("Updated Title").build();
        Election existing = Election.builder()
                .id(electionId)
                .status(ElectionStatus.DRAFT)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(2))
                .build();
        Election updated = Election.builder().id(electionId).title("Updated Title").status(ElectionStatus.DRAFT)
                .build();

        when(electionRepository.findById(electionId)).thenReturn(Optional.of(existing));
        when(electionRepository.update(any(Election.class))).thenReturn(updated);

        electionManagementService.updateElection(electionId, updates);

        verify(electionRepository).update(any(Election.class));
    }
}
