package com.shop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTests {
    @Autowired
    MockMvc mvc;

    @Test
    void listReturnsTheDemoCatalog() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void unknownProductIsAProblemDetail() throws Exception {
        mvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("https://api.shop.example/problems/product-not-found"));
    }

    @Test
    void invalidProductIsAValidationProblem() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\" \",\"priceInCents\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("https://api.shop.example/problems/validation-error"))
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.priceInCents").exists());
    }

    @Test
    void statsSummarizeTheCatalog() throws Exception {
        mvc.perform(get("/api/products/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCount").value(3))
                .andExpect(jsonPath("$.totalPriceInCents").value(26300));
    }
}
