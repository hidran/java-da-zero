package com.shop.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "shop.payment.api-key=test-only-value")
@AutoConfigureMockMvc
class OrderAccessTests {
    private static final String ALICE = basic("alice", "alice-demo");
    private static final String NOT_FOUND = "https://api.shop.example/problems/order-not-found";

    @Autowired
    MockMvc mvc;

    @Test
    void aliceReadsHerOrder() throws Exception {
        mvc.perform(get("/api/orders/1").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Keyboard"));
    }

    @Test
    void aliceCannotReadBobsOrder() throws Exception {
        mvc.perform(get("/api/orders/2").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value(NOT_FOUND));
    }

    @Test
    void missingOrderLooksExactlyTheSame() throws Exception {
        mvc.perform(get("/api/orders/99").header(HttpHeaders.AUTHORIZATION, ALICE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value(NOT_FOUND));
    }

    @Test
    void anonymousCannotReadOrders() throws Exception {
        mvc.perform(get("/api/orders/1"))
                .andExpect(status().isUnauthorized());
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
