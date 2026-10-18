package com.shop.catalog;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CatalogHealthIndicator implements HealthIndicator {
    private final ProductRepository repository;

    public CatalogHealthIndicator(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        int products = repository.findAll().size();
        if (products == 0) {
            return Health.down().withDetail("products", 0).build();
        }
        return Health.up().withDetail("products", products).build();
    }
}
