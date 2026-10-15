package com.shop.payment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
@Component
@Primary
public class CardPayment implements PaymentMethod {
    @Override
    public String pay(int amountInCents) {
        return amountInCents + " cents by card";
    }
}
