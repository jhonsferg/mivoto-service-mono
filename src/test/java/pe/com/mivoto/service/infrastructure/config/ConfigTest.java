package pe.com.mivoto.service.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Nested
    class ApplicationConfigTest {
        private final ApplicationConfig config = new ApplicationConfig();

        @Test
        void testObjectMapper() {
            ObjectMapper mapper = config.objectMapper();
            assertNotNull(mapper);
        }

        @Test
        void testTaskExecutor() {
            Executor executor = config.taskExecutor();
            assertNotNull(executor);
            assertTrue(executor instanceof ThreadPoolTaskExecutor);
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
            assertEquals(4, taskExecutor.getCorePoolSize());
            assertEquals(8, taskExecutor.getMaxPoolSize());
            assertEquals(100, taskExecutor.getQueueCapacity());
            assertEquals("mivoto-async-", taskExecutor.getThreadNamePrefix());
        }
    }

    @Nested
    class CorsConfigTest {
        private final CorsConfig config = new CorsConfig();

        @Test
        void testCorsConfigurationSource() {
            var source = (UrlBasedCorsConfigurationSource) config.corsConfigurationSource();
            assertNotNull(source);
        }
    }

    @Nested
    class AsyncConfigTest {
        private final AsyncConfig config = new AsyncConfig();

        @Test
        void testGetAsyncExecutor() {
            Executor executor = config.getAsyncExecutor();
            assertNotNull(executor);
            assertTrue(executor instanceof ThreadPoolTaskExecutor);
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) executor;
            assertEquals(5, taskExecutor.getCorePoolSize());
            assertEquals(10, taskExecutor.getMaxPoolSize());
            assertEquals(200, taskExecutor.getQueueCapacity());
            assertEquals("async-vote-", taskExecutor.getThreadNamePrefix());
        }
    }

    @Nested
    class OpenApiConfigTest {
        private final OpenApiConfig config = new OpenApiConfig();

        @Test
        void testMiVotoOpenAPI() {
            // Set private field serverPort using reflection as it's @Value injected
            ReflectionTestUtils.setField(config, "serverPort", "8080");

            OpenAPI openAPI = config.miVotoOpenAPI();
            assertNotNull(openAPI);
            assertNotNull(openAPI.getInfo());
            assertEquals("MiVoto API", openAPI.getInfo().getTitle());
            assertEquals("1.0.0", openAPI.getInfo().getVersion());

            assertNotNull(openAPI.getServers());
            assertFalse(openAPI.getServers().isEmpty());
            assertEquals("http://localhost:8080", openAPI.getServers().get(0).getUrl());
        }
    }
}
