package com.xpert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
       // .csrf(csrf -> csrf.disable()) // CSRF protection disabled for stateless JWT-based REST API

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/categories").hasRole("ADMIN")  // optional
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
