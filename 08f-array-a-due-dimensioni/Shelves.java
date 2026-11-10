import java.util.Arrays;
public class Shelves {
    public static void main(String[] args) {
        int[][] stock = new int[3][4];
        stock[1][2] = 7;
        System.out.println("Shelves: " + stock.length);
        System.out.println("Slots per shelf: " + stock[0].length);
        System.out.println("Shelf 1, slot 2: " + stock[1][2]);
        System.out.println(Arrays.deepToString(stock));
        int[][] boxes = {{5, 0, 2}, {1, 4}, {3}};
        System.out.println(Arrays.deepToString(boxes));
        System.out.println("Slots on shelf 1: " + boxes[1].length);
    }
}
