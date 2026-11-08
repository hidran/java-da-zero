package com.shop.catalog;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categories;

    public CategoryService(CategoryRepository categories) {
        this.categories = categories;
    }

    public List<Category> list() {
        return categories.findAll();
    }
}
