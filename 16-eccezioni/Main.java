public class Main {
    public static void main(String[] args) {
        Wallet wallet = new Wallet(1000);
        try {
            wallet.withdraw(300);
            System.out.println("Withdrawn 300, balance " + wallet.getBalanceInCents());
            wallet.withdraw(900);
            System.out.println("Withdrawn 900");
        } catch (InsufficientFundsException e) {
            System.out.println("Payment refused: " + e.getMessage());
        } finally {
            System.out.println("Final balance " + wallet.getBalanceInCents());
        }
        System.out.println("Goodbye");
    }
}
