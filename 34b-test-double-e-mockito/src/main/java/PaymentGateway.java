public interface PaymentGateway {
    boolean charge(Charge charge);
}
