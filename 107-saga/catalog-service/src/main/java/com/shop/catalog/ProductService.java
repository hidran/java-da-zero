package com.shop.catalog;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final Map<Long, Product> products = Map.of(
            1L, new Product(1L, "Keyboard", 4900),
            2L, new Product(2L, "Mouse", 2500),
            3L, new Product(3L, "Monitor", 18900));

    public Product find(long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }
}
