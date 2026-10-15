package com.shop.checkout;
import com.shop.payment.PaymentMethod;
import org.springframework.stereotype.Service;
@Service
public class CheckoutService {
    private final PaymentMethod paymentMethod;
    public CheckoutService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String checkout(int totalInCents) {
        return paymentMethod.pay(totalInCents);
    }
}
