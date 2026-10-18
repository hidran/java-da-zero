package com.shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderSaga {
    private static final Logger log = LoggerFactory.getLogger(OrderSaga.class);
    private final OrderService orders;
    private final OrderRepository repository;
    private final PaymentGateway payments;
    private final KafkaTemplate<String, Object> events;

    public OrderSaga(OrderService orders, OrderRepository repository, PaymentGateway payments,
            KafkaTemplate<String, Object> events) {
        this.orders = orders;
        this.repository = repository;
        this.payments = payments;
        this.events = events;
    }

    @KafkaListener(topics = "stock.rejected")
    public void onStockRejected(StockRejected event) {
        repository.save(orders.find(event.orderId()).withStatus(OrderStatus.CANCELLED));
        log.info("Order {} cancelled: stock not available", event.orderId());
    }

    @KafkaListener(topics = "stock.reserved")
    public void onStockReserved(StockReserved event) {
        Order order = orders.find(event.orderId());
        if (payments.charge(order.id(), order.totalInCents())) {
            repository.save(order.withStatus(OrderStatus.CONFIRMED));
            log.info("Order {} confirmed: payment accepted", order.id());
            return;
        }
        repository.save(order.withStatus(OrderStatus.CANCELLED));
        events.send("orders.cancelled", String.valueOf(order.id()),
                new OrderCancelled(order.id(), order.productId(), order.quantity()));
        log.info("Order {} cancelled: payment refused", order.id());
    }
}
