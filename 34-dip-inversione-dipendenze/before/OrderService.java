public class OrderService {
    private final StripePayment payment = new StripePayment();
    public void placeOrder(String orderId, int amountInCents) {
        int shipping = amountInCents < 2000 ? 500 : 0;
        payment.charge(amountInCents + shipping);
        System.out.println("order " + orderId + " placed");
    }
}
