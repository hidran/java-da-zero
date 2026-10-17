package com.shop.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private static final String BOB = basic("bob", "bob-demo");
    private static final String NEW_PRODUCT = "{\"name\":\"Webcam\",\"priceInCents\":5900}";

    @Autowired
    MockMvc mvc;

    @Test
    void catalogIsPublicForReading() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    void anonymousCannotCreate() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void aliceCreatesAProduct() throws Exception {
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, ALICE)
                        .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isCreated());
    }

    @Test
    void aliceCannotDelete() throws Exception {
        mvc.perform(delete("/api/products/2").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isForbidden());
    }

    @Test
    void bobDeletesAProduct() throws Exception {
        mvc.perform(delete("/api/products/3").header(HttpHeaders.AUTHORIZATION, BOB))
                .andExpect(status().isNoContent());
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
