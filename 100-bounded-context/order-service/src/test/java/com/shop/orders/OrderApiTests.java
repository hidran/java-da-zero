package com.shop.orders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    OrderService service;

    @Test
    void placesAnOrderWithItsOwnCopyOfTheProduct() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.productName").value("Keyboard"))
                .andExpect(jsonPath("$.unitPriceInCents").value(4900))
                .andExpect(jsonPath("$.totalInCents").value(9800));
    }

    @Test
    void readsAPlacedOrder() throws Exception {
        Order order = service.place(2L, 1);
        mvc.perform(get("/api/orders/" + order.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Mouse"))
                .andExpect(jsonPath("$.totalInCents").value(2500));
    }

    @Test
    void rejectsAnUnknownProduct() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":99,\"quantity\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("https://api.shop.example/problems/product-not-found"));
    }
}
