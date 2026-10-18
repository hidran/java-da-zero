package com.shop.orders;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageSender implements MessageSender {
    private final KafkaTemplate<String, String> kafka;

    public KafkaMessageSender(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void send(String topic, String key, String payload) {
        kafka.send(topic, key, payload).join();
    }
}
