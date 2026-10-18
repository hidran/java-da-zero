package com.shop.catalog;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> list() {
        return repository.findAll();
    }

    public Product find(long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(String name, int priceInCents) {
        return repository.save(new Product(0, name, priceInCents));
    }

    public CatalogStats stats() {
        List<Product> products = repository.findAll();
        long total = products.stream().mapToLong(Product::priceInCents).sum();
        return new CatalogStats(products.size(), total);
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
