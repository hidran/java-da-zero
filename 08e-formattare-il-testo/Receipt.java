public class Receipt {
    public static void main(String[] args) {
        String[] names = {"Mug", "T-shirt", "Notebook"};
        int[] pricesInCents = {1500, 2490, 705};
        int[] quantities = {2, 1, 3};
        System.out.printf("%-10s %3s %10s%n", "Item", "Qty", "Amount");
        int totalInCents = 0;
        for (int i = 0; i < names.length; i++) {
            int lineInCents = pricesInCents[i] * quantities[i];
            totalInCents += lineInCents;
            System.out.println(receiptLine(names[i], quantities[i], lineInCents));
        }
        System.out.printf("%-14s %10.2f%n", "Total", totalInCents / 100.0);
    }
    static String receiptLine(String name, int quantity, int cents) {
        return "%-10s %3d %10.2f".formatted(name, quantity, cents / 100.0);
    }
}
