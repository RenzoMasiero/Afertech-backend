package com.facturacion.Afertech.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 🔹 CORS PREFLIGHT
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Públicos
                        .requestMatchers("/auth/**", "/h2-console/**").permitAll()

                        // USERS → SOLO ADMIN
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // DELETE → solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                        // GET → ADMIN o USER
                        .requestMatchers(HttpMethod.GET, "/**")
                        .hasAnyRole("ADMIN", "USER")

                        // POST específicos
                        .requestMatchers(HttpMethod.POST, "/invoices/**")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/projects/**")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // 🔹 CORS GLOBAL CONFIG (SIN HARDCODE)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        String allowedOriginsEnv = System.getenv("APP_CORS_ALLOWED_ORIGINS");

        List<String> allowedOrigins;

        if (allowedOriginsEnv != null && !allowedOriginsEnv.isBlank()) {
            allowedOrigins = List.of(allowedOriginsEnv.split(","));
        } else {
            // fallback para desarrollo local
            allowedOrigins = List.of("http://localhost:5173");
        }

        config.setAllowedOrigins(allowedOrigins);

        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        config.setAllowedHeaders(
                List.of("Content-Type", "Authorization")
        );

        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}