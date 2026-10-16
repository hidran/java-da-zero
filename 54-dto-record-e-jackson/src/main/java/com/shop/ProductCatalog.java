package com.shop;

import org.springframework.stereotype.Service;

@Service
public class ProductCatalog {
    public Product featured() {
        return new Product(1L, "Keyboard", 4900, 2100);
    }
}
