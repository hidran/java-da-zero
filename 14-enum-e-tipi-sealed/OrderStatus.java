public enum OrderStatus {
    NEW, PAID, SHIPPED;
    public boolean isFinal() {
        return this == SHIPPED;
    }
}
