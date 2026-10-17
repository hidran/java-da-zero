package com.shop.order;

import java.util.List;

public record Order(long id, OrderStatus status, List<OrderLine> lines) {
    public int totalInCents() {
        return lines.stream().mapToInt(OrderLine::subtotalInCents).sum();
    }
}
