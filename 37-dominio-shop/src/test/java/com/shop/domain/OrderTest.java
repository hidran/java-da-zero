package com.shop.domain;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class OrderTest {
    private final Product book = new Product("BK-1", "Book", 1500);
    private final Product pen = new Product("PN-1", "Pen", 200);
    @Test
    void totalSumsTheLineSubtotals() {
        OrderLine books = new OrderLine(book, 2);
        OrderLine pens = new OrderLine(pen, 3);
        Order order = new Order(List.of(books, pens));
        assertThat(order.totalInCents()).isEqualTo(3600);
    }
    @Test
    void keepsItsOwnCopyOfTheLines() {
        List<OrderLine> lines = new ArrayList<>();
        lines.add(new OrderLine(pen, 1));
        Order order = new Order(lines);
        lines.clear();
        assertThat(order.lines()).hasSize(1);
    }
    @Test
    void rejectsAnEmptyOrder() {
        assertThatThrownBy(() -> new Order(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order must have lines");
    }
}
