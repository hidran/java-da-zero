package com.shop.stock;
import com.shop.order.OrderPlaced;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
@Component
public class StockListener {
    @EventListener
    @Order(2)
    public void onOrderPlaced(OrderPlaced event) {
        System.out.println("Stock: items of order " + event.orderId() + " reserved");
    }
}
