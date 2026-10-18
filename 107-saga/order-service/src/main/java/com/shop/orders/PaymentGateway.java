package com.shop.orders;

public interface PaymentGateway {
    boolean charge(long orderId, int amountInCents);
}
