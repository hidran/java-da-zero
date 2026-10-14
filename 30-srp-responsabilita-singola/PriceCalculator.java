public class PriceCalculator {
    private static final int VAT_PERCENT = 22;
    public Totals totals(Order order) {
        int subtotal = order.lines().stream()
                .mapToInt(line -> line.priceInCents() * line.quantity())
                .sum();
        int vat = subtotal * VAT_PERCENT / 100;
        return new Totals(subtotal, vat, subtotal + vat);
    }
}
record Totals(int subtotal, int vat, int total) {
}
