package com.shop.domain;
import java.util.List;
public record Order(List<OrderLine> lines) {
    private static final int FREE_SHIPPING_FROM = 5000;
    private static final int STANDARD_FEE = 500;
    private static final int REDUCED_SHIPPING_FROM = 3000;
    private static final int REDUCED_FEE = 200;
    public Order {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Order must have lines");
        }
        lines = List.copyOf(lines);
    }
    public int totalInCents() {
        return lines.stream().mapToInt(OrderLine::subtotalInCents).sum();
    }
    public int shippingInCents() {
        int total = totalInCents();
        if (total >= FREE_SHIPPING_FROM) {
            return 0;
        }
        if (total >= REDUCED_SHIPPING_FROM) {
            return REDUCED_FEE;
        }
        return STANDARD_FEE;
    }
}
