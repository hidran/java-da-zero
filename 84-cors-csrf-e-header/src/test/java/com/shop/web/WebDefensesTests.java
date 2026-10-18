package com.shop.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
class WebDefensesTests {
    private static final String FRONTEND = "http://127.0.0.1:20841";

    @Autowired
    MockMvc mvc;

    @Test
    void corsAllowsTheShopFrontend() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.ORIGIN, FRONTEND))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, FRONTEND));
    }

    @Test
    void corsRejectsAnotherOrigin() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.ORIGIN, "http://127.0.0.1:20999"))
                .andExpect(status().isForbidden());
    }

    @Test
    void preflightForCreateIsAnswered() throws Exception {
        mvc.perform(options("/api/products").header(HttpHeaders.ORIGIN, FRONTEND)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "authorization,content-type"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, containsString("POST")));
    }

    @Test
    void securityHeadersAreSent() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-Frame-Options", "DENY"))
                .andExpect(header().string("Content-Security-Policy", "frame-ancestors 'none'"))
                .andExpect(header().string("Referrer-Policy", "no-referrer"));
    }

    @Test
    void statelessApiNeedsNoCsrfTokenAndSetsNoCookie() throws Exception {
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, basic("alice", "alice-demo"))
                        .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Webcam\",\"priceInCents\":5900}"))
                .andExpect(status().isCreated())
                .andExpect(header().doesNotExist(HttpHeaders.SET_COOKIE));
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
