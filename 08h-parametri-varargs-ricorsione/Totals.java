public class Totals {
    public static void main(String[] args) {
        System.out.println("No values: " + sum());
        System.out.println("One value: " + sum(1500));
        System.out.println("Three values: " + sum(1500, 2490, 705));
        int[] prices = {1000, 2500, 800};
        System.out.println("An array: " + sum(prices));
        printTotal("Cart", 1500, 2490);
    }
    static int sum(int... values) {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }
    static void printTotal(String label, int... prices) {
        System.out.println(label + ": " + sum(prices));
    }
}
