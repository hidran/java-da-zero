public class StripePayment implements PaymentGateway {
    @Override
    public boolean charge(Charge charge) {
        System.out.println("charged " + charge.amountInCents() + " cents via Stripe");
        return true;
    }
}
