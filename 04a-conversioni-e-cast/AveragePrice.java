public class AveragePrice {
    public static void main(String[] args) {
        int totalInCents = 1999 + 2499 + 1000 + 1500;
        int count = 4;
        int intAverage = totalInCents / count;
        double lateCast = (double) (totalInCents / count);
        double average = (double) totalInCents / count;
        System.out.println("int: " + intAverage + ", late cast: " + lateCast);
        System.out.println("double: " + average);
        int averageInCents = (int) Math.round(average);
        System.out.println("rounded: " + averageInCents + " cents");
    }
}
