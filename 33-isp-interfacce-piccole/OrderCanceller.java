public interface OrderCanceller {
    void cancel(int id);
    void refund(int id);
}
