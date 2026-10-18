package com.shop.orders;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {
    private final ProductLookup products;
    private final OrderRepository orders;
    private final OrderEventPublisher events;
    private final TransactionTemplate transaction;

    public OrderService(ProductLookup products, OrderRepository orders, OrderEventPublisher events,
            TransactionTemplate transaction) {
        this.products = products;
        this.orders = orders;
        this.events = events;
        this.transaction = transaction;
    }

    public Order place(long productId, int quantity) {
        ProductSnapshot product = products.find(productId)
                .orElseThrow(() -> new UnknownProductException(productId));
        return transaction.execute(status -> {
            Order order = orders.save(product.productId(), product.name(), product.priceInCents(),
                    quantity);
            events.publish(OrderPlaced.from(order));
            return order;
        });
    }

    public Optional<Order> find(long id) {
        return orders.findById(id);
    }
}
