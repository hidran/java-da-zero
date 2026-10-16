package com.shop.notification;
import com.shop.order.OrderPlaced;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
public class EmailListener {
    @EventListener
    @Order(1)
    public void onOrderPlaced(OrderPlaced event) {
        System.out.println("Email: order " + event.orderId() + ", total " + event.totalInCents() + " cents");
    }
}
