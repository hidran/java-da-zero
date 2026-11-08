package com.shop.order;

import java.util.List;

public interface OrderRepository {
    Order create(List<OrderLine> lines);
}
