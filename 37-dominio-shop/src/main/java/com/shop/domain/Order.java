package com.shop.domain;
import java.util.List;
public record Order(List<OrderLine> lines) {
    public Order {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Order must have lines");
        }
        lines = List.copyOf(lines);
    }
    public int totalInCents() {
        return lines.stream().mapToInt(OrderLine::subtotalInCents).sum();
    }
}
