package com.shop.catalog;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super("Product " + id + " does not exist.");
    }
}
