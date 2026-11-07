import java.util.Scanner;
public class Checkout {
    public static void main(String[] args) {
        String[] names = {"Mug", "T-shirt", "Notebook"};
        int[] pricesInCents = {1500, 2490, 705};
        printMenu(names, pricesInCents);
        Scanner scanner = new Scanner(System.in);
        int totalInCents = 0;
        boolean open = true;
        while (open) {
            System.out.print("Choice: ");
            int choice = menuChoice(scanner.nextLine());
            if (choice == 0) {
                open = false;
            } else if (choice > 0) {
                int item = choice - 1;
                int quantity = readQuantity(scanner);
                int lineInCents = lineTotal(pricesInCents[item], quantity);
                totalInCents = totalInCents + lineInCents;
                System.out.println(receiptLine(names[item], quantity, lineInCents));
            } else {
                System.out.println("Unknown choice");
            }
        }
        System.out.println("Total: " + formatCents(totalInCents));
    }
    static void printMenu(String[] names, int[] prices) {
        for (int i = 0; i < names.length; i++) {
            System.out.println((i + 1) + ". " + names[i] + " " + formatCents(prices[i]));
        }
        System.out.println("0. Pay");
    }
    static String formatCents(int cents) {
        int euros = cents / 100;
        int rest = cents % 100;
        if (rest < 10) {
            return euros + ",0" + rest + " euro";
        }
        return euros + "," + rest + " euro";
    }
    static int menuChoice(String text) {
        return switch (text) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "0" -> 0;
            default -> -1;
        };
    }
    static int readQuantity(Scanner scanner) {
        int quantity = 0;
        while (quantity < 1) {
            System.out.print("Quantity: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Not a number: " + scanner.nextLine());
            }
        }
        return quantity;
    }
    static int lineTotal(int priceInCents, int quantity) {
        return priceInCents * quantity;
    }
    static String receiptLine(String name, int quantity, int cents) {
        return name + " x " + quantity + " = " + formatCents(cents);
    }
}
