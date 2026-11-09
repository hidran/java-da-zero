package com.shop.web;

import com.shop.security.TokenResponse;
import com.shop.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login", description = "Exchange username and password for a token")
@RestController
public class TokenController {
    private final TokenService tokens;

    public TokenController(TokenService tokens) {
        this.tokens = tokens;
    }

    @Operation(summary = "Issue a JWT for the user of the Basic credentials")
    @PostMapping("/api/auth/token")
    public TokenResponse token(Authentication authentication) {
        return tokens.issue(authentication);
    }
}
