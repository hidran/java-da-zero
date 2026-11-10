public class MathBasics {
    public static void main(String[] args) {
        int gap = Math.abs(1999 - 2499);
        int toPay = Math.max(0, 500 - 800);
        System.out.println("abs: " + gap + ", to pay: " + toPay);
        int shipped = Math.min(4, 6);
        int quantity = Math.clamp(15, 1, 10);
        System.out.println("min: " + shipped + ", clamp: " + quantity);
        double power = Math.pow(2, 10);
        System.out.println("pow: " + power + ", sqrt: " + Math.sqrt(144));
        int boxes = (int) Math.ceil(25 / 10.0);
        System.out.println("boxes: " + boxes + ", floor: " + Math.floor(-2.5));
        int today = 2;
        System.out.println("remainder: " + (today - 10) % 7);
        System.out.println("floorMod: " + Math.floorMod(today - 10, 7));
    }
}
