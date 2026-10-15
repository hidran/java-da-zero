public class Main {
    public static void main(String[] args) {
        Payment[] payments = {new CardPayment(2500), new CashPayment(2500)};
        for (Payment payment : payments) {
            System.out.println(payment.receipt());
        }
        Discount sale = new SummerSale();
        Payment discounted = new CardPayment(sale.apply(2500));
        System.out.println(discounted.receipt());
        System.out.println("Saving: " + sale.saving(2500));
    }
}
