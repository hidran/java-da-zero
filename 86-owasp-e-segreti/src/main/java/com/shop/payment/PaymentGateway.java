package com.shop.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateway {
    private static final Logger log = LoggerFactory.getLogger(PaymentGateway.class);
    private final PaymentProperties properties;

    public PaymentGateway(PaymentProperties properties) {
        this.properties = properties;
        // The calls to the payment provider are not part of this lesson
        log.info("Payment gateway ready: {}", properties);
    }
}
