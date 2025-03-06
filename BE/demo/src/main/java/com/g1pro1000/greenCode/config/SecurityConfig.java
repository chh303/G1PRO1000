package com.g1pro1000.greenCode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deaktiver CSRF for testing
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Tillat visning av H2 Console i iframes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/poengtest.html", "/static/**", "/**.html").permitAll() // Tillat HTML-filer
                .requestMatchers("/api/users/register", "/api/users/login", "/h2-console/**").permitAll() // Tillat API-endepunkter
                .requestMatchers("/api/score/update").permitAll() // 🔹 Tillat oppdatering av score uten autentisering
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable()) // Deaktiver Spring Security sin standard login-side
            .httpBasic(basic -> basic.disable()); // Deaktiver Basic Authentication

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
