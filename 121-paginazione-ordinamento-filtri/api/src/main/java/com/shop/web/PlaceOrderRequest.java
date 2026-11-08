package com.shop.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record PlaceOrderRequest(
        @NotEmpty(message = "The order needs at least one line")
        @Valid
        List<Line> lines
) {
    public record Line(
            @Positive(message = "Choose a product")
            long productId,
            @Min(value = 1, message = "Quantity must be at least 1")
            @Max(value = 99, message = "Quantity can be at most 99")
            int quantity
    ) {}
}
