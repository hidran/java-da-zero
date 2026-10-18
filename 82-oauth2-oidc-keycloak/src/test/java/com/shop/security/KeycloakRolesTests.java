package com.shop.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class KeycloakRolesTests {
    private static final String NEW_PRODUCT = "{\"name\":\"Webcam\",\"priceInCents\":5900}";
    private final KeycloakRealmRoleConverter converter = new KeycloakRealmRoleConverter();

    @Autowired
    MockMvc mvc;

    @Test
    void anonymousCannotCreate() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userTokenCreatesAProduct() throws Exception {
        mvc.perform(post("/api/products").with(keycloakToken("alice", "USER"))
                        .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isCreated());
    }

    @Test
    void userTokenCannotDelete() throws Exception {
        mvc.perform(delete("/api/products/2").with(keycloakToken("alice", "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminTokenDeletes() throws Exception {
        mvc.perform(delete("/api/products/3").with(keycloakToken("bob", "ADMIN")))
                .andExpect(status().isNoContent());
    }

    @Test
    void converterReadsTheRealmRoles() {
        Jwt token = Jwt.withTokenValue("test-only").header("alg", "none")
                .claim("realm_access", Map.of("roles", List.of("USER", "ADMIN")))
                .build();
        assertThat(converter.convert(token)).extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_USER", "ROLE_ADMIN");
    }

    @Test
    void tokenWithoutRealmAccessHasNoRoles() {
        Jwt token = Jwt.withTokenValue("test-only").header("alg", "none").claim("scope", "openid").build();
        assertThat(converter.convert(token)).isEmpty();
    }

    private JwtRequestPostProcessor keycloakToken(String username, String role) {
        return jwt().jwt(token -> token.claim("preferred_username", username)
                        .claim("realm_access", Map.of("roles", List.of(role))))
                .authorities(converter);
    }
}
