package com.shop.supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class SupplierClientTests {
    @Autowired SupplierClient client;
    @Autowired MockMvc mvc;

    @Test
    void readsTheStockFromTheSupplier() {
        assertThat(client.stock(1)).isEqualTo(new SupplierStock(1, 12));
    }

    @Test
    void productWithoutStockHasZeroUnits() {
        assertThat(client.stock(2).available()).isZero();
    }

    @Test
    void availabilityCombinesCatalogAndSupplier() throws Exception {
        mvc.perform(get("/api/products/1/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.available").value(12));
    }
}
