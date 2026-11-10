import java.util.Arrays;
public class StockTotals {
    public static void main(String[] args) {
        int[][] stock = {{4, 0, 2, 6}, {1, 3, 0, 0}, {5, 2, 9, 1}};
        int[] slotTotals = new int[stock[0].length];
        for (int shelf = 0; shelf < stock.length; shelf++) {
            int shelfTotal = 0;
            for (int slot = 0; slot < stock[shelf].length; slot++) {
                shelfTotal += stock[shelf][slot];
                slotTotals[slot] += stock[shelf][slot];
            }
            System.out.println("Shelf " + shelf + ": " + shelfTotal);
        }
        System.out.println("Slot totals: " + Arrays.toString(slotTotals));
    }
}
