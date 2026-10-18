import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class MonthArchive {
    public static void main(String[] args) throws IOException {
        Path month = Path.of("exports", "2026-09");
        Path zip = Path.of("exports", "2026-09.zip");
        try (ZipOutputStream out =
                new ZipOutputStream(Files.newOutputStream(zip))) {
            for (String name : List.of("orders.csv", "summary.txt")) {
                out.putNextEntry(new ZipEntry(name));
                try (InputStream in = Files.newInputStream(month.resolve(name))) {
                    in.transferTo(out);
                }
                out.closeEntry();
            }
        }
        System.out.println("Archive: " + Files.size(zip) + " bytes");
        try (ZipInputStream in =
                new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                long bytes = in.transferTo(OutputStream.nullOutputStream());
                System.out.println(entry.getName() + ": " + bytes + " bytes");
            }
        }
    }
}
