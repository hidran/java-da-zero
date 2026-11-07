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
class ProductListTests {
    @Autowired MockMvc mvc;

    @Test
    void firstPageHasFiveProductsAndTheTotals() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(12))
                .andExpect(jsonPath("$.totalPages").value(3));
    }

    @Test
    void sortsByPrice() throws Exception {
        mvc.perform(get("/api/products").param("sort", "PRICE").param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Mouse pad"))
                .andExpect(jsonPath("$.content[1].name").value("Mouse"))
                .andExpect(jsonPath("$.content[2].name").value("USB hub"));
    }

    @Test
    void filtersByName() throws Exception {
        mvc.perform(get("/api/products").param("name", "mouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void rejectsAPageThatIsTooLarge() throws Exception {
        mvc.perform(get("/api/products").param("size", "100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"));
    }
}
