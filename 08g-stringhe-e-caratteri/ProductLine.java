public class ProductLine {
    public static void main(String[] args) {
        String line = "Mug;1500";
        String[] parts = line.split(";");
        String name = parts[0];
        int cents = Integer.parseInt(parts[1]);
        System.out.println(name + " costs " + cents + " cents");
        String[] names = {"Mug", "T-shirt", "Notebook"};
        System.out.println("Cart: " + String.join(", ", names));
        String card = "4111 1111 1111 1234";
        String digits = card.replace(" ", "");
        int hidden = digits.length() - 4;
        String masked = "*".repeat(hidden) + digits.substring(hidden);
        System.out.println("Card: " + masked);
    }
}
