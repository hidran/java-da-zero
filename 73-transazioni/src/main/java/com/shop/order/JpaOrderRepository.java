package com.shop.order;

import com.shop.catalog.ProductJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderRepository implements OrderRepository {
    private final OrderJpaRepository orders;
    private final OrderLineJpaRepository lines;
    private final ProductJpaRepository products;

    public JpaOrderRepository(OrderJpaRepository orders, OrderLineJpaRepository lines, ProductJpaRepository products) {
        this.orders = orders;
        this.lines = lines;
        this.products = products;
    }

    @Override
    public long create() {
        return orders.save(new OrderEntity(OrderStatus.NEW)).getId();
    }

    @Override
    public void addLine(long orderId, long productId, int quantity) {
        OrderEntity order = orders.getReferenceById(orderId);
        lines.save(new OrderLineEntity(order, products.getReferenceById(productId), quantity));
    }

    @Override
    public Optional<Order> findById(long id) {
        return orders.findById(id).map(OrderEntity::toOrder);
    }
}
