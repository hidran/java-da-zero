public class Wallet {
    private int balanceInCents;
    public Wallet(int balanceInCents) {
        this.balanceInCents = balanceInCents;
    }
    public int getBalanceInCents() {
        return balanceInCents;
    }
    public void withdraw(int amountInCents) {
        if (amountInCents <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amountInCents > balanceInCents) {
            String message = "Balance " + balanceInCents + ", requested " + amountInCents;
            throw new InsufficientFundsException(message);
        }
        balanceInCents -= amountInCents;
    }
}
