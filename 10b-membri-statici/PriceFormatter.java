public class PriceFormatter {
    private PriceFormatter() {
    }
    public static String format(int cents) {
        int euros = cents / 100;
        int rest = cents % 100;
        if (rest < 10) {
            return euros + ",0" + rest + " euro";
        }
        return euros + "," + rest + " euro";
    }
}
