package com.shop.catalog;

import com.shop.audit.Audited;
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

    @Audited("product-created")
    public Product create(String name, int priceInCents) {
        return repository.save(new Product(0, name, priceInCents));
    }

    @Audited("product-deleted")
    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
