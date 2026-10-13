public class FirstMultiple {
    public static void main(String[] args) {
        for (int n = 50; n <= 60; n++) {
            if (n % 7 != 0) {
                continue;
            }
            System.out.println("First multiple of 7: " + n);
            break;
        }
        System.out.println("Search over");
    }
}
