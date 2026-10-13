public sealed interface PaymentMethod permits CardPayment, CashPayment {
    String describe();
}
