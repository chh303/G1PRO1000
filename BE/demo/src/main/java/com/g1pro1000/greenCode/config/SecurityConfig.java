package com.g1pro1000.greenCode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deaktiver CSRF for testing (IKKE I PRODUKSJON)
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Tillat H2 Console
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/poengtest.html", "/static/**", "/css/**", "/script/**", "/**.html").permitAll() // Tillat HTML-filer
                .requestMatchers("/api/users/register", "/api/users/login", "/api/users/logout", "/api/users/session", "/h2-console/**").permitAll() // Tillat registrering og login API-er
                .requestMatchers("/api/score/update", "/api/score/**", "/api/forum/**", "/api/comments/**").permitAll() // Tillat score API-er
                .requestMatchers("/js/**", "/images/**").permitAll() // Tillat CSS, JS og bilder
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .maximumSessions(1) // Kun én aktiv session per bruker
                .expiredUrl("/login.html") // Send bruker til login hvis session utløper
            )
            .formLogin(login -> login.disable()) // 🚀 Deaktiverer Spring Security sin standard login-side
            .httpBasic(basic -> basic.disable()); // 🚀 Deaktiverer Basic Authentication

        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
