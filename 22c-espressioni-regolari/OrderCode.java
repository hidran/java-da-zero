import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class OrderCode {
    private static final Pattern ORDER = Pattern.compile("ORD-(\\d{4})-(\\d+)");
    public static void main(String[] args) {
        String note = "Customer asks about ORD-2026-00042 and ORD-2025-7";
        Matcher matcher = ORDER.matcher(note);
        while (matcher.find()) {
            String year = matcher.group(1);
            int number = Integer.parseInt(matcher.group(2));
            System.out.println("Found: " + matcher.group());
            System.out.println("Year " + year + ", number " + number);
        }
    }
}
