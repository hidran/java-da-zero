package com.shop.orders;

import org.springframework.stereotype.Component;

@Component
public class DemoPaymentGateway implements PaymentGateway {
    static final int CARD_LIMIT_IN_CENTS = 30000;

    @Override
    public boolean charge(long orderId, int amountInCents) {
        return amountInCents <= CARD_LIMIT_IN_CENTS;
    }
}
