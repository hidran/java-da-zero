package com.shop.catalog;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Product find(long id) {
        LOGGER.info("Finding product {}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        LOGGER.info("Found product {}", product.name());
        return product;
    }

    public Product create(String name, int priceInCents) {
        return repository.save(new Product(0, name, priceInCents));
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
