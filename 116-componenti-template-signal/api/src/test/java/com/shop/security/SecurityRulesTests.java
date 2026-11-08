package com.shop.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SecurityRulesTests {
    private static final String DEV_SERVER = "http://127.0.0.1:21161";
    private static final String NEW_PRODUCT = """
            {"name":"Webcam HD","description":"Full HD webcam.","priceInCents":6900,"categoryId":1}
            """;

    @Autowired
    MockMvc mvc;

    @Test
    void anonymousReadsCatalogAndCategories() throws Exception {
        mvc.perform(get("/api/products")).andExpect(status().isOk());
        mvc.perform(get("/api/products/1")).andExpect(status().isOk());
        mvc.perform(get("/api/categories")).andExpect(status().isOk());
    }

    @Test
    void guestPlacesAnOrder() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lines\":[{\"productId\":3,\"quantity\":1}]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalInCents").value(18900));
    }

    @Test
    void tokenIsIssuedForTheDemoAdmin() throws Exception {
        mvc.perform(post("/api/auth/token").with(httpBasic("admin", "admin-demo")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.expiresInSeconds").value(900));
    }

    @Test
    void wrongPasswordFromJavaScriptGetsNoBrowserLoginDialog() throws Exception {
        mvc.perform(post("/api/auth/token").with(httpBasic("admin", "wrong"))
                        .header("X-Requested-With", "XMLHttpRequest"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HttpHeaders.WWW_AUTHENTICATE));
    }

    @Test
    void meAnswersWithTheOwnerOfTheToken() throws Exception {
        String authorization = bearer("customer", "customer-demo");
        mvc.perform(get("/api/me").header(HttpHeaders.AUTHORIZATION, authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("customer"))
                .andExpect(jsonPath("$.roles[0]").value("CUSTOMER"));
    }

    @Test
    void meNeedsAToken() throws Exception {
        mvc.perform(get("/api/me")).andExpect(status().isUnauthorized());
    }

    @Test
    void anonymousCannotChangeTheCatalog() throws Exception {
        mvc.perform(post("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void customerCannotChangeTheCatalog() throws Exception {
        mvc.perform(post("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminTokenCreatesAProduct() throws Exception {
        mvc.perform(post("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT)
                        .header(HttpHeaders.AUTHORIZATION, bearer("admin", "admin-demo")))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    void corsAllowsTheDevServer() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.ORIGIN, DEV_SERVER))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, DEV_SERVER));
    }

    @Test
    void corsRejectsAnotherOrigin() throws Exception {
        mvc.perform(get("/api/products").header(HttpHeaders.ORIGIN, "http://127.0.0.1:21999"))
                .andExpect(status().isForbidden());
    }

    @Test
    void preflightForTheLoginIsAnswered() throws Exception {
        mvc.perform(options("/api/auth/token").header(HttpHeaders.ORIGIN, DEV_SERVER)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "authorization,x-requested-with"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, DEV_SERVER));
    }

    private String bearer(String username, String password) throws Exception {
        String json = mvc.perform(post("/api/auth/token").with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + JsonPath.read(json, "$.token");
    }
}
