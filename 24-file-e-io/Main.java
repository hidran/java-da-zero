import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
public class Main {
    private static int priceOf(String line) {
        return Integer.parseInt(line.split(";")[2]);
    }
    public static void main(String[] args) {
        Path file = Path.of("orders.txt");
        String text = "1001;Book;1500\n"
                + "1002;Coffee;300\n"
                + "1003;Lamp;4500\n";
        try {
            Files.writeString(file, text);
            List<String> lines = Files.readAllLines(file);
            int total = 0;
            for (String line : lines) {
                total += priceOf(line);
            }
            System.out.println(lines.size() + " orders, " + total + " cents");
            try (Stream<String> stream = Files.lines(file)) {
                long big = stream.filter(line -> priceOf(line) > 1000).count();
                System.out.println("Over 1000 cents: " + big);
            }
            Path temp = Files.createTempFile("orders", ".txt");
            Files.writeString(temp, lines.getFirst());
            System.out.println("Temp copy: " + Files.readString(temp));
            Files.delete(temp);
            System.out.println("Temp deleted: " + !Files.exists(temp));
            System.out.println(Files.readString(Path.of("missing.txt")));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
