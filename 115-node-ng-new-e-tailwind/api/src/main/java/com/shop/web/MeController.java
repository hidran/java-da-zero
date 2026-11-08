package com.shop.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login", description = "Exchange username and password for a token")
@RestController
public class MeController {
    @Operation(summary = "Who am I: the user and the roles of the token")
    @GetMapping("/api/me")
    public MeResponse me(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring("ROLE_".length()))
                .toList();
        return new MeResponse(authentication.getName(), roles);
    }
}
