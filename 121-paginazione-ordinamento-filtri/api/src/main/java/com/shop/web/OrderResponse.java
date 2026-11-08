package com.shop.web;

import com.shop.order.Order;
import com.shop.order.OrderLine;
import com.shop.order.OrderStatus;
import java.util.List;

public record OrderResponse(long id, OrderStatus status, List<Line> lines, int totalInCents) {
    public record Line(long productId, String productName, int unitPriceInCents, int quantity, int subtotalInCents) {
        static Line from(OrderLine line) {
            return new Line(line.productId(), line.productName(), line.unitPriceInCents(),
                    line.quantity(), line.subtotalInCents());
        }
    }

    public static OrderResponse from(Order order) {
        List<Line> lines = order.lines().stream().map(Line::from).toList();
        return new OrderResponse(order.id(), order.status(), lines, order.totalInCents());
    }
}
