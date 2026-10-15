package com.shop.payment;
import org.springframework.stereotype.Component;
@Component
public class CashPayment implements PaymentMethod {
    @Override
    public String pay(int amountInCents) {
        return amountInCents + " cents in cash";
    }
}
