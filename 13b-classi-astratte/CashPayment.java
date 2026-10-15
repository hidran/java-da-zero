public class CashPayment extends Payment {
    public CashPayment(int amountInCents) {
        super(amountInCents);
    }
    @Override
    public int fee() {
        return 0;
    }
}
