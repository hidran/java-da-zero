package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProblemDetailTests {
    @Autowired MockMvc mvc;

    @Test
    void invalidFieldsReturnAnRfc9457Problem() throws Exception {
        mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"name\":\"   \",\"priceInCents\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.type").value(
                        "https://api.shop.example/problems/validation-error"))
                .andExpect(jsonPath("$.title").value("Request validation failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("One or more fields are invalid."))
                .andExpect(jsonPath("$.errors.name").value("must not be blank"))
                .andExpect(jsonPath("$.errors.priceInCents").value("must be greater than 0"));
    }

    @Test
    void missingNameIdentifiesTheFieldToCorrect() throws Exception {
        mvc.perform(post("/api/products").contentType("application/json")
                        .content("{\"priceInCents\":2500}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("must not be blank"));
    }
}
