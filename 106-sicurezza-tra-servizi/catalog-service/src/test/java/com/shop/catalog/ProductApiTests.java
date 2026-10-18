package com.shop.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTests {
    @Autowired
    MockMvc mvc;

    @Test
    void rejectsARequestWithoutToken() throws Exception {
        mvc.perform(get("/api/products/1")).andExpect(status().isUnauthorized());
    }

    @Test
    void rejectsATokenWithoutTheCatalogScope() throws Exception {
        mvc.perform(get("/api/products/1").with(jwt().authorities(scope("orders.write"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void returnsAProductWithTheCatalogScope() throws Exception {
        mvc.perform(get("/api/products/1").with(jwt().authorities(scope("catalog.read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    private static SimpleGrantedAuthority scope(String name) {
        return new SimpleGrantedAuthority("SCOPE_" + name);
    }
}
