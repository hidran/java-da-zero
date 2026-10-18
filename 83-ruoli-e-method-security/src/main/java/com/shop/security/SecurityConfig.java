package com.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    UserDetailsService users() {
        // Demo users of the course: passwords alice-demo and bob-demo, stored only as BCrypt hashes
        return new InMemoryUserDetailsManager(
                User.withUsername("alice")
                        .password("{bcrypt}$2a$10$YZBK.GYMthlDx9tf0GMnxOd9MBVpx8dsg0EKXSk3FkWYnLG8yd0Re")
                        .roles("USER").build(),
                User.withUsername("bob")
                        .password("{bcrypt}$2a$10$Z8sbHRaI1LujirsaEdW9SuECMKKoR8xX0auvzxjYig/099s/d4k2e")
                        .roles("ADMIN").build());
    }
    @Bean
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/rest-client.html", "/requests/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
