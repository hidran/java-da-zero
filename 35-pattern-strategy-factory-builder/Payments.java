public class Payments {
    interface PaymentMethod {
        String pay(int cents);
        static PaymentMethod of(String type) {
            return switch (type) {
                case "card" -> new CardPayment();
                case "cash" -> new CashPayment();
                default -> throw new IllegalArgumentException(
                        "Unknown payment: " + type);
            };
        }
    }
    private static class CardPayment implements PaymentMethod {
        @Override
        public String pay(int cents) {
            return "Card charged " + cents;
        }
    }
    private static class CashPayment implements PaymentMethod {
        @Override
        public String pay(int cents) {
            return "Cash received " + cents;
        }
    }
    public static void main(String[] args) {
        PaymentMethod card = PaymentMethod.of("card");
        System.out.println(card.pay(2500));
        System.out.println(PaymentMethod.of("cash").pay(1200));
        try {
            PaymentMethod.of("crypto");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
