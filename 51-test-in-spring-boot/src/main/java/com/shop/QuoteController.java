package com.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    private final ShippingService shipping;

    public QuoteController(ShippingService shipping) {
        this.shipping = shipping;
    }

    @GetMapping("/shipping")
    public int quote(@RequestParam int subtotalInCents) {
        return shipping.fee(subtotalInCents);
    }
}
