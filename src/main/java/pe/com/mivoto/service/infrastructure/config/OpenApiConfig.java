package pe.com.mivoto.service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration for OpenAPI (Swagger) documentation.
 * Defines the documentation metadata and security requirements for the API.
 */
@Configuration
public class OpenApiConfig {

        @Value("${server.port:8080}")
        private String serverPort;

        /**
         * Configures the OpenAPI bean with project metadata, contact info, and security
         * schemes.
         *
         * @return The configured OpenAPI object.
         */
        @Bean
        public OpenAPI miVotoOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("MiVoto API")
                                                .description("API REST para sistema de votación electrónica con estructuras de datos personalizadas")
                                                .version("1.0.0")
                                                .contact(new Contact().name("Equipo de Desarrollo")
                                                                .email("dev@mivoto.com")
                                                                .url("https://github.com/mivoto"))
                                                .license(new License().name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")))
                                .servers(List.of(
                                                new Server().url("http://localhost:" + serverPort)
                                                                .description("Servidor de desarrollo"),
                                                new Server().url("https://api.mivoto.com")
                                                                .description("Servidor de producción")))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .description("Autenticación JWT. Formato: Bearer {token}")));
        }
}
