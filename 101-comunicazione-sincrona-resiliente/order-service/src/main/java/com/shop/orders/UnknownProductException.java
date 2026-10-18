package com.shop.orders;

public class UnknownProductException extends RuntimeException {
    public UnknownProductException(long productId) {
        super("Product " + productId + " does not exist");
    }
}
