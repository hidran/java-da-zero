package com.shop.catalog;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCatalog {
    private final Map<Long, Product> products = Map.of(
            1L, new Product(1L, "Keyboard", "Mechanical keyboard, Italian layout", 4900),
            2L, new Product(2L, "Mouse", "Wireless mouse", 2500),
            3L, new Product(3L, "Monitor", "27 inch monitor", 18900));

    public Optional<Product> find(long id) {
        return Optional.ofNullable(products.get(id));
    }
}
