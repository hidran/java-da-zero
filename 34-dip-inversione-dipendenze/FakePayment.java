import java.util.ArrayList;
import java.util.List;
public class FakePayment implements PaymentGateway {
    private final List<Integer> charges = new ArrayList<>();
    @Override
    public void charge(int amountInCents) {
        charges.add(amountInCents);
    }
    public List<Integer> charges() {
        return List.copyOf(charges);
    }
}
