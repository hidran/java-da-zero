package com.shop.catalog;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(int priceInCents) {
        super("Price must be positive: " + priceInCents);
    }
}
