public class PriceCalculator {
    public static int total(int priceInCents, int quantity) {
        return priceInCents * quantity;
    }
    public static int applyDiscount(int amountInCents, int percent) {
        return amountInCents - amountInCents * percent / 100;
    }
    public static void main(String[] args) {
        int total = applyDiscount(total(1200, 3), 10);
        System.out.println("Total with discount: " + total + " cents");
    }
}
