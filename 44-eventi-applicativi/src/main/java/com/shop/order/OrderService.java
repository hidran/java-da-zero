package com.shop.order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
@Service
public class OrderService {
    private final ApplicationEventPublisher events;
    public OrderService(ApplicationEventPublisher events) {
        this.events = events;
    }
    public void placeOrder(String orderId, int totalInCents) {
        System.out.println("Order " + orderId + " saved");
        events.publishEvent(new OrderPlaced(orderId, totalInCents));
        System.out.println("Order " + orderId + " completed");
    }
}
