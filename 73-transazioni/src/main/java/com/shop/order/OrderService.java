package com.shop.order;

import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final ProductRepository products;

    public OrderService(OrderRepository orders, ProductRepository products) {
        this.orders = orders;
        this.products = products;
    }

    @Transactional
    public long place(List<OrderItem> items) {
        long orderId = orders.create();
        for (OrderItem item : items) {
            if (products.findById(item.productId()).isEmpty()) {
                throw new ProductNotFoundException(item.productId());
            }
            orders.addLine(orderId, item.productId(), item.quantity());
        }
        return orderId;
    }

    @Transactional(readOnly = true)
    public Order find(long id) {
        return orders.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
