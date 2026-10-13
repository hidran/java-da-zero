public class Receipt {
    public static void main(String[] args) {
        System.out.println(describe(new CardPayment("4242")));
        System.out.println(describe(new CashPayment(2000)));
        System.out.println(describe(new CashPayment(5000)));
    }
    static String describe(PaymentMethod method) {
        return switch (method) {
            case CardPayment(String last4) -> "Card ending in " + last4;
            case CashPayment(int cents) when cents >= 5000 -> "Check " + cents;
            case CashPayment(int cents) -> "Cash: " + cents;
        };
    }
}
