package com.shop.web;

import com.shop.security.TokenResponse;
import com.shop.security.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    private final TokenService tokens;

    public TokenController(TokenService tokens) {
        this.tokens = tokens;
    }

    @PostMapping("/api/token")
    public TokenResponse token(Authentication authentication) {
        return tokens.issue(authentication);
    }
}
