public class OrderService {
    private final PaymentGateway payment;
    public OrderService(PaymentGateway payment) {
        this.payment = payment;
    }
    public String placeOrder(String orderId, int amountInCents) {
        if (amountInCents <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        int shipping = amountInCents < 2000 ? 500 : 0;
        boolean paid = payment.charge(new Charge(orderId, amountInCents + shipping));
        return paid ? "order " + orderId + " placed" : "order " + orderId + " declined";
    }
}
