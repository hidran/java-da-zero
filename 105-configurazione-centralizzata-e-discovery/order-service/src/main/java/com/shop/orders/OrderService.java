package com.shop.orders;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final CatalogClient catalog;
    private final OrderRepository orders;

    public OrderService(CatalogClient catalog, OrderRepository orders) {
        this.catalog = catalog;
        this.orders = orders;
    }

    public Order place(long productId, int quantity) {
        ProductView product = catalog.product(productId);
        return orders.save(productId, quantity, product.priceInCents() * quantity);
    }

    public Order find(long id) {
        return orders.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
