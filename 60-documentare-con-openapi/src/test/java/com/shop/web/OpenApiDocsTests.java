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
class OpenApiDocsTests {
    @Autowired MockMvc mvc;

    @Test
    void contractHasTheShopTitle() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Shop API"))
                .andExpect(jsonPath("$.info.version").value("1.0"));
    }

    @Test
    void listOperationHasASummary() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(jsonPath("$.paths['/api/products'].get.summary")
                        .value("List products, one page at a time"));
    }

    @Test
    void readOperationDocumentsTheMissingProduct() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(jsonPath("$.paths['/api/products/{id}'].get.responses['404'].description")
                        .value("Product not found"));
    }

    @Test
    void sizeLimitComesFromTheConstraints() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(jsonPath("$.paths['/api/products'].get.parameters[?(@.name == 'size')].schema.maximum")
                        .value(20));
    }
}
