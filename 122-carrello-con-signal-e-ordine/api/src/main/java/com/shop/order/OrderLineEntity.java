package com.shop.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_line")
public class OrderLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    private long productId;
    @Column(nullable = false, length = 100)
    private String productName;
    private int unitPriceInCents;
    private int quantity;

    protected OrderLineEntity() {
    }

    OrderLineEntity(OrderEntity order, OrderLine line) {
        this.order = order;
        this.productId = line.productId();
        this.productName = line.productName();
        this.unitPriceInCents = line.unitPriceInCents();
        this.quantity = line.quantity();
    }

    OrderLine toOrderLine() {
        return new OrderLine(productId, productName, unitPriceInCents, quantity);
    }
}
