public class DayName {
    public static void main(String[] args) {
        int day = 6;
        String name;
        if (day == 1) {
            name = "Monday";
        } else if (day == 2) {
            name = "Tuesday";
        } else if (day == 3) {
            name = "Wednesday";
        } else if (day == 4) {
            name = "Thursday";
        } else if (day == 5) {
            name = "Friday";
        } else if (day == 6) {
            name = "Saturday";
        } else if (day == 7) {
            name = "Sunday";
        } else {
            name = "Unknown";
        }
        System.out.println("Day " + day + " is " + name);
    }
}
