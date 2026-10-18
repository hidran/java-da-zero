package com.shop.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtKeyConfig {
    // Demo signing key: a new RSA key pair at every startup, never written to disk or committed
    private final KeyPair keyPair = generateKeyPair();

    @Bean
    JwtEncoder jwtEncoder() {
        return NimbusJwtEncoder.withKeyPair(publicKey(), (RSAPrivateKey) keyPair.getPrivate()).build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }

    private RSAPublicKey publicKey() {
        return (RSAPublicKey) keyPair.getPublic();
    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("RSA is not available", exception);
        }
    }
}
