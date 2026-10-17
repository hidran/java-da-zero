package com.shop.catalog;

import java.util.List;

public record ProductPage(List<Product> content, int page, int size, long totalElements) {
    public int totalPages() {
        return (int) ((totalElements + size - 1) / size);
    }
}
