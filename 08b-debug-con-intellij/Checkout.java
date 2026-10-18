public class Checkout {
    public static void main(String[] args) {
        int[] pricesInCents = {1500, 2000, 1400};
        int totalInCents = sum(pricesInCents);
        System.out.println("Items: " + pricesInCents.length);
        System.out.println("Total in cents: " + totalInCents);
    }
    static int sum(int[] prices) {
        int total = 0;
        for (int i = 0; i < prices.length; i++) {
            total += prices[i];
        }
        return total;
    }
}
