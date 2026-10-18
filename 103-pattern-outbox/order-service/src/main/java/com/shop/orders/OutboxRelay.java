package com.shop.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxRelay {
    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);

    private final Outbox outbox;
    private final MessageSender sender;

    public OutboxRelay(Outbox outbox, MessageSender sender) {
        this.outbox = outbox;
        this.sender = sender;
    }

    @Scheduled(fixedDelayString = "${outbox.relay.delay}",
            initialDelayString = "${outbox.relay.delay}")
    public void publishPending() {
        for (OutboxMessage message : outbox.pending(10)) {
            try {
                sender.send(message.topic(), message.messageKey(), message.payload());
            } catch (RuntimeException exception) {
                log.warn("Kafka unavailable: outbox message {} stays pending", message.id());
                return;
            }
            outbox.markSent(message.id());
            log.info("Outbox message {} sent to {}", message.id(), message.topic());
        }
    }
}
