package pe.com.mivoto.service.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.com.mivoto.service.infrastructure.security.UserDetailsServiceImpl;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtAuthenticationEntryPoint;
import pe.com.mivoto.service.infrastructure.security.jwt.JwtAuthenticationFilter;

/**
 * Spring Security configuration.
 * Defines authentication and authorization rules, filter chain, and security
 * beans.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the Security Filter Chain.
     * Disables CSRF, sets stateless session policy, configures endpoint access
     * rules, and adds JWT filter.
     *
     * @param http HttpSecurity configuration.
     * @return The built SecurityFilterChain.
     * @throws Exception if configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/health").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/votes/**").hasAnyRole("VOTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/votes/history").hasAnyRole("VOTER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/elections/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/elections/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/elections/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/elections/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/candidates/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/candidates/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/candidates/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/candidates/**").hasRole("ADMIN")
                        .requestMatchers("/api/audit/**").hasAnyRole("ADMIN", "SUPERVISOR")
                        .requestMatchers("/api/statistics/**").hasAnyRole("ADMIN", "SUPERVISOR")
                        .anyRequest().authenticated());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Provides the AuthenticationProvider.
     * Configures DaoAuthenticationProvider with UserDetailsService and
     * PasswordEncoder.
     *
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides the AuthenticationManager.
     *
     * @param config AuthenticationConfiguration.
     * @return The AuthenticationManager.
     * @throws Exception if retrieval fails.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides the PasswordEncoder.
     * Uses BCrypt.
     *
     * @return The PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
