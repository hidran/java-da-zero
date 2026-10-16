package com.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutService.class);

    public int checkout(String orderId, int subtotal, int shipping) {
        if (subtotal < 0 || shipping < 0) {
            throw new IllegalArgumentException("Amounts must be non-negative");
        }
        int total = subtotal + shipping;
        LOGGER.atInfo()
                .addKeyValue("orderId", orderId)
                .addKeyValue("totalInCents", total)
                .log("checkout_completed");
        return total;
    }
}
