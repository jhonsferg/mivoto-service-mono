package pe.com.mivoto.service.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for application caching.
 * Uses Caffeine as the cache provider.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures the CacheManager with Caffeine.
     * Sets expiration and maximum size policies.
     * Defines caches for: elections, candidates, users, statistics.
     *
     * @return The configured CacheManager.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("elections", "candidates", "users", "statistics");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .recordStats());
        return cacheManager;
    }
}
