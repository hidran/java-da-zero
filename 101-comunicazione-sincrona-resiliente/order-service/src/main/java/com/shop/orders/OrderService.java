package com.shop.orders;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final ProductLookup products;
    private final OrderRepository orders;

    public OrderService(ProductLookup products, OrderRepository orders) {
        this.products = products;
        this.orders = orders;
    }

    public Order place(long productId, int quantity) {
        ProductSnapshot product = products.find(productId)
                .orElseThrow(() -> new UnknownProductException(productId));
        Order order = new Order(orders.nextId(), product.productId(), product.name(),
                product.priceInCents(), quantity);
        orders.save(order);
        return order;
    }

    public Optional<Order> find(long id) {
        return orders.findById(id);
    }
}
