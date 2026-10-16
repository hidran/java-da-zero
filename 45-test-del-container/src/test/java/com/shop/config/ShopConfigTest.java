package com.shop.config;
import com.shop.checkout.CheckoutService;
import com.shop.payment.BankPaymentGateway;
import com.shop.payment.PaymentGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.assertj.core.api.Assertions.assertThat;
@SpringJUnitConfig(ShopConfig.class)
class ShopConfigTest {
    private final PaymentGateway gateway;
    private final CheckoutService checkout;
    @Autowired
    ShopConfigTest(PaymentGateway gateway, CheckoutService checkout) {
        this.gateway = gateway;
        this.checkout = checkout;
    }
    @Test
    void wiresCheckoutToTheBankGateway() {
        assertThat(gateway).isInstanceOf(BankPaymentGateway.class);
        assertThat(checkout.checkout(2000)).isEqualTo("Bank charge: 2500 cents");
    }
}
