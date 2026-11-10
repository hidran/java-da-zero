import java.util.Arrays;
public class PassByValue {
    public static void main(String[] args) {
        int stock = 5;
        addOne(stock);
        System.out.println("Stock in main: " + stock);
        int[] prices = {1000, 2500, 800};
        applyDiscount(prices);
        System.out.println("After discount: " + Arrays.toString(prices));
        replaceArray(prices);
        System.out.println("After replace: " + Arrays.toString(prices));
    }
    static void addOne(int count) {
        count++;
        System.out.println("Inside addOne: " + count);
    }
    static void applyDiscount(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] -= values[i] / 10;
        }
    }
    static void replaceArray(int[] values) {
        values = new int[3];
        System.out.println("Inside replaceArray: " + Arrays.toString(values));
    }
}
