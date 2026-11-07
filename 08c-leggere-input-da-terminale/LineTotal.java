public class LineTotal {
    public static void main(String[] args) {
        String name = IO.readln("Product: ");
        String text = IO.readln("Quantity: ");
        int quantity = Integer.parseInt(text);
        int priceInCents = 1500;
        System.out.println(name + " x " + quantity + " = " + quantity * priceInCents + " cents");
    }
}
