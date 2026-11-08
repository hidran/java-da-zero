package com.shop.web;

import com.shop.order.Order;
import com.shop.order.OrderItem;
import com.shop.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Place an order from the cart")
@RestController
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Place an order")
    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse place(@Valid @RequestBody PlaceOrderRequest request) {
        List<OrderItem> items = request.lines().stream()
                .map(line -> new OrderItem(line.productId(), line.quantity()))
                .toList();
        Order order = service.place(items);
        return OrderResponse.from(order);
    }
}
