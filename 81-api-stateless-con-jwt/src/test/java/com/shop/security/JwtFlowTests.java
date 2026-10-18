package com.shop.security;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
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
class JwtFlowTests {
    private static final String NEW_PRODUCT = "{\"name\":\"Webcam\",\"priceInCents\":5900}";

    @Autowired
    MockMvc mvc;

    @Test
    void tokenIsIssuedForValidCredentials() throws Exception {
        mvc.perform(post("/api/token").header(HttpHeaders.AUTHORIZATION, basic("alice", "alice-demo")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.expiresInSeconds").value(900));
    }

    @Test
    void bearerTokenCreatesAProduct() throws Exception {
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, bearer("alice", "alice-demo"))
                        .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isCreated());
    }

    @Test
    void requestWithoutTokenAsksForABearerToken() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string(HttpHeaders.WWW_AUTHENTICATE, containsString("Bearer")));
    }

    @Test
    void passwordsAreAcceptedOnlyByTheTokenEndpoint() throws Exception {
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, basic("alice", "alice-demo"))
                        .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userTokenCannotDelete() throws Exception {
        mvc.perform(delete("/api/products/2").header(HttpHeaders.AUTHORIZATION, bearer("alice", "alice-demo")))
                .andExpect(status().isForbidden());
    }

    @Test
    void tamperedTokenIsRejected() throws Exception {
        String token = bearer("alice", "alice-demo");
        String tampered = token.substring(0, token.length() - 4) + (token.endsWith("AAAA") ? "BBBB" : "AAAA");
        mvc.perform(delete("/api/products/2").header(HttpHeaders.AUTHORIZATION, tampered))
                .andExpect(status().isUnauthorized());
    }

    private String bearer(String username, String password) throws Exception {
        String json = mvc.perform(post("/api/token").header(HttpHeaders.AUTHORIZATION, basic(username, password)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + JsonPath.read(json, "$.token");
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
