import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    PaymentGateway payment;
    @Test
    void smallOrderPaysShipping() {
        FakePayment fake = new FakePayment();
        String result = new OrderService(fake).placeOrder("T-1", 1200);
        assertThat(result).isEqualTo("order T-1 placed");
        assertThat(fake.charges()).containsExactly(new Charge("T-1", 1700));
    }
    @Test
    void declinedCardIsReported() {
        given(payment.charge(new Charge("T-2", 3000))).willReturn(false);
        String result = new OrderService(payment).placeOrder("T-2", 3000);
        assertThat(result).isEqualTo("order T-2 declined");
    }
    @Test
    void invalidAmountNeverCharges() {
        assertThatThrownBy(() -> new OrderService(payment).placeOrder("T-3", 0))
                .isInstanceOf(IllegalArgumentException.class);
        verify(payment, never()).charge(any());
    }
    @Test
    void largeOrderShipsForFree() {
        new OrderService(payment).placeOrder("T-4", 2500);
        ArgumentCaptor<Charge> captor = ArgumentCaptor.forClass(Charge.class);
        verify(payment).charge(captor.capture());
        assertThat(captor.getValue().amountInCents()).isEqualTo(2500);
    }
}
