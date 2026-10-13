public class Precedence {
    public static void main(String[] args) {
        int first = 8;
        int second = 4;
        int wrongAverage = first + second / 2;
        int average = (first + second) / 2;
        System.out.println("wrong average: " + wrongAverage);
        System.out.println("average: " + average);
    }
}
