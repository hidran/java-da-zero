package com.shop.notifications;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(topics = "orders.placed")
class OrderPlacedListenerTests {
    @Autowired
    KafkaTemplate<String, String> kafka;

    @MockitoBean
    NotificationSender sender;

    @Test
    void sendsANotificationForEveryOrderPlaced() {
        kafka.send("orders.placed", "7", "{\"orderId\":7,\"productId\":1,\"quantity\":2,\"totalInCents\":9800}");
        verify(sender, timeout(15_000)).send("order 7 placed, total 9800 cents");
    }
}
