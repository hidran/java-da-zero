package com.shop;

import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    public int fee(int subtotalInCents) {
        if (subtotalInCents < 0) {
            throw new IllegalArgumentException("Subtotal must be non-negative");
        }
        return subtotalInCents >= 5000 ? 0 : 490;
    }
}
