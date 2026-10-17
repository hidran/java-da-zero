package com.shop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductForm(@NotBlank String name, @NotNull @Positive Integer priceInCents) {
    public static ProductForm empty() {
        return new ProductForm("", null);
    }
}
