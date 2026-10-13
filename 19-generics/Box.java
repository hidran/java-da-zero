public class Box<T> {
    private final T content;
    public Box(T content) {
        this.content = content;
    }
    public T get() {
        return content;
    }
}
