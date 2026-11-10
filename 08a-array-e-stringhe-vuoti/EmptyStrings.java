public class EmptyStrings {
    public static void main(String[] args) {
        String empty = "";
        String spaces = "   ";
        System.out.println("Length: " + empty.length());
        System.out.println("Empty: " + empty.isEmpty() + ", " + spaces.isEmpty());
        System.out.println("Blank: " + empty.isBlank() + ", " + spaces.isBlank());
        String missing = null;
        System.out.println("Missing: " + missing);
        System.out.println("Same: " + empty.equals(missing));
        String[] items = {"pen", "book", "lamp"};
        String list = "";
        for (String item : items) {
            if (!list.isEmpty()) {
                list += ", ";
            }
            list += item;
        }
        System.out.println("List: " + list);
    }
}
