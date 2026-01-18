package pe.com.mivoto.service.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.domain.model.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Component for generating and validating JWT tokens.
 * Handles token creation, parsing, and validation using a secret key.
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:28800000}")
    private long jwtExpirationMs;

    /**
     * Generates a JWT token for an authenticated user.
     * Contains user ID, email, role, and document number as claims.
     *
     * @param user The user to generate the token for.
     * @return The generated JWT token string.
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("documentNumber", user.getDocumentNumber())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token The JWT token.
     * @return The user ID.
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Extracts the email from a JWT token.
     *
     * @param token The JWT token.
     * @return The email address.
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    /**
     * Extracts the user role from a JWT token.
     *
     * @param token The JWT token.
     * @return The user role name.
     */
    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("role", String.class);
    }

    /**
     * Validates a JWT token.
     * Checks signature, expiration, and format.
     *
     * @param authToken The JWT token string.
     * @return true if valid, false otherwise.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException ex) {
            log.error("JWT signature inválida");
        } catch (MalformedJwtException ex) {
            log.error("JWT token inválido");
        } catch (ExpiredJwtException ex) {
            log.error("JWT token expirado");
        } catch (UnsupportedJwtException ex) {
            log.error("JWT token no soportado");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string vacío");
        }
        return false;
    }

    /**
     * Generates the signing key for JWT from the configured secret.
     *
     * @return The SecretKey object.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
