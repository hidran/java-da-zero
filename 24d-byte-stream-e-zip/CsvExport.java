import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
public class CsvExport {
    private static final String[] PRODUCTS = {"Book", "Lamp", "Caffè", "Mug"};
    private static final int[] PRICES = {1500, 4500, 300, 800};
    private static String csvLine(int id) {
        return id + "," + PRODUCTS[id % 4] + "," + PRICES[id % 4] + "\n";
    }
    public static void main(String[] args) throws IOException {
        String word = "Caffè";
        System.out.println(word + ": " + word.length() + " chars, "
                + word.getBytes(StandardCharsets.UTF_8).length + " bytes");
        Path month = Files.createDirectories(Path.of("exports", "2026-09"));
        Path csv = month.resolve("orders.csv");
        try (BufferedWriter out =
                Files.newBufferedWriter(csv, StandardCharsets.UTF_8)) {
            out.write("id,product,cents\n");
            for (int id = 1; id <= 100_000; id++) {
                out.write(csvLine(id));
            }
        }
        System.out.println("CSV: " + Files.size(csv) + " bytes");
        long count = 0;
        long total = 0;
        try (BufferedReader in =
                Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {
            in.readLine();
            String line;
            while ((line = in.readLine()) != null) {
                count++;
                total += Integer.parseInt(line.split(",")[2]);
            }
        }
        String summary = count + " orders, " + total + " cents";
        Files.writeString(month.resolve("summary.txt"), summary);
        System.out.println(summary);
    }
}
