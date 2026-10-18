package com.shop.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain edge(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/rest-client.html", "/requests/**", "/realms/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().denyAll());
        http.oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
