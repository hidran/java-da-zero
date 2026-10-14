import java.time.LocalDate;
public record Order(String id, LocalDate placedOn) {
    public LocalDate paymentDeadline() {
        return placedOn.plusDays(14);
    }
    public LocalDate deliveryDate() {
        LocalDate date = placedOn.plusDays(3);
        return switch (date.getDayOfWeek()) {
            case SATURDAY -> date.plusDays(2);
            case SUNDAY -> date.plusDays(1);
            default -> date;
        };
    }
}
