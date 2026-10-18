package com.shop.catalog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService products;
    private final StockService stock;

    public ProductController(ProductService products, StockService stock) {
        this.products = products;
        this.stock = stock;
    }

    @GetMapping("/{id}")
    public ProductResponse product(@PathVariable long id) {
        return ProductResponse.from(products.find(id), stock.available(id));
    }
}
