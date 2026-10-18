package com.shop.catalog;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public InMemoryProductRepository() {
        save(new Product(0, "Keyboard", 4900));
        save(new Product(0, "Mouse", 2500));
        save(new Product(0, "Monitor", 18900));
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
        long id = product.id() > 0 ? product.id() : sequence.incrementAndGet();
        Product saved = new Product(id, product.name(), product.priceInCents());
        products.put(id, saved);
        return saved;
    }

    @Override
    public boolean deleteById(long id) {
        return products.remove(id) != null;
    }
}
