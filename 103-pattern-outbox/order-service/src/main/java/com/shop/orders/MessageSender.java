package com.shop.orders;

public interface MessageSender {
    void send(String topic, String key, String payload);
}
