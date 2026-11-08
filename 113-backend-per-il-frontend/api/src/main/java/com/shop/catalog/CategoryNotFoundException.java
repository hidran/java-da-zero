package com.shop.catalog;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long id) {
        super("Category " + id + " does not exist.");
    }
}
