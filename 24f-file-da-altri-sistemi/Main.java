import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
public class Main {
    public static void main(String[] args) {
        Path file = Path.of("supplier/products-bom.csv");
        Charset charset = StandardCharsets.UTF_8;
        ProductCsvReader reader = new ProductCsvReader();
        try {
            ImportResult result = reader.read(file, charset);
            result.products().forEach(System.out::println);
            result.rejected().forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Import failed: " + e);
        }
    }
}
