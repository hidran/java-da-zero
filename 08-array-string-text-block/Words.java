public class Words {
    public static void main(String[] args) {
        String language = "Java";
        System.out.println("Length: " + language.length());
        System.out.println("First: " + language.charAt(0));
        System.out.println("Middle: " + language.substring(1, 3));
        System.out.println("Has va: " + language.contains("va"));
        language.toUpperCase();
        System.out.println("After: " + language);
        String upper = language.toUpperCase();
        System.out.println("Upper: " + upper);
        System.out.println("Equals: " + upper.equals("JAVA"));
    }
}
