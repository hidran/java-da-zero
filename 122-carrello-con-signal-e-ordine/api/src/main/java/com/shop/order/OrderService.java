package com.shop.order;

import com.shop.catalog.Product;
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
    public Order place(List<OrderItem> items) {
        List<OrderLine> lines = items.stream().map(this::toLine).toList();
        return orders.create(lines);
    }

    // The price comes from the catalog, never from the browser.
    private OrderLine toLine(OrderItem item) {
        Product product = products.findById(item.productId())
                .orElseThrow(() -> new ProductNotFoundException(item.productId()));
        return new OrderLine(product.id(), product.name(), product.priceInCents(), item.quantity());
    }
}
