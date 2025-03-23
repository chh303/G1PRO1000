package com.g1pro1000.greenCode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deaktiver CSRF for testing
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Tillat H2 Console
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/poengtest.html", "/static/**", "/css/**", "/script/**", "/**.html").permitAll() // Tillat HTML-filer
                .requestMatchers("/api/users", "/api/users/register", "/api/users/login", "/h2-console/**").permitAll() // Tillat registrering og login
                .requestMatchers("/api/score/update", "/api/score/**").permitAll() // Tillat score-oppdatering og henting av score
                .requestMatchers("/js/**", "/images/**").permitAll() // 🔹 Sørg for at CSS, JS og bilder er tilgjengelige
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable()) // Deaktiver standard login-side
            .httpBasic(basic -> basic.disable()); // Deaktiver Basic Authentication

        return http.build();
    }
}
