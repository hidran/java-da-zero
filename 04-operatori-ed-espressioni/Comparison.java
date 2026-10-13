public class Comparison {
    public static void main(String[] args) {
        int age = 16;
        boolean withAdult = true;
        System.out.println("adult: " + (age >= 18));
        System.out.println("exactly 16: " + (age == 16));
        System.out.println("can enter: " + (age >= 18 || withAdult));
        System.out.println("minor and alone: " + (age < 18 && !withAdult));
    }
}
