public final class CardPayment extends Payment {
    public CardPayment(int amountInCents) {
        super(amountInCents);
    }
    @Override
    public int fee() {
        return getAmountInCents() * 2 / 100;
    }
}
