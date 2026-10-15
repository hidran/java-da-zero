package com.shop.cart;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
@Component
@Scope("prototype")
public class ShoppingCart {
    private final List<String> items = new ArrayList<>();
    public ShoppingCart() {
        System.out.println("Cart created");
    }
    public void add(String productName) {
        items.add(productName);
    }
    public List<String> items() {
        return List.copyOf(items);
    }
}
