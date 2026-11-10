import java.util.Arrays;
public class PriceList {
    public static void main(String[] args) {
        int[] prices = {1500, 705, 2490, 990};
        int[] sorted = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sorted);
        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("Sorted: " + Arrays.toString(sorted));
        System.out.println("Index of 1500: " + Arrays.binarySearch(sorted, 1500));
        int[] expected = {705, 990, 1500, 2490};
        System.out.println("With ==: " + (sorted == expected));
        System.out.println("With equals: " + Arrays.equals(sorted, expected));
        int[] newShelf = new int[4];
        Arrays.fill(newShelf, 10);
        System.out.println("New shelf: " + Arrays.toString(newShelf));
    }
}
