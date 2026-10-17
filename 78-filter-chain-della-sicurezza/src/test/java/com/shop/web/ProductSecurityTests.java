package com.shop.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductSecurityTests {
    private static final String ALICE = basic("alice", "alice-demo");

    @Autowired
    MockMvc mvc;

    @Test
    void anonymousRequestIsRejectedWith401() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void aliceReadsTheCatalog() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isOk());
    }

    @Test
    void wrongPasswordIsRejectedWith401() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.AUTHORIZATION, basic("alice", "wrong-password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postWithoutCsrfTokenIsRejectedWith403() throws Exception {
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, ALICE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Webcam\",\"priceInCents\":5900}"))
                .andExpect(status().isForbidden());
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
