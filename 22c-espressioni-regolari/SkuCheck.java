public class SkuCheck {
    static void check(String shape, String... codes) {
        for (String code : codes) {
            System.out.println(code + ": " + code.matches(shape));
        }
    }
    public static void main(String[] args) {
        check("[A-Z]{2}-\\d{2}", "AB-12", "ab-12", "AB-123");
        check("[A-Z]+-\\d+[SML]?", "MUG-15", "TSHIRT-7M", "TSHIRT-M");
    }
}
