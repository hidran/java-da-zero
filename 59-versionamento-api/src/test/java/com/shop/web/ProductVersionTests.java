package com.shop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductVersionTests {
    @Autowired MockMvc mvc;

    @Test
    void requestWithoutAVersionGetsVersionOne() throws Exception {
        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceInCents").value(4900))
                .andExpect(jsonPath("$.price").doesNotExist());
    }

    @Test
    void versionOneKeepsThePriceInCents() throws Exception {
        mvc.perform(get("/api/products/1").header("API-Version", "1.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceInCents").value(4900));
    }

    @Test
    void versionTwoReturnsAPriceObject() throws Exception {
        mvc.perform(get("/api/products/1").header("API-Version", "2.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price.amountInCents").value(4900))
                .andExpect(jsonPath("$.price.currency").value("EUR"))
                .andExpect(jsonPath("$.priceInCents").doesNotExist());
    }

    @Test
    void unsupportedVersionIsRejected() throws Exception {
        mvc.perform(get("/api/products/1").header("API-Version", "3.0"))
                .andExpect(status().isBadRequest());
    }
}
