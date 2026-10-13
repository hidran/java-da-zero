public class CardPayment implements PaymentMethod {
    private final String lastDigits;
    public CardPayment(String lastDigits) {
        this.lastDigits = lastDigits;
    }
    @Override
    public String pay(int amountInCents) {
        return "Paid " + amountInCents + " cents by card ending " + lastDigits;
    }
}
