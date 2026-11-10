public class Conversions {
    public static void main(String[] args) {
        int items = 3;
        long asLong = items;
        double asDouble = items;
        System.out.println("long: " + asLong + ", double: " + asDouble);
        double price = 9.99;
        int truncated = (int) price;
        long rounded = Math.round(price);
        System.out.println("cast: " + truncated + ", round: " + rounded);
    }
}
