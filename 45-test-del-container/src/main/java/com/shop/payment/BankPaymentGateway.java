package com.shop.payment;
import org.springframework.stereotype.Component;
@Component
public class BankPaymentGateway implements PaymentGateway {
    @Override
    public String charge(int amountInCents) {
        return "Bank charge: " + amountInCents + " cents";
    }
}
