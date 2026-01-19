package pe.com.mivoto.service.infrastructure.persistence.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import pe.com.mivoto.service.domain.model.Candidate;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Candidate cache in Redis.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CandidateCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String ELECTION_CANDIDATES_PREFIX = "candidates:election:";
    private static final Duration LIST_TTL = Duration.ofHours(4);

    /**
     * Caches the list of candidates associated with a specific election.
     * The candidates are stored as a list under a key unique to the election ID.
     * This cache entry has a predefined TTL (e.g., 4 hours).
     * If the electionId is null, the method simply returns.
     * Any Redis-related exceptions are caught and logged.
     *
     * @param electionId The unique identifier of the election.
     * @param candidates The list of {@link Candidate} objects to cache.
     */
    public void saveByElection(Long electionId, List<Candidate> candidates) {
        if (electionId == null)
            return;
        try {
            redisTemplate.opsForValue().set(ELECTION_CANDIDATES_PREFIX + electionId, candidates, LIST_TTL);
            log.debug("Candidates cached for election: {}", electionId);
        } catch (Exception e) {
            log.error("Error caching candidates for election: {}", electionId, e);
        }
    }

    /**
     * Retrieves the list of candidates for a given election from the cache.
     * It attempts to fetch the object mapped to the election's candidate key.
     * If found and confirmed to be a List, it is returned wrapped in an
     * {@link Optional}.
     * Otherwise, or in case of errors, an empty Optional is returned.
     * Logs any exceptions encountered.
     *
     * @param electionId The unique identifier of the election.
     * @return An {@link Optional} containing the list of {@link Candidate}s, or
     *         empty if not found.
     */
    @SuppressWarnings("unchecked")
    public Optional<List<Candidate>> findByElection(Long electionId) {
        try {
            Object cached = redisTemplate.opsForValue().get(ELECTION_CANDIDATES_PREFIX + electionId);
            if (cached instanceof List) {
                return Optional.of((List<Candidate>) cached);
            }
        } catch (Exception e) {
            log.error("Error retrieving candidates from cache for election: {}", electionId, e);
        }
        return Optional.empty();
    }

    /**
     * Removes the cached list of candidates for a specific election.
     * This is crucial when candidates are added, updated, or removed to ensure the
     * cache reflects the latest state.
     * Any errors during the deletion process are logged.
     *
     * @param electionId The unique identifier of the election whose candidate cache
     *                   should be cleared.
     */
    public void deleteByElection(Long electionId) {
        try {
            redisTemplate.delete(ELECTION_CANDIDATES_PREFIX + electionId);
            log.debug("Candidates cache cleared for election: {}", electionId);
        } catch (Exception e) {
            log.error("Error deleting candidates from cache for election: {}", electionId, e);
        }
    }
}
