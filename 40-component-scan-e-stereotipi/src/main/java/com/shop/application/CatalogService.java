package com.shop.application;
import com.shop.domain.Product;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class CatalogService {
    private final ProductRepository repository;
    public CatalogService(ProductRepository repository) {
        this.repository = repository;
    }
    public void add(String name, int priceInCents) {
        repository.save(new Product(name, priceInCents));
    }
    public List<Product> products() {
        return repository.findAll();
    }
}
