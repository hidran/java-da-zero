import java.util.concurrent.locks.ReentrantLock;
public class Counter {
    private final ReentrantLock lock = new ReentrantLock();
    private int count;
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
    public int get() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
