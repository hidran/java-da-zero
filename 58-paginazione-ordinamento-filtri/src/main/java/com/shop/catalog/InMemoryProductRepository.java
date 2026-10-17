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
    private static final List<Product> DEMO_PRODUCTS = List.of(
            new Product(1, "Keyboard", 4900), new Product(2, "Mouse", 2500),
            new Product(3, "Monitor", 18900), new Product(4, "Webcam", 5900),
            new Product(5, "Headset", 7900), new Product(6, "Laptop stand", 3900),
            new Product(7, "USB hub", 2900), new Product(8, "Desk lamp", 3400),
            new Product(9, "Microphone", 9900), new Product(10, "Speakers", 6900),
            new Product(11, "Mouse pad", 1500), new Product(12, "Docking station", 15900));

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(DEMO_PRODUCTS.size());

    public InMemoryProductRepository() {
        DEMO_PRODUCTS.forEach(product -> products.put(product.id(), product));
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
