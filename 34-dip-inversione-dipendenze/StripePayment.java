public class StripePayment implements PaymentGateway {
    @Override
    public void charge(int amountInCents) {
        System.out.println("charged " + amountInCents + " cents via Stripe");
    }
}
