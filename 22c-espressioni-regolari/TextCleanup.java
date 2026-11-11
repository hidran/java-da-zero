import java.util.regex.Pattern;
public class TextCleanup {
    public static void main(String[] args) {
        String name = "  Blue    coffee   mug ";
        String clean = name.strip().replaceAll("\\s+", " ");
        System.out.println("Name: [" + clean + "]");
        String line = "Mug ;  1500;Blue";
        String[] parts = line.split("\\s*;\\s*");
        System.out.println("Parts: " + String.join(",", parts));
        String search = "1.5";
        System.out.println("Dot: " + "1x5".matches(search));
        System.out.println("Quoted: " + "1x5".matches(Pattern.quote(search)));
    }
}
