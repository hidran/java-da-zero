package com.shop;
import com.shop.checkout.CheckoutService;
import com.shop.config.ShopConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles("prod");
            context.register(ShopConfig.class);
            context.refresh();
            CheckoutService checkout = context.getBean(CheckoutService.class);
            System.out.println(checkout.checkout(4990));
            System.out.println(checkout.checkout(500));
        }
    }
}
