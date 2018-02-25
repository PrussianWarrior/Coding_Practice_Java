import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

  k is a positive integer and is less than or equal to the length of the linked list. If the number 
  of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

  You may not alter the values in the nodes, only nodes itself may be changed.

  Only constant memory is allowed.

  For example,
  Given this linked list: 1->2->3->4->5

  For k = 2, you should return: 2->1->4->3->5

  For k = 3, you should return: 3->2->1->4->5

*/

public class Reverse_LL_In_K_Groups {
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

          int[] length_group_size = new int[2];
          if (line.indexOf("Customized:") >= 0) {
            length_group_size = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Node linked_list_1 = generate_linked_list(length_group_size[0]);
          System.out.println("Before reverse:");
          print_linked_list(linked_list_1);
          System.out.println("k = " + length_group_size[1]);

          long start = System.currentTimeMillis();
          Node reversed_ll_1 = reverse_ll_k_groups_1(linked_list_1, length_group_size[1]);
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

  private static Node reverse_ll_k_groups_1(Node head, int k) {
    if (k == 1) {
      return head;
    }

    Node dummy_head = new Node(0);
    Node prec = dummy_head;
    dummy_head.next = head;

    Node iter = head;
    int counter = 1;

    while (iter != null) {
      if (counter % k == 0) {
        prec = reverse_ll_1(prec, iter.next);
        iter = prec.next;
      } else {
        iter = iter.next;
      }
      counter++;
    }
    return dummy_head.next;
  }

  private static Node reverse_ll_1(Node before_start, Node after_end) {
    Node curr = before_start.next;
    Node curr_next = curr.next;

    while (curr_next != after_end) {
      curr.next = curr_next.next;
      curr_next.next = before_start.next;
      before_start.next = curr_next;
      curr_next = curr.next;
    }

    return curr;
  }

  private static int[] get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] integer_strings = line.split(" ");
    int[] length_group_size = new int[2];
    length_group_size[0] = Integer.parseInt(integer_strings[0].trim());
    length_group_size[1] = Integer.parseInt(integer_strings[1].trim());
    return length_group_size;
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
}