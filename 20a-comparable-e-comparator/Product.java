public record Product(String name, int priceInCents)
        implements Comparable<Product> {
    @Override
    public int compareTo(Product other) {
        int byName = name.compareTo(other.name());
        if (byName != 0) {
            return byName;
        }
        return Integer.compare(priceInCents, other.priceInCents());
    }
    @Override
    public String toString() {
        return name + " " + priceInCents;
    }
}
