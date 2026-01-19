package pe.com.mivoto.service.application.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.mivoto.service.application.services.AuditService;
import pe.com.mivoto.service.application.usecases.audit.CreateAuditLogUseCaseImpl;
import pe.com.mivoto.service.application.usecases.audit.GetAuditTrailUseCaseImpl;
import pe.com.mivoto.service.domain.enums.AuditAction;
import pe.com.mivoto.service.domain.model.AuditLog;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditUseCasesTest {

    @Mock
    private AuditService auditService;

    private CreateAuditLogUseCaseImpl createAuditLogUseCase;
    private GetAuditTrailUseCaseImpl getAuditTrailUseCase;

    @BeforeEach
    void setUp() {
        createAuditLogUseCase = new CreateAuditLogUseCaseImpl(auditService);
        getAuditTrailUseCase = new GetAuditTrailUseCaseImpl(auditService);
    }

    @Test
    @DisplayName("Should create audit log")
    void testCreateAuditLog() {
        Long userId = 1L;
        AuditAction action = AuditAction.LOGIN;
        String entity = "User";
        Long entityId = 1L;
        String desc = "Desc";
        String ip = "127.0.0.1";
        String agent = "Agent";
        Map<String, Object> meta = Collections.emptyMap();

        AuditLog expected = AuditLog.builder().id(100L).build();
        when(auditService.logAction(userId, action, entity, entityId, desc, ip, agent, meta)).thenReturn(expected);

        AuditLog result = createAuditLogUseCase.execute(userId, action, entity, entityId, desc, ip, agent, meta);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(auditService).logAction(userId, action, entity, entityId, desc, ip, agent, meta);
    }

    @Test
    @DisplayName("Should get audit trail by user")
    void testGetByUser() {
        Long userId = 1L;
        List<AuditLog> expected = Collections.singletonList(new AuditLog());
        when(auditService.getAuditTrailByUser(userId)).thenReturn(expected);

        List<AuditLog> result = getAuditTrailUseCase.executeByUser(userId);

        assertEquals(expected, result);
        verify(auditService).getAuditTrailByUser(userId);
    }

    @Test
    @DisplayName("Should get audit trail by entity")
    void testGetByEntity() {
        String entity = "E";
        Long entityId = 2L;
        List<AuditLog> expected = Collections.singletonList(new AuditLog());
        when(auditService.getAuditTrailByEntity(entity, entityId)).thenReturn(expected);

        List<AuditLog> result = getAuditTrailUseCase.executeByEntity(entity, entityId);

        assertEquals(expected, result);
        verify(auditService).getAuditTrailByEntity(entity, entityId);
    }

    @Test
    @DisplayName("Should get audit trail by action")
    void testGetByAction() {
        AuditAction action = AuditAction.LOGIN;
        List<AuditLog> expected = Collections.singletonList(new AuditLog());
        when(auditService.getAuditTrailByAction(action)).thenReturn(expected);

        List<AuditLog> result = getAuditTrailUseCase.executeByAction(action);

        assertEquals(expected, result);
        verify(auditService).getAuditTrailByAction(action);
    }

    @Test
    @DisplayName("Should get audit trail by date range")
    void testGetByDateRange() {
        LocalDateTime now = LocalDateTime.now();
        List<AuditLog> expected = Collections.singletonList(new AuditLog());
        when(auditService.getAuditTrailByDateRange(now, now)).thenReturn(expected);

        List<AuditLog> result = getAuditTrailUseCase.executeByDateRange(now, now);

        assertEquals(expected, result);
        verify(auditService).getAuditTrailByDateRange(now, now);
    }

    @Test
    @DisplayName("Should get critical logs")
    void testGetCritical() {
        List<AuditLog> expected = Collections.singletonList(new AuditLog());
        when(auditService.getCriticalAuditLogs()).thenReturn(expected);

        List<AuditLog> result = getAuditTrailUseCase.executeCriticalLogs();

        assertEquals(expected, result);
        verify(auditService).getCriticalAuditLogs();
    }
}
