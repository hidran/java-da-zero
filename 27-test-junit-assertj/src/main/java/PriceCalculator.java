public class PriceCalculator {
    public int totalInCents(int unitPriceInCents, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return unitPriceInCents * quantity;
    }
    public int applyDiscount(int priceInCents, int percent) {
        return priceInCents - priceInCents * percent / 100;
    }
}
