package com.shop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name can be at most 100 characters")
        String name,
        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description can be at most 500 characters")
        String description,
        @Positive(message = "Price must be greater than zero")
        int priceInCents,
        @Positive(message = "Choose a category")
        long categoryId
) {}
