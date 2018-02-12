import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_39_Min_Stack {
  private static class Node<T extends Comparable> {
    T data;
    T min;
    Node next;
    public Node(T data, T min) {
      this.data = data;
      this.min = min;
    }
  }

  private static class Min_Stack<T extends Comparable> {
    Node<T> top;

    public void push(T data) {
      if (top == null) {
        top = new Node(data, data);
      } else {
        Node<T> new_node = new Node(data, top.min.compareTo(data) < 0 ? top.min : data);
        new_node.next = top;
        top = new_node;
      }
    }

    public void pop() {
      if (top == null) {
        throw new NoSuchElementException("ERROR: The stack is empty.");
      }
      top = top.next;
    }

    public T top() {
      if (top == null) {
        throw new NoSuchElementException("ERROR: The stack is empty."); 
      }
      return top.data;
    }

    public T get_min() {
      if (top == null) {
        throw new NoSuchElementException("ERROR: The stack is empty."); 
      }
      return top.min;
    }

    public boolean is_empty() {
      return top == null;
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int case_number = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", case_number++, line);
          List<Integer> input = process_input(line);
          Min_Stack<Integer> min_stack = new Min_Stack<>();
          for (int n : input) {
            min_stack.push(n);
          }
          
          int counter = 1;
          while (!min_stack.is_empty()) {
            System.out.printf("%5d. top = %5d, min = %5d\n", counter++, min_stack.top(), min_stack.get_min());
            min_stack.pop();
          }
          System.out.println();
          
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }   
  }

  private static List<Integer> process_input(String line) {
    line = line.trim();
    String[] words = line.split(" ");
    List<Integer> input = new ArrayList<>();
    for (String word : words) {
      input.add(Integer.parseInt(word.trim()));
    }
    return input;
  }
}