public class Main {
    public static void main(String[] args) {
        OrderStatus status = OrderStatus.PAID;
        String message = switch (status) {
            case NEW -> "Waiting for payment";
            case PAID -> "Ready to ship";
            case SHIPPED -> "On its way";
        };
        System.out.println(status + ": " + message);
        for (OrderStatus each : OrderStatus.values()) {
            System.out.println(each + " final? " + each.isFinal());
        }
        PaymentMethod card = new CardPayment(2500, "4242");
        PaymentMethod cash = new CashPayment(1200);
        System.out.println(card.describe());
        System.out.println(cash.describe());
    }
}
