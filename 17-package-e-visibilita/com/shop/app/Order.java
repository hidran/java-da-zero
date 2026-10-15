package com.shop.app;
import com.shop.catalog.Product;
public class Order {
    public static class Line {
        private final Product product;
        private final int quantity;
        public Line(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
    private final Line[] lines;
    public Order(Line[] lines) {
        this.lines = lines;
    }
    public int totalInCents() {
        int total = 0;
        for (Line line : lines) {
            total += line.product.priceInCents() * line.quantity;
        }
        return total;
    }
}
