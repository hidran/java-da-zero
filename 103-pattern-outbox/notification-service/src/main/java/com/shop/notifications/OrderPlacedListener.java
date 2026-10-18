package com.shop.notifications;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedListener {
    private final NotificationSender sender;

    public OrderPlacedListener(NotificationSender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = "orders.placed")
    public void on(OrderPlaced event) {
        sender.send("order " + event.orderId() + " placed, total "
                + event.totalInCents() + " cents");
    }
}
