package com.shop.web;

import com.shop.catalog.Category;

public record CategoryResponse(long id, String name) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.id(), category.name());
    }
}
