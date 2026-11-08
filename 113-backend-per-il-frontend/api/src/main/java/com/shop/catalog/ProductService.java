package com.shop.catalog;

import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository products;
    private final CategoryRepository categories;

    public ProductService(ProductRepository products, CategoryRepository categories) {
        this.products = products;
        this.categories = categories;
    }

    // Thirty products fit in memory: filtering and paging the list here is enough (KISS).
    public ProductPage search(String name, Long categoryId, ProductSort sort, int page, int size) {
        String text = name.strip().toLowerCase(Locale.ROOT);
        List<Product> matching = products.findAll().stream()
                .filter(product -> product.name().toLowerCase(Locale.ROOT).contains(text))
                .filter(product -> categoryId == null || product.category().id() == categoryId)
                .sorted(sort.comparator())
                .toList();
        List<Product> content = matching.stream()
                .skip((long) page * size).limit(size).toList();
        return new ProductPage(content, page, size, matching.size());
    }

    public Product find(long id) {
        return products.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional
    public Product create(String name, String description, int priceInCents, long categoryId) {
        String cleanName = name.strip();
        if (products.existsByName(cleanName)) {
            throw new DuplicateProductNameException(cleanName);
        }
        Category category = categories.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return products.save(new Product(0, cleanName, description.strip(), priceInCents, category));
    }
}
