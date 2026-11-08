package com.shop.security;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private static final Duration LIFETIME = Duration.ofMinutes(15);
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public TokenResponse issue(Authentication authentication) {
        Instant now = Instant.now();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring("ROLE_".length()))
                .toList();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("shop")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(LIFETIME))
                .claim("roles", roles)
                .build();
        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenResponse(token, LIFETIME.toSeconds());
    }
}
