import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
public class Sets {
    public static void main(String[] args) {
        List<String> items = List.of("Tea", "Pen", "Tea", "Mug", "Pen");
        Set<String> unique = new HashSet<>(items);
        Set<String> ordered = new LinkedHashSet<>(items);
        System.out.println("Items: " + items.size());
        System.out.println("Distinct: " + unique.size());
        System.out.println("In order: " + ordered);
        System.out.println("Has Mug: " + ordered.contains("Mug"));
        System.out.println("Added again: " + ordered.add("Tea"));
    }
}
