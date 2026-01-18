package pe.com.mivoto.service.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Type-safe configuration properties for the MiVoto application.
 * Mapped to "mivoto" prefix in application.yml.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mivoto")
public class MivotoProperties {

    private VoteProcessing voteProcessing = new VoteProcessing();
    private Election election = new Election();
    private Security security = new Security();

    /**
     * Configuration for vote processing queue and batching.
     */
    @Data
    public static class VoteProcessing {
        private int queueSize = 1000;
        private int batchSize = 100;
        private long processingInterval = 5000;
    }

    /**
     * Configuration for election behavior.
     */
    @Data
    public static class Election {
        private boolean autoClose = true;
        private String resultsVisibility = "ADMIN_ONLY";
    }

    /**
     * Configuration for security parameters.
     */
    @Data
    public static class Security {
        private int maxLoginAttempts = 5;
        private long accountLockDuration = 3600;
    }
}
