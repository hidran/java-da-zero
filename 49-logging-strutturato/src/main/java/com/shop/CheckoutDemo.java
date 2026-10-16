package com.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CheckoutDemo implements CommandLineRunner {
    private final CheckoutService checkout;

    public CheckoutDemo(CheckoutService checkout) {
        this.checkout = checkout;
    }

    @Override
    public void run(String... args) {
        checkout.checkout("ORD-1042", 2500, 490);
    }
}
