import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class CharsetDemo {
    public static void main(String[] args) {
        Charset windows = Charset.forName("windows-1252");
        byte[] utf8 = "Caffè".getBytes(StandardCharsets.UTF_8);
        byte[] ansi = "Caffè".getBytes(windows);
        System.out.println(Arrays.toString(utf8));
        System.out.println(Arrays.toString(ansi));
        System.out.println(new String(utf8, windows));
        System.out.println(new String(ansi, StandardCharsets.UTF_8));
    }
}
