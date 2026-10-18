package com.shop;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ObservabilityTests {
    @Autowired MockMvc mvc;
    @Autowired MeterRegistry registry;

    @Test
    void healthShowsComponentsWithoutDetails() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.catalog.status").value("UP"))
                .andExpect(jsonPath("$.components.diskSpace.details").doesNotExist());
    }

    @Test
    void readinessDependsOnTheCatalog() throws Exception {
        mvc.perform(get("/actuator/health/readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.catalog.status").value("UP"))
                .andExpect(jsonPath("$.components.catalog.details.products").isNumber());
    }

    @Test
    void livenessDoesNotDependOnTheCatalog() throws Exception {
        mvc.perform(get("/actuator/health/liveness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.catalog").doesNotExist());
    }

    @Test
    void addingAProductIncrementsTheCounter() throws Exception {
        double before = registry.get("shop.products.added").counter().count();
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Webcam\",\"priceInCents\":3900}"))
                .andExpect(status().isCreated());
        assertEquals(before + 1, registry.get("shop.products.added").counter().count());
    }

    @Test
    void prometheusExposesTheCounter() throws Exception {
        mvc.perform(get("/actuator/prometheus"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("shop_products_added_total")));
    }
}
