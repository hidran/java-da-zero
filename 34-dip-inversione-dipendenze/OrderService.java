public class OrderService {
    private final PaymentGateway payment;
    public OrderService(PaymentGateway payment) {
        this.payment = payment;
    }
    public void placeOrder(String orderId, int amountInCents) {
        int shipping = amountInCents < 2000 ? 500 : 0;
        payment.charge(amountInCents + shipping);
        System.out.println("order " + orderId + " placed");
    }
}
