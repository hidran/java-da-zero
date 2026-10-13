import java.util.List;
public class Shop {
    static <T> T firstOf(List<T> items) {
        return items.get(0);
    }
    static <T extends Comparable<T>> T max(List<T> items) {
        T best = items.get(0);
        for (T item : items) {
            if (item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }
    public static void main(String[] args) {
        Box<Product> box = new Box<>(new Product("Coffee", 250));
        Product product = box.get();
        System.out.println("In the box: " + product.name());
        List<Integer> prices = List.of(250, 1500, 120);
        List<String> names = List.of("Tea", "Coffee", "Water");
        Integer firstPrice = firstOf(prices);
        String firstName = firstOf(names);
        System.out.println("First price: " + firstPrice);
        System.out.println("First name: " + firstName);
        System.out.println("Highest price: " + max(prices));
        System.out.println("Last alphabetically: " + max(names));
    }
}
