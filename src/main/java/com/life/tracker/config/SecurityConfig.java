package com.life.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final ZielRoleFilter zielRoleFilter;

    public SecurityConfig(ZielRoleFilter zielRoleFilter) {
        this.zielRoleFilter = zielRoleFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/registration/save", "/api/login").permitAll()
                        .requestMatchers("/api/ziel").permitAll() // öffentlich, aber Filter prüft Rolle
                        .anyRequest().authenticated()
                )
                .addFilterBefore(zielRoleFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

