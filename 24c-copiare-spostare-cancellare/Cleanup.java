import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
public class Cleanup {
    public static void main(String[] args) {
        Path old = Path.of("exports", "2025");
        try (Stream<Path> walk = Files.walk(old)) {
            List<Path> all = walk.toList().reversed();
            for (Path path : all) {
                Files.delete(path);
                System.out.println("Deleted " + path);
            }
            System.out.println("Still there: " + Files.exists(old));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
