package com.shop;
import java.io.IOException;
import java.io.StringReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
public class CatalogImport {
    public static void main(String[] args) throws IOException {
        String csv = """
                name;priceInCents
                Mug;1200
                "Tea; green";450
                """;
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();
        try (CSVParser parser = format.parse(new StringReader(csv))) {
            for (CSVRecord record : parser) {
                String name = record.get("name");
                String price = record.get("priceInCents");
                System.out.println(name + " costs " + price + " cents");
            }
        }
    }
}
