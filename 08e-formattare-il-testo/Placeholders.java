public class Placeholders {
    public static void main(String[] args) {
        String name = "Anna";
        int items = 3;
        double total = 42.5;
        String message = "Hi %s, you have %d items".formatted(name, items);
        System.out.println(message);
        System.out.printf("Total: %.2f euro%n", total);
        System.out.println("Joined: " + 10.0 / 3);
        System.out.printf("Formatted: %.2f%n", 10.0 / 3);
    }
}
