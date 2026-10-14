import java.util.List;
public class Main {
    static int totalShipping(List<Product> products) {
        return products.stream()
                .mapToInt(Product::shippingCostInCents)
                .sum();
    }
    public static void main(String[] args) {
        Product book = new Product("Book", 1500);
        Product mug = new Product("Mug", 800);
        Product ebook = new DigitalProduct("Ebook", 900);
        List<Product> physical = List.of(book, mug);
        List<Product> mixed = List.of(book, mug, ebook);
        System.out.println("Physical only: " + totalShipping(physical));
        try {
            System.out.println("With ebook: " + totalShipping(mixed));
        } catch (UnsupportedOperationException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }
}
