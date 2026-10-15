public class Main {
    public static void main(String[] args) {
        Product coffee = new Product("Coffee", 250);
        Product sameCoffee = new Product("Coffee", 250);
        System.out.println(coffee);
        System.out.println("Same object: " + (coffee == sameCoffee));
        System.out.println("Equal: " + coffee.equals(sameCoffee));
        System.out.println("Same hash: " + (coffee.hashCode() == sameCoffee.hashCode()));
    }
}
