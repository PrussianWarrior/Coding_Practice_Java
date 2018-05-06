import java.util.*;

public class Problem_2 {
  private static class Min_Node {
    int data;
    int min;
    Min_Node next;

    public Min_Node(int data, int min) {
      this.data = data;
      this.min = min;
    }
  }

  private static class Min_Stack {
    private Min_Node top;

    public void push(int data) {
      if (top == null) {
        top = new Min_Node(data, data);
      } else {
        Min_Node new_node = new Min_Node(data, data >= top.min ? top.min : data);
        new_node.next = top;
        top = new_node;
      }
    }

    public boolean is_empty() {
      return top == null;
    }

    public int get_min() {
      if (top == null) {
        throw new IllegalArgumentException("Error. The stack is empty so there is no min value.");
      }
      return top.min;
    }

    public int peek() {
      if (top == null) {
        throw new IllegalArgumentException("Error. Stack is empty so there is nothing to peek at.");
      }
      return top.data;
    }

    public int pop() {
      if (top == null) {
        throw new IllegalArgumentException("Error. Popping from an empty stack.");
      }
      int returned_val = top.data;
      top = top.next;
      return returned_val;
    }
  }

  public static void main(String[] args) {
    Min_Stack min_stack = new Min_Stack();
    int N = Integer.parseInt(args[0]);

    for (int i = 1; i <= N; i++) {
      int random_num = (int)(Math.random() * 109);
      System.out.println("Pushing " + random_num + " onto the stack.");
      min_stack.push(random_num);
    }

    System.out.println();

    while (!min_stack.is_empty()) {
      System.out.println("Stack peek = " + min_stack.peek());
      System.out.println("Stack min = " + min_stack.get_min());
      System.out.println("Popping " + min_stack.pop());
      System.out.println();
    }
  }
}