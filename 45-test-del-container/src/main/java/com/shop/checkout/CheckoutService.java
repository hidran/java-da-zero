package com.shop.checkout;
import com.shop.payment.PaymentGateway;
import org.springframework.stereotype.Service;
@Service
public class CheckoutService {
    private final PaymentGateway gateway;
    public CheckoutService(PaymentGateway gateway) {
        this.gateway = gateway;
    }
    public String checkout(int totalInCents) {
        int shipping = totalInCents < 3000 ? 500 : 0;
        return gateway.charge(totalInCents + shipping);
    }
}
