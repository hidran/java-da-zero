import java.util.List;
public class Main {
    static int totalShipping(List<Shippable> parcels) {
        return parcels.stream()
                .mapToInt(Shippable::shippingCostInCents)
                .sum();
    }
    public static void main(String[] args) {
        PhysicalProduct book = new PhysicalProduct("Book", 1500);
        PhysicalProduct mug = new PhysicalProduct("Mug", 800);
        DigitalProduct ebook = new DigitalProduct("Ebook", 900);
        List<Product> cart = List.of(book, mug, ebook);
        List<Shippable> parcels = List.of(book, mug);
        int price = cart.stream().mapToInt(Product::getPriceInCents).sum();
        System.out.println("Cart price: " + price);
        System.out.println("Shipping: " + totalShipping(parcels));
    }
}
