package com.shop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTests {
    @Autowired MockMvc mvc;

    @Test
    void listStartsWithTheKeyboard() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Keyboard"));
    }

    @Test
    void existingProductIsFound() throws Exception {
        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceInCents").value(4900));
    }

    @Test
    void createdProductHasATrimmedName() throws Exception {
        mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"name\":\"  Mouse  \",\"priceInCents\":2500}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Mouse"));
    }

    @Test
    void invalidProductReturnsAValidationProblem() throws Exception {
        mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"name\":\" \",\"priceInCents\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("must not be blank"));
    }

    @Test
    void deletedProductIsGone() throws Exception {
        String location = mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"name\":\"Monitor\",\"priceInCents\":18900}"))
                .andReturn().getResponse().getHeader("Location");
        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void unknownProductReturnsAProblemDetail() throws Exception {
        mvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.type").value(
                        "https://api.shop.example/problems/product-not-found"))
                .andExpect(jsonPath("$.title").value("Product not found"))
                .andExpect(jsonPath("$.detail").value("Product 99 does not exist."));
    }
}
