package com.shop.payment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Component
@Profile("prod")
public class BankPaymentGateway implements PaymentGateway {
    @Override
    public String charge(int amountInCents, String currency) {
        return "Bank charge: " + amountInCents + " cents in " + currency;
    }
}
