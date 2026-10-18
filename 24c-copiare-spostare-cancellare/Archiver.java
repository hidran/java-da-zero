import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
public class Archiver {
    public static void main(String[] args) {
        Path from = Path.of("exports", "2026", "09", "orders.csv");
        Path to = Path.of("archive", "2026-09-orders.csv");
        try {
            long size = Files.size(from);
            FileTime modified = Files.getLastModifiedTime(from);
            System.out.println(size + " bytes, modified " + modified);
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("In archive: " + Files.exists(to));
            System.out.println("In exports: " + Files.exists(from));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
