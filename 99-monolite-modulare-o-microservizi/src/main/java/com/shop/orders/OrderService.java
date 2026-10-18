package com.shop.orders;

import com.shop.catalog.Product;
import com.shop.catalog.ProductCatalog;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final ProductCatalog catalog;

    public OrderService(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    public Order place(long productId, int quantity) {
        Product product = catalog.find(productId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown product " + productId));
        return new Order(product.id(), product.name(), quantity, product.priceInCents() * quantity);
    }
}
