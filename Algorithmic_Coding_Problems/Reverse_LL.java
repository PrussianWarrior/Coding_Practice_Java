import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a singly linked list, reverse its nodes
*/

public class Reverse_LL {
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

          int linked_list_length = 0;
          if (line.indexOf("Customized:") >= 0) {
            linked_list_length = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Node linked_list_1 = generate_linked_list(linked_list_length);
          Node linked_list_2 = generate_linked_list(linked_list_length);

          System.out.println("Before reverse:");
          print_linked_list(linked_list_1);

          long start = System.currentTimeMillis();
          Node reversed_ll_1 = reverse_ll_1(linked_list_1);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          Node reversed_ll_2 = reverse_ll_2(linked_list_2);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("Linked list after reverse:");
          System.out.println("Solution 1: ");
          print_linked_list(reversed_ll_1);
          System.out.println("Execution time = " + execution_time_soln_1);

          System.out.println("Solution 2: ");
          print_linked_list(reversed_ll_2);
          System.out.println("Execution time = " + execution_time_soln_2);

          if (!are_linked_list_equals(reversed_ll_1, reversed_ll_2)) {
            System.out.println("FAILED");
            break;
          }

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static Node reverse_ll_1(Node head) {
    if (head == null || head.next == null) {
      return head;
    }

    Node next = head.next;
    head.next = null;

    while (next != null) {
      Node temp = next.next;
      next.next = head;
      head = next;
      next = temp;
    }
    return head;
  }

  private static Node reverse_ll_2(Node head) {
    if (head == null || head.next == null) {
      return head;
    }

    Node next = head.next;
    head.next = null;
    Node new_head = reverse_ll_2(next);
    next.next = head;
    return new_head;
  }

  private static int get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    return Integer.parseInt(line.trim());
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