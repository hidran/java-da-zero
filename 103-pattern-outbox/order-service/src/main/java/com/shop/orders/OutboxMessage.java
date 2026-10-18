package com.shop.orders;

public record OutboxMessage(long id, String topic, String messageKey, String payload) {
}
