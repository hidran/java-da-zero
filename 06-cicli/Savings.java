public class Savings {
    public static void main(String[] args) {
        int balance = 100;
        int years = 0;
        while (balance < 150) {
            balance = balance + balance / 10;
            years++;
            System.out.println("Year " + years + ": " + balance);
        }
        System.out.println("Target reached in " + years + " years");
    }
}
