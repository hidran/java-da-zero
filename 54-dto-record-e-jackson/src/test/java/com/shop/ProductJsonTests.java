package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductJsonTests {
    @Autowired MockMvc mvc;
    @Autowired JsonMapper json;

    @Test
    void endpointExposesOnlyThePublicContract() throws Exception {
        mvc.perform(get("/api/products/featured"))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.priceInCents").value(4900))
                .andExpect(jsonPath("$.supplierCostInCents").doesNotExist());
    }

    @Test
    void serializesTheRecordWithTypedValues() throws Exception {
        String text = json.writeValueAsString(new ProductResponse(1L, "Keyboard", 4900));
        var tree = json.readTree(text);
        assertEquals(3, tree.size());
        assertTrue(tree.get("priceInCents").isIntegralNumber());
        assertEquals(4900, tree.get("priceInCents").intValue());
    }

    @Test
    void readsTheRecordThroughItsCanonicalConstructor() throws Exception {
        ProductResponse dto = json.readValue(
                "{\"id\":1,\"name\":\"Keyboard\",\"priceInCents\":4900}", ProductResponse.class);
        assertEquals(new ProductResponse(1L, "Keyboard", 4900), dto);
    }

    @Test
    void mappingPreservesTheDomainObject() {
        Product product = new Product(1L, "Keyboard", 4900, 2100);
        assertEquals(new ProductResponse(1L, "Keyboard", 4900), ProductResponse.from(product));
        assertEquals(2100, product.supplierCostInCents());
    }
}
