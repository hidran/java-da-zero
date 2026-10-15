package com.shop;
import com.shop.checkout.CheckoutService;
import com.shop.checkout.CounterCheckout;
import com.shop.config.ShopConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ShopConfig.class)) {
            CheckoutService online = context.getBean(CheckoutService.class);
            System.out.println("Online: " + online.checkout(4990));
            CounterCheckout counter = context.getBean(CounterCheckout.class);
            System.out.println("Counter: " + counter.checkout(1500));
        }
    }
}
