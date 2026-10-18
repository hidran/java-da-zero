package com.shop.orders;

import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class OrderEvents implements OrderEventPublisher {
    static final String TOPIC = "orders.placed";

    private final Outbox outbox;
    private final JsonMapper json;

    public OrderEvents(Outbox outbox, JsonMapper json) {
        this.outbox = outbox;
        this.json = json;
    }

    @Override
    public void publish(OrderPlaced event) {
        outbox.add(TOPIC, String.valueOf(event.orderId()), json.writeValueAsString(event));
    }
}
