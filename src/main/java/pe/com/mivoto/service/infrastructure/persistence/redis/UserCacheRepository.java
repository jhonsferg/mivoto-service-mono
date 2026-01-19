package pe.com.mivoto.service.infrastructure.persistence.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.model.User;

import java.time.Duration;
import java.util.Optional;

/**
 * Repository for managing User cache in Redis.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "user:profile:";
    private static final Duration CACHE_TTL = Duration.ofHours(8);

    /**
     * Saves a user object to the Redis cache with a predefined Time-To-Live (TTL).
     * This method handles the serialization of the user object and stores it under
     * a key derived from the user's ID.
     * If the user or their ID is null, the operation is aborted to prevent invalid
     * cache entries.
     * Any exceptions during the caching process are caught and logged to prevent
     * interrupting the application flow.
     *
     * @param user The {@link User} domain object to be cached. Must not be null and
     *             must have a valid ID.
     */
    public void save(User user) {
        if (user == null || user.getId() == null) {
            return;
        }
        String key = KEY_PREFIX + user.getId();
        try {
            redisTemplate.opsForValue().set(key, user, CACHE_TTL);
            log.debug("User cached successfully with ID: {}", user.getId());
        } catch (Exception e) {
            log.error("Failed to cache user with ID: {}. Error: {}", user.getId(), e.getMessage(), e);
        }
    }

    /**
     * Retrieves a {@link User} object from the Redis cache using their unique ID.
     * This method attempts to fetch the serialized data associated with the
     * constructed key.
     * If the data exists and is of the correct type, it is returned wrapped in an
     * {@link Optional}.
     * If the data is missing, null, or serialization fails, an empty Optional is
     * returned.
     * Any exceptions, such as connection issues or deserialization errors, are
     * logged, and an empty Optional is returned.
     *
     * @param userId The unique identifier of the user to retrieve.
     * @return An {@link Optional} containing the {@link User} object if found and
     *         valid; otherwise, an empty {@link Optional}.
     */
    public Optional<User> findById(Long userId) {
        String key = KEY_PREFIX + userId;
        try {
            Object cached = redisTemplate.opsForValue().get(key);
            if (cached instanceof User) {
                return Optional.of((User) cached);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve user from cache. User ID: {}. Error: {}", userId, e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Removes a specific user from the Redis cache.
     * This is typically used during logout, profile updates, or password changes to
     * ensure cache consistency.
     * Exceptions encountered during the delete operation are caught and logged.
     *
     * @param userId The unique identifier of the user whose cache entry should be
     *               invalidated.
     */
    public void delete(Long userId) {
        String key = KEY_PREFIX + userId;
        try {
            redisTemplate.delete(key);
            log.debug("User cache invalidated for ID: {}", userId);
        } catch (Exception e) {
            log.error("Failed to delete user from cache. User ID: {}. Error: {}", userId, e.getMessage(), e);
        }
    }
}
