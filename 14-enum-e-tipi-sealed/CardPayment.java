public record CardPayment(int cents, String last4) implements PaymentMethod {
    @Override
    public String describe() {
        return "Paid " + cents + " cents by card ending " + last4;
    }
}
