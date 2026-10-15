package com.shop;
import com.shop.cart.ShoppingCart;
import com.shop.config.ShopConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ShopConfig.class)) {
            System.out.println("Context ready");
            ShoppingCart anna = context.getBean(ShoppingCart.class);
            ShoppingCart marco = context.getBean(ShoppingCart.class);
            System.out.println("Same cart: " + (anna == marco));
            anna.add("Keyboard");
            System.out.println("Marco sees: " + marco.items());
            System.out.println("Closing the context");
        }
        System.out.println("End of main");
    }
}
