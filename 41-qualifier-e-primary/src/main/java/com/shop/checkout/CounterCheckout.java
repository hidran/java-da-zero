package com.shop.checkout;
import com.shop.payment.PaymentMethod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class CounterCheckout {
    private final PaymentMethod cash;
    public CounterCheckout(@Qualifier("cashPayment") PaymentMethod cash) {
        this.cash = cash;
    }
    public String checkout(int totalInCents) {
        return cash.pay(totalInCents);
    }
}
