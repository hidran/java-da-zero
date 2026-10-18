package com.shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final CatalogClient catalog;
    private final OrderRepository orders;
    private final KafkaTemplate<String, Object> events;

    public OrderService(CatalogClient catalog, OrderRepository orders, KafkaTemplate<String, Object> events) {
        this.catalog = catalog;
        this.orders = orders;
        this.events = events;
    }

    public Order place(long productId, int quantity) {
        ProductView product = catalog.product(productId);
        Order order = orders.create(productId, quantity, product.priceInCents() * quantity);
        events.send("orders.placed", String.valueOf(order.id()),
                new OrderPlaced(order.id(), productId, quantity, order.totalInCents()));
        log.info("Order {} placed: waiting for stock", order.id());
        return order;
    }

    public Order find(long id) {
        return orders.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
