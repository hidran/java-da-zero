public record CashPayment(int cents) implements PaymentMethod {
    @Override
    public String describe() {
        return "Paid " + cents + " cents in cash";
    }
}
