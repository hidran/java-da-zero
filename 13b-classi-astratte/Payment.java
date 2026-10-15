public abstract class Payment {
    private final int amountInCents;
    public Payment(int amountInCents) {
        this.amountInCents = amountInCents;
    }
    public int getAmountInCents() {
        return amountInCents;
    }
    public abstract int fee();
    public final String receipt() {
        return "Paid " + (amountInCents + fee()) + " cents, fee " + fee();
    }
}
