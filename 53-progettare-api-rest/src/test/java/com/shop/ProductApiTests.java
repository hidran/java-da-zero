package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTests {
    @Autowired MockMvc mvc;

    @Test
    void listsSeedProduct() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void unknownProductIsNotFound() throws Exception {
        mvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createsReadsAndDeletesAResource() throws Exception {
        String location = mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"name\":\"Mouse\",\"priceInCents\":2500}"))
                .andExpect(status().isCreated()).andExpect(header().string("Location", startsWith("/api/products/")))
                .andExpect(jsonPath("$.name").value("Mouse"))
                .andReturn().getResponse().getHeader("Location");
        mvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.priceInCents").value(2500));
        mvc.perform(delete(location)).andExpect(status().isNoContent())
                .andExpect(content().string(""));
        mvc.perform(delete(location)).andExpect(status().isNotFound());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void postRejectsUnsupportedMediaType() throws Exception {
        mvc.perform(post("/api/products").contentType("text/plain").content("Mouse"))
                .andExpect(status().isUnsupportedMediaType());
    }
}
