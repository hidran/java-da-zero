package com.shop.web;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shop.catalog.ProductService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteRulesTests {
    private static final String ALICE = basic("alice", "alice-demo");
    private static final String BOB = basic("bob", "bob-demo");

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductService service;

    @Test
    void aliceCannotDeleteThroughTheProductsApi() throws Exception {
        mvc.perform(delete("/api/products/1").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isForbidden());
    }

    @Test
    void aliceCannotRemoveThroughTheClearanceEndpoint() throws Exception {
        mvc.perform(post("/api/clearance/1").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isForbidden());
    }

    @Test
    void bobDeletesThroughTheProductsApi() throws Exception {
        mvc.perform(delete("/api/products/2").header(HttpHeaders.AUTHORIZATION, BOB))
                .andExpect(status().isNoContent());
    }

    @Test
    void bobRemovesThroughTheClearanceEndpoint() throws Exception {
        mvc.perform(post("/api/clearance/3").header(HttpHeaders.AUTHORIZATION, BOB))
                .andExpect(status().isNoContent());
    }

    @Test
    void serviceRefusesACallerWithoutAuthentication() {
        assertThatThrownBy(() -> service.delete(1))
                .isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
