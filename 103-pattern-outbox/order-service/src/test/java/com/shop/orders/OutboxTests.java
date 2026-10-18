package com.shop.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class OutboxTests {
    @Autowired
    OrderService orders;

    @Autowired
    OutboxRelay relay;

    @Autowired
    Outbox outbox;

    @Autowired
    JdbcClient jdbc;

    @MockitoBean
    ProductLookup products;

    @MockitoBean
    MessageSender sender;

    @BeforeEach
    void emptyTablesAndAKnownProduct() {
        jdbc.sql("delete from outbox").update();
        jdbc.sql("delete from orders").update();
        given(products.find(1L)).willReturn(Optional.of(new ProductSnapshot(1L, "Keyboard", 4900)));
    }

    @Test
    void savesTheEventInTheSameTransactionAsTheOrder() {
        Order order = orders.place(1L, 2);
        assertThat(outbox.pending(10)).singleElement().satisfies(message -> {
            assertThat(message.topic()).isEqualTo("orders.placed");
            assertThat(message.messageKey()).isEqualTo(String.valueOf(order.id()));
            assertThat(message.payload()).contains("\"totalInCents\":9800");
        });
        verifyNoInteractions(sender);
    }

    @Test
    void keepsTheEventWhileKafkaIsDown() {
        willThrow(new IllegalStateException("broker down")).willDoNothing()
                .given(sender).send(anyString(), anyString(), anyString());
        Order order = orders.place(1L, 2);
        relay.publishPending();
        assertThat(outbox.pending(10)).hasSize(1);
        relay.publishPending();
        assertThat(outbox.pending(10)).isEmpty();
        verify(sender, times(2)).send(eq("orders.placed"), eq(String.valueOf(order.id())),
                contains("\"orderId\":" + order.id()));
    }

    @Test
    void sendsEachEventOnlyOnce() {
        orders.place(1L, 2);
        relay.publishPending();
        relay.publishPending();
        verify(sender, times(1)).send(anyString(), anyString(), anyString());
    }
}
