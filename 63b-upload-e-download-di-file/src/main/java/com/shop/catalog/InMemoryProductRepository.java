package com.shop.catalog;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public InMemoryProductRepository() {
        products.put(1L, new Product(1L, "Keyboard", 4900));
    }

    @Override
    public List<Product> findAll() {
        return products.values().stream().sorted(Comparator.comparingLong(Product::id)).toList();
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Product save(Product product) {
        long id = product.id() == 0 ? sequence.incrementAndGet() : product.id();
        Product stored = new Product(id, product.name(), product.priceInCents());
        products.put(id, stored);
        return stored;
    }

    @Override
    public boolean deleteById(long id) {
        return products.remove(id) != null;
    }
}
