import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class Main {
    public static void main(String[] args) {
        LocalDate placedOn = LocalDate.of(2026, 10, 28);
        Order order = new Order("A-17", placedOn);
        LocalDate delivery = order.deliveryDate();
        LocalDate deadline = order.paymentDeadline();
        var italian = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Delivery: " + delivery.format(italian));
        System.out.println("Day: " + delivery.getDayOfWeek());
        System.out.println("Pay by: " + deadline.format(italian));
        placedOn.plusDays(100);
        System.out.println("Placed: " + placedOn);
        LocalDate warrantyEnd = delivery.plusYears(2);
        Period warranty = Period.between(delivery, warrantyEnd);
        long days = ChronoUnit.DAYS.between(delivery, warrantyEnd);
        System.out.println("Warranty: " + warranty);
        System.out.println("Days: " + days);
    }
}
