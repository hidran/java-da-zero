package com.shop.checkout;
import com.shop.payment.PaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class CheckoutService {
    private final PaymentGateway gateway;
    private final String currency;
    private final int minimumInCents;
    public CheckoutService(PaymentGateway gateway,
            @Value("${shop.currency}") String currency,
            @Value("${shop.minimum-order:1000}") int minimumInCents) {
        this.gateway = gateway;
        this.currency = currency;
        this.minimumInCents = minimumInCents;
    }
    public String checkout(int totalInCents) {
        if (totalInCents < minimumInCents) {
            return "Minimum order: " + minimumInCents + " cents";
        }
        return gateway.charge(totalInCents, currency);
    }
}
