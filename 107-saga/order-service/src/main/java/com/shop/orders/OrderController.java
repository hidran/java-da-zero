package com.shop.orders;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> place(@RequestBody PlaceOrderRequest request) {
        Order order = service.place(request.productId(), request.quantity());
        return ResponseEntity.created(URI.create("/api/orders/" + order.id())).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public OrderResponse order(@PathVariable long id) {
        return OrderResponse.from(service.find(id));
    }
}
