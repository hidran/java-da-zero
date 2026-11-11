import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
public class Queues {
    public static void main(String[] args) {
        Queue<Integer> toShip = new ArrayDeque<>();
        toShip.offer(101);
        toShip.offer(102);
        toShip.offer(103);
        System.out.println("Shipped: " + toShip.poll());
        System.out.println("Next: " + toShip.peek());
        System.out.println("Waiting: " + toShip);
        Deque<String> undo = new ArrayDeque<>();
        undo.push("add Tea");
        undo.push("add Mug");
        System.out.println("Undo: " + undo.pop() + ", left: " + undo);
        Queue<Integer> urgent = new PriorityQueue<>();
        urgent.offer(130);
        urgent.offer(107);
        urgent.offer(115);
        System.out.println("Inside: " + urgent);
        System.out.println("First: " + urgent.poll());
        System.out.println("Then: " + urgent.poll());
    }
}
