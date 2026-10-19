import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.util.List;
public class Main {
    record Product(String name, int priceInCents) {
    }
    static final Path PROCESSED = Path.of("processed");
    static final Path REJECTED = Path.of("rejected");
    static Product toProduct(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("bad line: " + line);
        }
        return new Product(parts[0], Integer.parseInt(parts[1]));
    }
    static void importFile(Path file) throws IOException {
        Path name = file.getFileName();
        try {
            List<Product> products = Files.readAllLines(file).stream()
                    .map(Main::toProduct)
                    .toList();
            products.forEach(System.out::println);
            Files.move(file, PROCESSED.resolve(name));
            System.out.println(name + " -> processed");
        } catch (IllegalArgumentException e) {
            String reason = e.getMessage();
            Files.move(file, REJECTED.resolve(name));
            Path note = REJECTED.resolve(name + ".reason.txt");
            Files.writeString(note, reason + "\n");
            System.out.println(name + " -> rejected: " + reason);
        }
    }
    public static void main(String[] args)
            throws IOException, InterruptedException {
        Path inbox = Path.of("inbox");
        try (var watcher = FileSystems.getDefault().newWatchService()) {
            inbox.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE);
            System.out.println("Watching " + inbox);
            boolean running = true;
            while (running) {
                WatchKey key = watcher.take();
                for (var event : key.pollEvents()) {
                    Path name = (Path) event.context();
                    if (name.toString().equals("stop")) {
                        Files.delete(inbox.resolve(name));
                        running = false;
                    } else {
                        importFile(inbox.resolve(name));
                    }
                }
                if (!key.reset()) {
                    running = false;
                }
            }
        }
        System.out.println("Stopped");
    }
}
