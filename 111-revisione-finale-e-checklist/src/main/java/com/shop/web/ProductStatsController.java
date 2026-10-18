package com.shop.web;

import com.shop.catalog.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductStatsController {
    private final ProductService service;
    public ProductStatsController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/api/products/stats")
    public CatalogStatsResponse stats() {
        return CatalogStatsResponse.from(service.stats());
    }
}
