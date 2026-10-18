package com.shop.orders;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(partitions = 1, topics = {"orders.placed", "orders.cancelled", "stock.reserved", "stock.rejected"})
@AutoConfigureMockMvc
class OrderSagaTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    KafkaTemplate<String, Object> kafka;

    @Autowired
    OrderRepository orders;

    @Autowired
    OrderEvents published;

    @MockitoBean
    CatalogClient catalog;

    @Test
    void placingAnOrderStartsTheSaga() throws Exception {
        given(catalog.product(1L)).willReturn(new ProductView(1L, "Keyboard", 4900));
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(published.events).anyMatch(event -> event.startsWith("orders.placed")));
    }

    @Test
    void reservedStockAndAcceptedPaymentConfirmTheOrder() {
        Order order = orders.create(1, 2, 9800);
        kafka.send("stock.reserved", new StockReserved(order.id()));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(orders.findById(order.id())).get().extracting(Order::status).isEqualTo(OrderStatus.CONFIRMED));
    }

    @Test
    void rejectedStockCancelsTheOrder() {
        Order order = orders.create(2, 50, 125000);
        kafka.send("stock.rejected", new StockRejected(order.id()));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(orders.findById(order.id())).get().extracting(Order::status).isEqualTo(OrderStatus.CANCELLED));
    }

    @Test
    void refusedPaymentCancelsTheOrderAndReleasesTheStock() {
        Order order = orders.create(3, 2, 37800);
        kafka.send("stock.reserved", new StockReserved(order.id()));
        String compensation = "orders.cancelled {\"orderId\":" + order.id() + ",\"productId\":3,\"quantity\":2}";
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            assertThat(orders.findById(order.id())).get().extracting(Order::status).isEqualTo(OrderStatus.CANCELLED);
            assertThat(published.events).contains(compensation);
        });
    }

    @TestConfiguration
    static class OrderEvents {
        final List<String> events = new CopyOnWriteArrayList<>();

        @KafkaListener(topics = {"orders.placed", "orders.cancelled"}, groupId = "orders-test")
        void capture(ConsumerRecord<String, String> record) {
            events.add(record.topic() + " " + record.value());
        }
    }
}
