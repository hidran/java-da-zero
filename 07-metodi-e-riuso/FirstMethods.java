public class FirstMethods {
    public static void main(String[] args) {
        int sum = add(3, 4);
        System.out.println("3 + 4 = " + sum);
        System.out.println("10 + 5 = " + add(10, 5));
        greet("Anna");
    }

    static int add(int a, int b) {
        return a + b;
    }

    static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}
