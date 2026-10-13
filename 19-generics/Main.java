import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Coffee");
        names.add("Tea");
        String first = names.get(0);
        System.out.println("First: " + first.toUpperCase());
        String second = names.get(1);
        System.out.println("Second: " + second.toUpperCase());
    }
}
