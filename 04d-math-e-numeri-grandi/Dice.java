import java.util.Random;
public class Dice {
    public static void main(String[] args) {
        Random dice = new Random(42);
        int first = dice.nextInt(1, 7);
        int second = dice.nextInt(1, 7);
        int third = dice.nextInt(1, 7);
        System.out.println("rolls: " + first + ", " + second + ", " + third);
        Random twin = new Random(42);
        System.out.println("twin: " + twin.nextInt(1, 7));
        System.out.println("twin: " + twin.nextInt(1, 7));
    }
}
