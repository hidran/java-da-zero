package com.shop.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductSecurityTests {
    private static final String NEW_PRODUCT = "{\"name\":\"Webcam\",\"priceInCents\":5900}";
    @Autowired
    MockMvc mvc;
    @Test
    void anonymousReadsTheCatalog() throws Exception {
        mvc.perform(get("/api/products")).andExpect(status().isOk());
    }
    @Test
    void anonymousCannotCreate() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminDeletes() throws Exception {
        mvc.perform(delete("/api/products/1")).andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(roles = "USER")
    void userCannotDelete() throws Exception {
        mvc.perform(delete("/api/products/2")).andExpect(status().isForbidden());
    }
    @Test
    void userTokenCannotDeleteEither() throws Exception {
        mvc.perform(delete("/api/products/3")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isForbidden());
    }
    @Test
    void realTokenFromTheTokenEndpointIsAccepted() throws Exception {
        String json = mvc.perform(post("/api/token").with(httpBasic("alice", "alice-demo")))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String token = JsonPath.read(json, "$.token");
        mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isCreated());
    }
}
