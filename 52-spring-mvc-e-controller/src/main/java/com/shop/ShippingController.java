package com.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
    private final ShippingService shipping;

    public ShippingController(ShippingService shipping) {
        this.shipping = shipping;
    }

    @GetMapping("/api/shipping")
    public int quote(@RequestParam int subtotalInCents) {
        return shipping.fee(subtotalInCents);
    }
}
