package com.shop.catalog.internal;

import com.shop.catalog.Product;
import com.shop.catalog.ProductCatalog;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class InMemoryProductCatalog implements ProductCatalog {
    private final Map<Long, Product> products = Map.of(
            1L, new Product(1L, "Keyboard", 4900),
            2L, new Product(2L, "Mouse", 2500),
            3L, new Product(3L, "Monitor", 18900));

    @Override
    public Optional<Product> find(long id) {
        return Optional.ofNullable(products.get(id));
    }
}
