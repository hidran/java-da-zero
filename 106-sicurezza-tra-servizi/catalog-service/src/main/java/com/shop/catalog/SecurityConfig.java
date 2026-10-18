package com.shop.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/api/products/**")
                .hasAuthority("SCOPE_catalog.read")
                .anyRequest().denyAll());
        http.oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
