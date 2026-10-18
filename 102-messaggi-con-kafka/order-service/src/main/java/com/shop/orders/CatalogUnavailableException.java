package com.shop.orders;

public class CatalogUnavailableException extends RuntimeException {
    public CatalogUnavailableException(Throwable cause) {
        super("The catalog is not available", cause);
    }
}
