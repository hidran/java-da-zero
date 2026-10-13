public class Main {
    public static void main(String[] args) {
        PaymentMethod method = new CardPayment("4242");
        System.out.println(method.pay(1250));
        method = new CashPayment();
        System.out.println(method.pay(1250));
        checkout(new CardPayment("1881"), 3000);
        checkout(new CashPayment(), 499);
    }
    static void checkout(PaymentMethod method, int amount) {
        System.out.println("Checkout: " + method.pay(amount));
    }
}
