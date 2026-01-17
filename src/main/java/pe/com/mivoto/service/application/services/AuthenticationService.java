package pe.com.mivoto.service.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.mivoto.service.domain.exceptions.AuthenticationException;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.domain.model.VotingSession;
import pe.com.mivoto.service.domain.ports.out.SessionRepository;
import pe.com.mivoto.service.domain.ports.out.UserRepository;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int SESSION_EXPIRATION_HOURS = 8;
    private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 30;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuditService auditService;

    @Transactional
    public VotingSession authenticate(String username, String password, String ipAddress, String userAgent) {
        log.info("Intentando autenticar usuario: {}", username);

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado: {}", username);
                    this.auditService.logFailedLogin(username, ipAddress);
                    return new AuthenticationException("Credenciales inválidas");
                });

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Contraseña incorrecta para usuario: {}", username);
            this.auditService.logFailedLogin(username, ipAddress);
            throw new AuthenticationException("Credenciales inválidas");
        }

        if (!user.getActive()) {
            log.warn("Usuario inactivo: {}", username);
            throw new AuthenticationException("Usuario inactivo");
        }

        user.updateLastLogin();
        this.userRepository.update(user);
        VotingSession session = createSession(user, ipAddress, userAgent);
        this.auditService.logSuccessfulLogin(user.getId(), ipAddress);

        log.info("Usuario autenticado exitosamente: {}", username);
        return session;
    }

    private VotingSession createSession(User user, String ipAddress, String userAgent) {
        String accessToken = this.jwtTokenProvider.generateToken(user);
        String refreshToken = UUID.randomUUID().toString();

        VotingSession session = VotingSession.builder()
                .userId(user.getId())
                .sessionToken(accessToken)
                .refreshToken(refreshToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(SESSION_EXPIRATION_HOURS))
                .lastAccessedAt(LocalDateTime.now())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .active(true)
                .build();

        return this.sessionRepository.save(session);
    }

    @Transactional
    public void logout(String sessionToken) {
        log.info("Cerrando sesión");

        VotingSession session = this.sessionRepository.findBySessionToken(sessionToken).orElseThrow(() -> new AuthenticationException("Sesión no encontrada"));

        session.invalidate();
        this.sessionRepository.update(session);
        this.auditService.logLogout(session.getUserId());

        log.info("Sesión cerrada para usuario: {}", session.getUserId());
    }

    @Transactional
    public VotingSession refreshToken(String refreshToken) {
        log.info("Refrescando token");

        VotingSession session = this.sessionRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new AuthenticationException("Refresh token inválido"));

        if (!session.isValid()) {
            throw new AuthenticationException("Sesión expirada");
        }

        User user = this.userRepository.findById(session.getUserId()).orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));
        String newAccessToken = this.jwtTokenProvider.generateToken(user);
        session.setSessionToken(newAccessToken);
        session.setExpiresAt(LocalDateTime.now().plusHours(SESSION_EXPIRATION_HOURS));
        session.updateAccess();
        return this.sessionRepository.update(session);
    }

    public boolean validateToken(String sessionToken) {
        try {
            if (!this.jwtTokenProvider.validateToken(sessionToken)) {
                return false;
            }

            return this.sessionRepository.existsActiveBySessionToken(sessionToken);

        } catch (Exception e) {
            log.error("Error validando token", e);
            return false;
        }
    }

    public User getUserFromToken(String sessionToken) {
        Long userId = this.jwtTokenProvider.getUserIdFromToken(sessionToken);
        return this.userRepository.findById(userId).orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));
    }

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        log.info("Cambiando contraseña para usuario: {}", userId);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AuthenticationException("Contraseña actual incorrecta");
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        this.userRepository.update(user);
        this.sessionRepository.invalidateAllUserSessions(userId);
        this.auditService.logPasswordChange(userId);

        log.info("Contraseña cambiada exitosamente para usuario: {}", userId);
    }

    public boolean isAuthenticated(Long userId) {
        return this.sessionRepository.countActiveByUserId(userId) > 0;
    }
}
