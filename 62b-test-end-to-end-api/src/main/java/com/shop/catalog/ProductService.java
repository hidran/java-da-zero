package com.shop.catalog;

import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductPage search(String name, ProductSort sort, int page, int size) {
        String text = name.strip().toLowerCase(Locale.ROOT);
        List<Product> matching = repository.findAll().stream()
                .filter(product -> product.name().toLowerCase(Locale.ROOT).contains(text))
                .sorted(sort.comparator())
                .toList();
        List<Product> content = matching.stream()
                .skip((long) page * size).limit(size).toList();
        return new ProductPage(content, page, size, matching.size());
    }

    public Product find(long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(String name, int priceInCents) {
        return repository.save(new Product(0, name.strip(), priceInCents));
    }

    public void delete(long id) {
        if (!repository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
