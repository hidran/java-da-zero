package com.shop.catalog;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
@Component
public class ProductCache {
    private final Map<String, Integer> prices = new HashMap<>();
    public ProductCache() {
        System.out.println("Cache created");
    }
    @PostConstruct
    public void load() {
        prices.put("Keyboard", 4990);
        prices.put("Mouse", 1990);
        System.out.println("Cache loaded: " + prices.size() + " products");
    }
    @PreDestroy
    public void clear() {
        prices.clear();
        System.out.println("Cache cleared");
    }
    public int priceOf(String name) {
        return prices.getOrDefault(name, 0);
    }
}
