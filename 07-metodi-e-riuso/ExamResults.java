public class ExamResults {
    public static void main(String[] args) {
        printResult("Anna", 72);
        printResult("Marco", 45);
        printResult("Sara", 60);
    }

    static void printResult(String name, int score) {
        System.out.println(name + ": " + score + " " + outcome(score));
    }

    static String outcome(int score) {
        if (score >= 60) {
            return "passed";
        }
        return "failed";
    }
}
