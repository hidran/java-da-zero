import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
public class ShippingBands {
    public static void main(String[] args) {
        NavigableMap<Integer, Integer> shipping = new TreeMap<>();
        shipping.put(5000, 0);
        shipping.put(0, 590);
        shipping.put(2000, 290);
        System.out.println("Bands: " + shipping);
        System.out.println("Lowest: " + shipping.firstKey());
        int total = 3450;
        int band = shipping.floorKey(total);
        System.out.println("Cart " + total + " pays " + shipping.get(band));
        System.out.println("Paid bands: " + shipping.headMap(5000));
        Set<String> tags = new TreeSet<>(List.of("tea", "mug", "tea", "cup"));
        System.out.println("Tags: " + tags);
    }
}
