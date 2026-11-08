import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public final class ProductCsvReader {
    private static final String HEADER = "id;name;priceInCents";
    private static final String BOM = "\uFEFF";
    public ImportResult read(Path file, Charset charset)
            throws IOException {
        List<Product> products = new ArrayList<>();
        List<ImportResult.Rejected> rejected = new ArrayList<>();
        try (var reader = Files.newBufferedReader(file, charset)) {
            String header = reader.readLine();
            if (header == null) {
                throw new IOException("Empty file: " + file);
            }
            if (header.startsWith(BOM)) {
                header = header.substring(1);
            }
            if (!header.equals(HEADER)) {
                throw new IOException("Unexpected header: " + header);
            }
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    products.add(parse(line));
                } catch (IllegalArgumentException e) {
                    rejected.add(new ImportResult.Rejected(
                            lineNumber, line, e.getMessage()));
                }
            }
        }
        return new ImportResult(products, rejected);
    }
    private Product parse(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 3) {
            throw new IllegalArgumentException(
                    "expected 3 fields, found " + fields.length);
        }
        long id = Long.parseLong(fields[0].strip());
        String name = fields[1].strip();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        int priceInCents = Integer.parseInt(fields[2].strip());
        if (priceInCents < 0) {
            throw new IllegalArgumentException(
                    "negative price: " + priceInCents);
        }
        return new Product(id, name, priceInCents);
    }
}
