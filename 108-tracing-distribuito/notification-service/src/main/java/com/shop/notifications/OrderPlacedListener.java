package com.shop.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener {
    private static final Logger log = LoggerFactory.getLogger(OrderPlacedListener.class);

    @KafkaListener(topics = "orders.placed")
    public void onOrderPlaced(OrderPlaced order) {
        log.info("Confirmation sent for order {}", order.orderId());
    }
}
