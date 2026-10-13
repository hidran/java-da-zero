public class AtLeastOnce {
    public static void main(String[] args) {
        int tickets = 0;
        while (tickets > 0) {
            System.out.println("while: tickets left " + tickets);
            tickets--;
        }
        do {
            System.out.println("do while: tickets left " + tickets);
            tickets--;
        } while (tickets > 0);
    }
}
