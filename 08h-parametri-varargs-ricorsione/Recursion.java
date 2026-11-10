public class Recursion {
    public static void main(String[] args) {
        countdown(3);
        System.out.println("Factorial of 5: " + factorial(5));
    }
    static void countdown(int n) {
        if (n == 0) {
            System.out.println("Go!");
            return;
        }
        System.out.println(n);
        countdown(n - 1);
    }
    static long factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
