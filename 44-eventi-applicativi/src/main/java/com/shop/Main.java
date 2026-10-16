package com.shop;
import com.shop.config.ShopConfig;
import com.shop.order.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ShopConfig.class)) {
            OrderService orders = context.getBean(OrderService.class);
            orders.placeOrder("A-100", 4990);
        }
    }
}
