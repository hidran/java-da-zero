package com.shop.catalog;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> list() {
        return repository.findAll();
    }

    @Cacheable(cacheNames = "products", key = "#id")
    public Product find(long id) {
        LOGGER.info("Loading product {} from the repository", id);
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(String name, int priceInCents) {
        return repository.save(new Product(0, name, priceInCents));
    }

    @CachePut(cacheNames = "products", key = "#id")
    public Product update(long id, String name, int priceInCents) {
        if (repository.findById(id).isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        return repository.save(new Product(id, name, priceInCents));
    }

    @CacheEvict(cacheNames = "products", key = "#id")
    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
