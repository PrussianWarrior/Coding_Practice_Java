import java.util.*;

public class Problem_4 {

  private static class Queue {
    LinkedList<Integer> push_stack;
    LinkedList<Integer> pop_stack;

    public Queue() {
      push_stack = new LinkedList<>();
      pop_stack = new LinkedList<>();
    }

    public void enqueue(int data) {
      push_stack.add(data);
    }

    public boolean is_empty() {
      return push_stack.isEmpty() && pop_stack.isEmpty();
    }

    public int dequeue() {
      if (is_empty()) {
        throw new IllegalArgumentException("Error. The queue is empty. There is nothing to dequeue.");
      }

      if (pop_stack.isEmpty()) {
        while (!push_stack.isEmpty()) {
          pop_stack.add(push_stack.removeLast());
        }
      }
      return pop_stack.removeLast();
    }

    public int peek() {
      if (is_empty()) {
        throw new IllegalArgumentException("Error. The queue is empty. There is nothing to peek at.");
      }

      if (pop_stack.isEmpty()) {
        while (!push_stack.isEmpty()) {
          pop_stack.add(push_stack.removeLast());
        }
      }
      return pop_stack.getLast();
    }
  }

  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);

    Queue queue = new Queue();

    System.out.println("Adding the following integers into queue.");
    for (int n = 1; n <= N; n++) {
      System.out.print(n + " ");
      queue.enqueue(n);
    }
    System.out.println();

    while (!queue.is_empty()) {
      System.out.print(queue.dequeue() + " ");
    }
    System.out.println();
  }
}