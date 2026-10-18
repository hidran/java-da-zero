package com.shop.web;

import com.shop.order.OrderService;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/api/orders/{id}")
    public OrderResponse find(@PathVariable long id, Principal principal) {
        return OrderResponse.from(service.findForCustomer(id, principal.getName()));
    }
}
