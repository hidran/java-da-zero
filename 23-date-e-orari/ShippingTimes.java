import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
public class ShippingTimes {
    public static void main(String[] args) {
        var pickedUp = LocalDateTime.of(2026, 10, 24, 22, 0);
        var delivered = LocalDateTime.of(2026, 10, 25, 10, 0);
        Duration local = Duration.between(pickedUp, delivered);
        System.out.println("Local: " + local.toHours() + " hours");
        ZoneId rome = ZoneId.of("Europe/Rome");
        ZonedDateTime start = pickedUp.atZone(rome);
        ZonedDateTime end = delivered.atZone(rome);
        Duration zoned = Duration.between(start, end);
        System.out.println("Zoned: " + zoned.toHours() + " hours");
        System.out.println("From " + start);
        System.out.println("To " + end);
    }
}
