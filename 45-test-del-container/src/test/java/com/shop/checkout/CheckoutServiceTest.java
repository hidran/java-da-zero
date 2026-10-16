package com.shop.checkout;
import com.shop.payment.PaymentGateway;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class CheckoutServiceTest {
    private final PaymentGateway fake = amountInCents -> "Fake: " + amountInCents;
    private final CheckoutService service = new CheckoutService(fake);
    @Test
    void addsShippingUnderThreshold() {
        assertThat(service.checkout(2000)).isEqualTo("Fake: 2500");
    }
    @Test
    void shipsForFreeFromThreshold() {
        assertThat(service.checkout(3000)).isEqualTo("Fake: 3000");
    }
}
