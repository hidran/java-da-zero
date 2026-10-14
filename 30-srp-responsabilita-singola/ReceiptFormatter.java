public class ReceiptFormatter {
    public String format(Order order, Totals totals) {
        return "Customer: " + order.customer()
                + "\nSubtotal: " + totals.subtotal()
                + "\nVAT: " + totals.vat()
                + "\nTotal: " + totals.total();
    }
}
