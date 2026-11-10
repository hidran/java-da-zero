public class EmptyArrays {
    public static void main(String[] args) {
        int[] none = new int[0];
        String[] noNames = {};
        System.out.println("Lengths: " + none.length + " and " + noNames.length);
        for (String name : noNames) {
            System.out.println("Name: " + name);
        }
        System.out.println("After the loop");
        int[] later;
        later = new int[2];
        later[1] = 5;
        System.out.println("Later: " + later[0] + ", " + later[1]);
    }
}
