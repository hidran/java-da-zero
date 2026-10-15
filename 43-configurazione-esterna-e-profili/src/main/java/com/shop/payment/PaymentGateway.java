package com.shop.payment;
public interface PaymentGateway {
    String charge(int amountInCents, String currency);
}
