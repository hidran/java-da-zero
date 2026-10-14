public class OrderService {
    private final PriceCalculator calculator = new PriceCalculator();
    private final ReceiptFormatter formatter = new ReceiptFormatter();
    public void checkout(Order order) {
        Totals totals = calculator.totals(order);
        System.out.println(formatter.format(order, totals));
        System.out.println("Order saved for " + order.customer());
    }
}
