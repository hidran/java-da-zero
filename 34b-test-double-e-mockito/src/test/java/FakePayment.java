import java.util.ArrayList;
import java.util.List;
public class FakePayment implements PaymentGateway {
    private final List<Charge> charges = new ArrayList<>();
    @Override
    public boolean charge(Charge charge) {
        charges.add(charge);
        return true;
    }
    public List<Charge> charges() {
        return List.copyOf(charges);
    }
}
