import java.util.Scanner;
public class SafeLineTotal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int priceInCents = 1500;
        System.out.print("Quantity: ");
        if (scanner.hasNextInt()) {
            int quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Product: ");
            String name = scanner.nextLine();
            System.out.println(name + " x " + quantity + " = " + quantity * priceInCents + " cents");
        } else {
            System.out.println("Not a number: " + scanner.nextLine());
        }
    }
}
