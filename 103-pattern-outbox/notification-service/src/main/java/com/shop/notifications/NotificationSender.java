package com.shop.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {
    private static final Logger log = LoggerFactory.getLogger(NotificationSender.class);

    public void send(String message) {
        log.info("Notification sent: {}", message);
    }
}
