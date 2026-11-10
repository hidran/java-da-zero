public class CartList {
    public static void main(String[] args) {
        String[] names = {"Mug", "T-shirt", "Notebook"};
        StringBuilder builder = new StringBuilder();
        for (String name : names) {
            if (!builder.isEmpty()) {
                builder.append(", ");
            }
            builder.append(name);
        }
        String cart = builder.toString();
        System.out.println("Cart: " + cart);
        System.out.println("Characters: " + builder.length());
    }
}
