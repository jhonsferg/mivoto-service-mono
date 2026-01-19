package pe.com.mivoto.service.infrastructure.persistence.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.model.Election;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Election cache in Redis.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ElectionCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "election:";
    private static final String ACTIVE_LIST_KEY = "elections:active";
    private static final Duration ELECTION_TTL = Duration.ofHours(4);
    private static final Duration LIST_TTL = Duration.ofHours(1);

    /**
     * Caches a single {@link Election} entity in Redis.
     * The election is stored using a key composed of its unique ID.
     * This operation has a specific TTL (e.g., 4 hours) to ensure data freshness.
     * If the election or its ID is null, the operation returns immediately.
     * Any runtime exceptions (e.g., Redis connection failure) are caught and
     * logged.
     *
     * @param election The {@link Election} object to cache.
     */
    public void save(Election election) {
        if (election == null || election.getId() == null)
            return;
        try {
            redisTemplate.opsForValue().set(KEY_PREFIX + election.getId(), election, ELECTION_TTL);
            log.debug("Election cached: {}", election.getId());
        } catch (Exception e) {
            log.error("Error caching election: {}", election.getId(), e);
        }
    }

    /**
     * Retrieves a cached {@link Election} by its ID.
     * It attempts to fetch and cast the object stored under the election's specific
     * key.
     * If the object is not found or is of an incompatible type, an empty
     * {@link Optional} is returned.
     * Exceptions during retrieval are logged securely.
     *
     * @param id The unique identifier of the election.
     * @return An {@link Optional} containing the {@link Election} if found,
     *         otherwise empty.
     */
    public Optional<Election> findById(Long id) {
        try {
            Object cached = redisTemplate.opsForValue().get(KEY_PREFIX + id);
            if (cached instanceof Election) {
                return Optional.of((Election) cached);
            }
        } catch (Exception e) {
            log.error("Error retrieving election from cache: {}", id, e);
        }
        return Optional.empty();
    }

    /**
     * Removes a specific election from the cache using its ID.
     * Should be called when an election is updated or deleted to maintain
     * consistency.
     * Logs any errors encountered during the deletion process.
     *
     * @param id The unique identifier of the election to remove.
     */
    public void delete(Long id) {
        try {
            redisTemplate.delete(KEY_PREFIX + id);
            log.debug("Election removed from cache: {}", id);
        } catch (Exception e) {
            log.error("Error deleting election from cache: {}", id, e);
        }
    }

    /**
     * Caches the list of all currently active elections.
     * This list is frequently accessed and thus cached separately with a shorter
     * TTL (e.g., 1 hour).
     * Stores the entire list under a single key.
     * Logs any exceptions that occur during saving.
     *
     * @param elections The list of active {@link Election} objects to cache.
     */
    public void saveActiveList(List<Election> elections) {
        try {
            redisTemplate.opsForValue().set(ACTIVE_LIST_KEY, elections, LIST_TTL);
            log.debug("Active elections list cached. Count: {}", elections != null ? elections.size() : 0);
        } catch (Exception e) {
            log.error("Error caching active elections list", e);
        }
    }

    /**
     * Retrieves the cached list of active elections.
     * Attempts to fetch the list stored under the active elections key.
     * If the cache is empty or the data is corrupted/invalid type, returns an empty
     * {@link Optional}.
     * Exceptions are logged.
     *
     * @return An {@link Optional} containing the list of active {@link Election}s
     *         if available.
     */
    @SuppressWarnings("unchecked")
    public Optional<List<Election>> findActiveList() {
        try {
            Object cached = redisTemplate.opsForValue().get(ACTIVE_LIST_KEY);
            if (cached instanceof List) {
                return Optional.of((List<Election>) cached);
            }
        } catch (Exception e) {
            log.error("Error retrieving active elections list from cache", e);
        }
        return Optional.empty();
    }

    /**
     * Initializes the removal of the active elections list from the cache.
     * This is necessary when an election's status changes to or from ACTIVE.
     * Logs any errors that impede the deletion.
     */
    public void deleteActiveList() {
        try {
            redisTemplate.delete(ACTIVE_LIST_KEY);
            log.debug("Active elections list removed from cache");
        } catch (Exception e) {
            log.error("Error deleting active elections list from cache", e);
        }
    }
}
