public class BitOperators {
    public static void main(String[] args) {
        int a = 0b1100;
        int b = 0b1010;
        System.out.println("a = " + Integer.toBinaryString(a));
        System.out.println("b = " + Integer.toBinaryString(b));
        System.out.println("a & b = " + Integer.toBinaryString(a & b));
        System.out.println("a | b = " + Integer.toBinaryString(a | b));
        System.out.println("a ^ b = " + Integer.toBinaryString(a ^ b));
        System.out.println("~a = " + (~a));
        System.out.println("255 in hex: " + Integer.toHexString(255));
        System.out.println("1 << 3 = " + (1 << 3) + ", 40 >> 2 = " + (40 >> 2));
    }
}
