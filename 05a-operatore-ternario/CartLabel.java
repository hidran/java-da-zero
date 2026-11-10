public class CartLabel {
    public static void main(String[] args) {
        int count = 1;
        String label = count == 1 ? "item" : "items";
        System.out.println("Cart: " + count + " " + label);
        count = 3;
        System.out.println("Cart: " + count + " " + (count == 1 ? "item" : "items"));
        count = 0;
        int totalInCents = 0;
        int averageInCents = count == 0 ? 0 : totalInCents / count;
        System.out.println("Average: " + averageInCents + " cents");
    }
}
