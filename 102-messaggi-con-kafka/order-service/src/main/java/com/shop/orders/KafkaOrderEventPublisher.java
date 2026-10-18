package com.shop.orders;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {
    static final String TOPIC = "orders.placed";

    private final KafkaTemplate<String, OrderPlaced> kafka;

    public KafkaOrderEventPublisher(KafkaTemplate<String, OrderPlaced> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void publish(OrderPlaced event) {
        kafka.send(TOPIC, String.valueOf(event.orderId()), event);
    }
}
