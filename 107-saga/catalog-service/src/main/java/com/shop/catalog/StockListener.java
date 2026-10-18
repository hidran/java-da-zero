package com.shop.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockListener {
    private static final Logger log = LoggerFactory.getLogger(StockListener.class);
    private final StockService stock;
    private final KafkaTemplate<String, Object> events;

    public StockListener(StockService stock, KafkaTemplate<String, Object> events) {
        this.stock = stock;
        this.events = events;
    }

    @KafkaListener(topics = "orders.placed")
    public void onOrderPlaced(OrderPlaced order) {
        String key = String.valueOf(order.orderId());
        if (stock.reserve(order.productId(), order.quantity())) {
            log.info("Stock reserved for order {}", order.orderId());
            events.send("stock.reserved", key, new StockReserved(order.orderId()));
        } else {
            log.info("Stock rejected for order {}", order.orderId());
            events.send("stock.rejected", key, new StockRejected(order.orderId()));
        }
    }
    @KafkaListener(topics = "orders.cancelled")
    public void onOrderCancelled(OrderCancelled order) {
        stock.release(order.productId(), order.quantity());
        log.info("Stock released for order {}", order.orderId());
    }
}
