public class CounterDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Runnable work = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.increment();
            }
        };
        Thread one = Thread.ofPlatform().start(work);
        Thread two = Thread.ofPlatform().start(work);
        one.join();
        two.join();
        System.out.println("Total: " + counter.get());
    }
}
