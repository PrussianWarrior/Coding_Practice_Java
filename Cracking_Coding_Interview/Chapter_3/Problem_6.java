import java.util.*;

public class Problem_6 {
  private static class Node {
    int data;
    Node next;

    public Node(int data) {
      this.data = data;
    }
  }

  private static class Stack {
    Node top;

    public void push(int data) {
      Node new_node = new Node(data);
      new_node.next = top;
      top = new_node;
    }

    public int peek() {
      if (top == null) {
        throw new IllegalArgumentException("Stack is empty so there is nothing to peek at.");
      }
      return top.data;
    }

    public int pop() {
      if (top == null) {
        throw new IllegalArgumentException("Stack is empty so there is nothing to peek at.");
      }
      int popped_value = top.data;
      top = top.next;
      return popped_value; 
    }

    public boolean is_empty() {
      return top == null;
    }
  }

  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);
    int max = Integer.parseInt(args[2]);

    List<Integer> temp = new ArrayList<>();
    Stack stack = new Stack();

    for (int i = 1; i <= N; i++) {
      int data = (int)(Math.random() * (max - min + 1)) + min; 
      temp.add(data);
      stack.push(data);
    }

    System.out.println("Unsorted stack:");
    for (int i = temp.size() - 1; i >= 0; i--) {
      System.out.print(temp.get(i) + " ");
    }
    System.out.println();

    sort_stack(stack);
    System.out.println("Sorted stack:");
    int prev = Integer.MIN_VALUE;
    int curr = 0;
    while (!stack.is_empty()) {
      curr = stack.pop();
      System.out.print(curr + " ");
      if (curr < prev) {
        System.out.println("Stack is not sorted because " + curr + " is smaller than " + prev);
      }
      prev = curr;
    }
    System.out.println();
  }

  private static void sort_stack(Stack stack) {
    Stack temp = new Stack();

    while (!stack.is_empty()) {
      int top = stack.pop();
      int count = 0;
      while (!temp.is_empty() && top < temp.peek()) {
        stack.push(temp.pop());
        count++;
      }

      temp.push(top);
      for (int i = 1; i <= count; i++) {
        temp.push(stack.pop());
      }
    }

    while (!temp.is_empty()) {
      stack.push(temp.pop());
    }
  }
}