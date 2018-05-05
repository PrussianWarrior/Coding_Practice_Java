import java.util.*;
import java.io.*;

public class Problem_8 {
  private static class Node {
    Node next;
    int data;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int loop_node = Integer.parseInt(args[1]);

    Node loop_linked_list = generate_loop_linked_list(N, loop_node);
    Node start_of_loop = find_start_of_loop(loop_linked_list);
    if (start_of_loop != null) {
      System.out.println("Start of loop = " + start_of_loop.data);
    } else {
      System.out.println("Linked list has no loop");
    }
  }

  private static Node find_start_of_loop(Node head) {
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (fast == slow) {
        break;
      }
    }

    if (fast == null || fast.next == null) {
      return null;
    }

    Node iter = head;
    while (iter != slow) {
      iter = iter.next;
      slow = slow.next;
    }
    return iter;
  }

  private static Node generate_loop_linked_list(int N, int loop_node) {
    if (loop_node > N) {
      throw new IllegalArgumentException("The loop node cannot be greater than the length of the linked list.");
    }
    Node dummy_head = new Node(0);
    Node iter = dummy_head;

    Node[] arr_node = new Node[N];
    for (int i = 1; i <= N; i++) {
      arr_node[i - 1] = new Node(i);
      iter.next = arr_node[i - 1];
      iter = iter.next;
    }

    print_linked_list(dummy_head.next);
    arr_node[N - 1].next = arr_node[loop_node - 1];
    return dummy_head.next;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces(line.substring(index_of_colon + 1).trim());
    String[] words = line.split(" ");

    List<Integer> list_of_integers = new ArrayList<>();
    for (String word : words) {
      list_of_integers.add(Integer.parseInt(word));
    }

    return list_of_integers;
  }

  private static String remove_extra_spaces(String line) {
    line = line.trim();
    StringBuilder extra_spaces_removed = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (Character.isSpace(line.charAt(i))) {
        start = i;
      } else if (i + 1 == line.length() || Character.isSpace(line.charAt(i + 1))) {
        extra_spaces_removed.append(line.substring(start + 1, i + 1));
        if (i + 1 < line.length()) {
          extra_spaces_removed.append(" ");
        }
      }
    }
    return extra_spaces_removed.toString();
  }

  private static void print_linked_list(Node head) {
    int counter = 1;
    for (Node iter = head; iter != null; iter = iter.next) {
      System.out.printf("%5d", iter.data);
      if (counter % 10 == 0) {
        System.out.println();
      }
      counter++;
    }
    System.out.println();
  }

  private static Node copy_linked_list(Node head) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    for (Node it = head; it != null; it = it.next) {
      iter.next = new Node(it.data);
      iter = iter.next;
    }
    return dummy_head.next;
  }

  private static boolean are_two_lists_identical(Node head_1, Node head_2) {
    while (head_1 != null && head_2 != null) {
      if (head_1.data != head_2.data) {
        return false;
      }
      head_1 = head_1.next;
      head_2 = head_2.next;
    }
    return head_1 == null && head_2 == null;
  }
}