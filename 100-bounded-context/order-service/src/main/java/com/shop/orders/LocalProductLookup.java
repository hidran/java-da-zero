package com.shop.orders;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class LocalProductLookup implements ProductLookup {
    private final Map<Long, ProductSnapshot> products = Map.of(
            1L, new ProductSnapshot(1L, "Keyboard", 4900),
            2L, new ProductSnapshot(2L, "Mouse", 2500),
            3L, new ProductSnapshot(3L, "Monitor", 18900));

    @Override
    public Optional<ProductSnapshot> find(long productId) {
        return Optional.ofNullable(products.get(productId));
    }
}
