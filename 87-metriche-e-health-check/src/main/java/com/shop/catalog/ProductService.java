package com.shop.catalog;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final Counter addedProducts;
    private final Timer listTimer;

    public ProductService(ProductRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.addedProducts = Counter.builder("shop.products.added").register(registry);
        this.listTimer = Timer.builder("shop.products.list").register(registry);
    }

    public List<Product> list() {
        return listTimer.record(repository::findAll);
    }

    public Product find(long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(String name, int priceInCents) {
        Product product = repository.save(new Product(0, name, priceInCents));
        addedProducts.increment();
        return product;
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
