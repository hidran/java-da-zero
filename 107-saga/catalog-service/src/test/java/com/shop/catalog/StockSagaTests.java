package com.shop.catalog;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(partitions = 1, topics = {"orders.placed", "orders.cancelled", "stock.reserved", "stock.rejected"})
class StockSagaTests {
    @Autowired
    KafkaTemplate<String, Object> kafka;

    @Autowired
    StockService stock;

    @Autowired
    StockEvents published;

    @Test
    void reservesTheStockOfAPlacedOrder() {
        kafka.send("orders.placed", "1", new OrderPlaced(1, 1, 2, 9800));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            assertThat(stock.available(1)).isEqualTo(8);
            assertThat(published.events).contains("stock.reserved {\"orderId\":1}");
        });
    }

    @Test
    void rejectsAnOrderWithoutStock() {
        kafka.send("orders.placed", "2", new OrderPlaced(2, 2, 50, 125000));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(published.events).contains("stock.rejected {\"orderId\":2}"));
        assertThat(stock.available(2)).isEqualTo(5);
    }

    @Test
    void releasesTheStockOfACancelledOrder() {
        kafka.send("orders.placed", "3", new OrderPlaced(3, 3, 2, 37800));
        await().atMost(Duration.ofSeconds(10)).until(() -> stock.available(3) == 1);
        kafka.send("orders.cancelled", "3", new OrderCancelled(3, 3, 2));
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> assertThat(stock.available(3)).isEqualTo(3));
    }

    @TestConfiguration
    static class StockEvents {
        final List<String> events = new CopyOnWriteArrayList<>();

        @KafkaListener(topics = {"stock.reserved", "stock.rejected"}, groupId = "catalog-test")
        void capture(ConsumerRecord<String, String> record) {
            events.add(record.topic() + " " + record.value());
        }
    }
}
