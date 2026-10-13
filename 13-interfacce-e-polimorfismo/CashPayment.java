public class CashPayment implements PaymentMethod {
    @Override
    public String pay(int amountInCents) {
        return "Paid " + amountInCents + " cents in cash";
    }
}
