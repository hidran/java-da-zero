public class Overflow {
    public static void main(String[] args) {
        int max = 2_147_483_647;
        int next = max + 1;
        System.out.println("max: " + max + ", next: " + next);
        long wrongRevenue = 50_000 * 99_900;
        long revenue = 50_000L * 99_900;
        System.out.println("wrong revenue: " + wrongRevenue);
        System.out.println("revenue: " + revenue);
    }
}
