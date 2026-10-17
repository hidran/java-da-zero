package com.shop.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ConcurrentUpdateApiTests {
    @Autowired
    MockMvc mvc;

    @Test
    void staleUpdateReturnsAConflictProblem() throws Exception {
        String body = "{\"name\":\"Mouse\",\"priceInCents\":2700,\"version\":0}";
        mvc.perform(put("/api/products/2").contentType("application/json").content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(1));
        mvc.perform(put("/api/products/2").contentType("application/json").content(body))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.type").value("https://api.shop.example/problems/concurrent-update"))
                .andExpect(jsonPath("$.title").value("Concurrent update"));
    }
}
