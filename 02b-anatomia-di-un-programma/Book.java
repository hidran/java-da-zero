public class Book {
    public static void main(String[] args) {
        printHeader();
        System.out.println(new Book().describe());
    }

    static void printHeader() {
        System.out.println("Book catalog");
    }

    public String describe() {
        return "Dune" + "|" + 412 + "|" + true;
    }
}
