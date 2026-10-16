package com.shop;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalog {
    private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public ProductCatalog() {
        products.put(1L, new Product(1L, "Keyboard", 4900));
    }

    public List<Product> all() {
        return products.values().stream().sorted(Comparator.comparingLong(Product::id)).toList();
    }

    public Optional<Product> find(long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product create(String name, int priceInCents) {
        if (name == null || name.isBlank() || priceInCents < 0) {
            throw new IllegalArgumentException("Invalid product");
        }
        long id = sequence.incrementAndGet();
        Product product = new Product(id, name, priceInCents);
        products.put(id, product);
        return product;
    }

    public boolean delete(long id) {
        return products.remove(id) != null;
    }
}
