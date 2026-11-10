public class CodeCheck {
    public static void main(String[] args) {
        String code = "AB-12";
        int letters = 0;
        int digits = 0;
        for (char c : code.toCharArray()) {
            if (Character.isLetter(c)) {
                letters++;
            } else if (Character.isDigit(c)) {
                digits++;
            }
        }
        System.out.println("Letters: " + letters + ", digits: " + digits);
        System.out.println("Valid: " + (letters == 2 && digits == 2));
        String name = "mug";
        String title = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        System.out.println("Title: " + title);
    }
}
