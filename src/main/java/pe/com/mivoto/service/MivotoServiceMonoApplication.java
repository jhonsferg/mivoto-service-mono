package pe.com.mivoto.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class MivotoServiceMonoApplication {
    public static void main(String[] args) {
        log.info("=".repeat(80));
        log.info("Iniciando Sistema de Votación Electrónica");
        log.info("=".repeat(80));

        SpringApplication.run(MivotoServiceMonoApplication.class, args);

        log.info("=".repeat(80));
        log.info("Sistema de Votación Electrónica iniciado correctamente");
        log.info("Documentación API disponible en: http://localhost:8080/swagger-ui.html");
        log.info("=".repeat(80));
    }
}
