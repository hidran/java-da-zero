import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
public class Main {
    public static void main(String[] args) throws IOException {
        Path exports = Path.of("exports");
        Path year = exports.resolve("2026");
        Path october = year.resolve("10");
        Path file = october.resolve("orders-01.csv");
        System.out.println("File: " + file);
        Path messy = october.resolve("../09");
        Path september = messy.normalize();
        System.out.println(messy + " becomes " + september);
        Files.createDirectories(october);
        Files.createDirectories(september);
        Files.writeString(file, "");
        Files.writeString(october.resolve("orders-15.csv"), "");
        Files.writeString(october.resolve("notes.txt"), "");
        Files.writeString(september.resolve("orders-30.csv"), "");
        try (Stream<Path> months = Files.list(year)) {
            months.map(Path::getFileName)
                    .sorted()
                    .forEach(p -> System.out.println("Month: " + p));
        }
        try (Stream<Path> tree = Files.walk(exports)) {
            tree.filter(Files::isRegularFile)
                    .map(exports::relativize)
                    .sorted()
                    .forEach(p -> System.out.println("Tree: " + p));
        }
        String glob = "orders-*.csv";
        try (var orders = Files.newDirectoryStream(october, glob)) {
            for (Path order : orders) {
                System.out.println("Order: " + order.getFileName());
            }
        }
    }
}
