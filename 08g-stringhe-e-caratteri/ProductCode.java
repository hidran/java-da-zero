public class ProductCode {
    public static void main(String[] args) {
        String raw = "  ab-12 ";
        String code = raw.strip().toUpperCase();
        System.out.println("Code: [" + code + "]");
        int dash = code.indexOf('-');
        System.out.println("Prefix: " + code.substring(0, dash));
        System.out.println("Number: " + code.substring(dash + 1));
        System.out.println("No hash: " + code.indexOf('#'));
        System.out.println("Starts with AB: " + code.startsWith("AB"));
        System.out.println("Same code: " + code.equalsIgnoreCase("ab-12"));
        System.out.println("Order: " + code.compareTo("AA-99"));
    }
}
