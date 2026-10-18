package com.shop.orders;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final ProductLookup products;
    private final OrderRepository orders;
    private final OrderEventPublisher events;

    public OrderService(ProductLookup products, OrderRepository orders, OrderEventPublisher events) {
        this.products = products;
        this.orders = orders;
        this.events = events;
    }

    public Order place(long productId, int quantity) {
        ProductSnapshot product = products.find(productId)
                .orElseThrow(() -> new UnknownProductException(productId));
        Order order = new Order(orders.nextId(), product.productId(), product.name(),
                product.priceInCents(), quantity);
        orders.save(order);
        events.publish(OrderPlaced.from(order));
        return order;
    }

    public Optional<Order> find(long id) {
        return orders.findById(id);
    }
}
