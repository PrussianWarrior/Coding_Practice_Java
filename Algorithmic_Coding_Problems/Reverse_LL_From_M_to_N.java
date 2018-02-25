import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Reverse a linked list from position m to n. Do it in-place and in one-pass.

  For example:
  Given 1->2->3->4->5->NULL, m = 2 and n = 4,

  return 1->4->3->2->5->NULL.

  Note:
  Given m, n satisfy the following condition:
  1 ≤ m ≤ n ≤ length of list.
*/

public class Reverse_LL_From_M_to_N {
  private static class Node {
    int data;
    Node next;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          int[] length_m_n = new int[3];
          if (line.indexOf("Customized:") >= 0) {
            length_m_n = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          if (length_m_n[1] > length_m_n[2]) {
            throw new IllegalArgumentException("m must be smaller than n");
          }

          Node linked_list_1 = generate_linked_list(length_m_n[0]);

          System.out.println("Before reverse:");
          print_linked_list(linked_list_1);

          long start = System.currentTimeMillis();
          Node reversed_ll_1 = reverse_ll_from_m_to_n_1(linked_list_1, length_m_n[1], length_m_n[2]);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Linked list after reverse:");
          System.out.println("Solution 1: ");
          print_linked_list(reversed_ll_1);
          System.out.println("Execution time = " + execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static Node reverse_ll_from_m_to_n_1(Node head, int m, int n) {
    if (m == n) {
      return head;
    }
    Node dummy_head = new Node(0);
    dummy_head.next = head;
    Node prec_start = dummy_head;
    Node after_end = head;

    int counter = 1;
    while (counter != m) {
      prec_start = prec_start.next;
      after_end = after_end.next;
      counter++;
    }

    while (counter != n) {
      after_end = after_end.next;
      counter++;
    }

    after_end = after_end.next;
    prec_start = reverse_ll(prec_start, after_end);
    return dummy_head.next;
  }

  private static Node reverse_ll(Node prec_start, Node after_end) {
    Node curr = prec_start.next;
    Node curr_next = curr.next;

    while (curr_next != after_end) {
      curr.next = curr_next.next;
      curr_next.next = prec_start.next;
      prec_start.next = curr_next;
      curr_next = curr.next;
    }

    return curr;
  }

  private static int[] get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] integer_strings = line.split(" ");
    int[] length_m_n = new int[3];
    length_m_n[0] = Integer.parseInt(integer_strings[0].trim());
    length_m_n[1] = Integer.parseInt(integer_strings[1].trim());
    length_m_n[2] = Integer.parseInt(integer_strings[2].trim());
    return length_m_n;
  }

  private static Node generate_linked_list(int length) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    for (int i = 1; i <= length; i++) {
      iter.next = new Node(i);
      iter = iter.next;
    }
    return dummy_head.next;
  }

  private static void print_linked_list(Node head) {
    int counter = 1;
    for (Node iter = head; iter != null; iter = iter.next) {
      System.out.printf("%5d: %5d\n", counter++, iter.data);
    }
    System.out.println();
  }

  private static boolean are_linked_list_equals(Node head_1, Node head_2) {
    Node iter_1 = head_1;
    Node iter_2 = head_2;
    while (iter_1 != null && iter_2 != null) {
      if (iter_1.data != iter_2.data) {
        return false;
      }
      iter_1 = iter_1.next;
      iter_2 = iter_2.next;
    }
    return iter_1 == null && iter_2 == null;
  }
}