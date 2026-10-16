package com.shop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shop")
public record ShopSettings(String currency, int shippingInCents) {
}
