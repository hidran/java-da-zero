package com.shop.catalog;
import java.util.Map;
public class InMemoryProductRepository {
    private final Map<String, Integer> prices = Map.of("pen", 150, "notebook", 400);
    public int priceOf(String name) {
        return prices.get(name);
    }
}
