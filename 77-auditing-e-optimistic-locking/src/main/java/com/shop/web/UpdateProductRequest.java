package com.shop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(
        @NotBlank String name,
        @Positive int priceInCents,
        @PositiveOrZero long version) {
}
