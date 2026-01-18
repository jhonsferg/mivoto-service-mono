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

/**
 * Application service for user authentication and session management.
 * Implements {@link pe.com.mivoto.service.domain.ports.in.AuthUseCase} to
 * handle login flows,
 * token generation, session validation, and security operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements pe.com.mivoto.service.domain.ports.in.AuthUseCase {
    private static final int SESSION_EXPIRATION_HOURS = 8;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuditService auditService;

    /**
     * Authenticates a user and creates a new voting session.
     *
     * @param username  The user's username.
     * @param password  The raw password.
     * @param ipAddress The source IP address.
     * @param userAgent The client User-Agent.
     * @return The active VotingSession.
     * @throws AuthenticationException if credentials are invalid or user is
     *                                 inactive.
     */
    @Transactional
    /**
     * Authenticates a user with username and password.
     * Delegates to {@link #authenticate(String, String, String, String)} with null
     * IP/User-Agent.
     *
     * @param username The username.
     * @param password The password.
     * @return The active VotingSession.
     */
    @Override
    public VotingSession login(String username, String password) {
        return authenticate(username, password, null, null);
    }

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

    /**
     * Internal method to create and persist a new voting session.
     * Generates JWT access token and a random UUID refresh token.
     *
     * @param user      The authenticated user.
     * @param ipAddress The source IP address.
     * @param userAgent The client user agent.
     * @return The persisted VotingSession.
     */
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

    /**
     * Terminates a user session.
     *
     * @param sessionToken The session token to invalidate.
     */
    @Transactional
    public void logout(String sessionToken) {
        log.info("Cerrando sesión");

        VotingSession session = this.sessionRepository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new AuthenticationException("Sesión no encontrada"));

        session.invalidate();
        this.sessionRepository.update(session);
        this.auditService.logLogout(session.getUserId());

        log.info("Sesión cerrada para usuario: {}", session.getUserId());
    }

    /**
     * Refreshes an expired access token using a valid refresh token.
     *
     * @param refreshToken The refresh token.
     * @return The session with updated tokens.
     */
    @Transactional
    public VotingSession refreshToken(String refreshToken) {
        log.info("Refrescando token");

        VotingSession session = this.sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException("Refresh token inválido"));

        if (!session.isValid()) {
            throw new AuthenticationException("Sesión expirada");
        }

        User user = this.userRepository.findById(session.getUserId())
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));
        String newAccessToken = this.jwtTokenProvider.generateToken(user);
        session.setSessionToken(newAccessToken);
        session.setExpiresAt(LocalDateTime.now().plusHours(SESSION_EXPIRATION_HOURS));
        session.updateAccess();
        return this.sessionRepository.update(session);
    }

    /**
     * Validates if a session token is currently active.
     *
     * @param sessionToken The token to validate.
     * @return true if valid and active, false otherwise.
     */
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

    /**
     * Retrieves the user associated with a session token.
     *
     * @param sessionToken The session token.
     * @return The User object.
     */
    public User getUserFromToken(String sessionToken) {
        Long userId = this.jwtTokenProvider.getUserIdFromToken(sessionToken);
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));
    }

    /**
     * Updates a user's password.
     *
     * @param userId      The user ID.
     * @param oldPassword The current password.
     * @param newPassword The new password.
     */
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        log.info("Cambiando contraseña para usuario: {}", userId);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

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

    /**
     * Checks if a user has any active sessions.
     *
     * @param userId The user ID.
     * @return true if authenticated, false otherwise.
     */
    public boolean isAuthenticated(Long userId) {
        return this.sessionRepository.countActiveByUserId(userId) > 0;
    }
}
