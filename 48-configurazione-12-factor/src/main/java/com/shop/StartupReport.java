package com.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupReport implements CommandLineRunner {
    private final ShopSettings settings;

    public StartupReport(ShopSettings settings) {
        this.settings = settings;
    }

    @Override
    public void run(String... args) {
        System.out.println("Currency: " + settings.currency());
        System.out.println("Shipping: " + settings.shippingInCents());
    }
}
