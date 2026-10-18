import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
public class ArchiveIndex {
    private static void safeWrite(Path file, String text)
            throws IOException {
        Path dir = file.getParent();
        Path tmp = Files.createTempFile(dir, "safe", ".tmp");
        Files.writeString(tmp, text);
        Files.move(tmp, file, StandardCopyOption.ATOMIC_MOVE,
                StandardCopyOption.REPLACE_EXISTING);
    }
    public static void main(String[] args) {
        Path index = Path.of("archive", "index.txt");
        String text = "2026-09-orders.csv;archived\n2025;deleted\n";
        try {
            safeWrite(index, text);
            System.out.println("Index bytes: " + Files.size(index));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
