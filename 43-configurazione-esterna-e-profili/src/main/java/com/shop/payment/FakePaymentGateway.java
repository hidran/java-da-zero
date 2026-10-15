package com.shop.payment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
@Component
@Profile("dev")
public class FakePaymentGateway implements PaymentGateway {
    @Override
    public String charge(int amountInCents, String currency) {
        return "Fake charge: " + amountInCents + " cents in " + currency;
    }
}
