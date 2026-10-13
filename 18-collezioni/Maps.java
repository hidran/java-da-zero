import java.util.LinkedHashMap;
import java.util.Map;
public class Maps {
    public static void main(String[] args) {
        Map<String, Integer> quantities = new LinkedHashMap<>();
        quantities.put("Coffee", 2);
        quantities.put("Book", 1);
        quantities.put("Coffee", 3);
        System.out.println("Coffee: " + quantities.get("Coffee"));
        System.out.println("Pen: " + quantities.get("Pen"));
        System.out.println("Pen or 0: " + quantities.getOrDefault("Pen", 0));
        for (Map.Entry<String, Integer> entry : quantities.entrySet()) {
            System.out.println(entry.getKey() + " x " + entry.getValue());
        }
    }
}
