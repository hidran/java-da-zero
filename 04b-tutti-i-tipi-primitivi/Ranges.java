public class Ranges {
    public static void main(String[] args) {
        System.out.println("byte min: " + Byte.MIN_VALUE);
        System.out.println("short max: " + Short.MAX_VALUE);
        System.out.println("int max: " + Integer.MAX_VALUE);
        short stock = 1_500;
        long yearlyCents = 3_000_000_000L;
        int red = 0xFF;
        int mask = 0b1010;
        System.out.println("stock: " + stock + ", yearly: " + yearlyCents);
        System.out.println("hex: " + red + ", binary: " + mask);
    }
}
