package com.shop.web;

import com.shop.catalog.ProductPage;
import java.util.List;

public record ProductPageResponse(
        int page, int size, long totalElements, int totalPages, List<ProductResponse> content) {
    public static ProductPageResponse from(ProductPage page) {
        List<ProductResponse> content = page.content().stream().map(ProductResponse::from).toList();
        return new ProductPageResponse(
                page.page(), page.size(), page.totalElements(), page.totalPages(), content);
    }
}
