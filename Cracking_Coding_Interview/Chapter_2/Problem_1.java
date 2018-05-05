import java.util.*;
import java.io.*;

public class Problem_1 {
  private static class Node {
    Node next;
    int data;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> list = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            list = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }
          
          Node head_1 = construct_linked_list(list);
          Node head_2 = construct_linked_list(list);
          System.out.println("Before removing duplicates");
          print_linked_list(head_1);

          Node list_1_duplicates_removed = remove_duplicates_1(head_1);
          remove_duplicates_2(head_2);

          System.out.println("After removing duplicates:");
          System.out.println("Method 1");
          print_linked_list(list_1_duplicates_removed);
          System.out.println("Method 2");
          print_linked_list(head_2);

          if (!are_two_lists_identical(list_1_duplicates_removed, head_2)) {
            System.out.println("FAILED. Method 1 does not yield the same result as method 2");
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

  private static Node remove_duplicates_1(Node head) {
    if (head == null) {
      return head;
    }

    Set<Integer> already_seen_number = new HashSet<>();
    Node dummy_head = new Node(0);
    Node iter = dummy_head;

    for (Node it = head; it != null; it = it.next) {
      if (!already_seen_number.contains(it.data)) {
        iter.next = new Node(it.data);
        iter = iter.next;
      }
      already_seen_number.add(it.data);
    }

    return dummy_head.next;
  }

  private static void remove_duplicates_2(Node head) {
    if (head == null) {
      return;
    }

    for (Node iter_1 = head; iter_1 != null; iter_1 = iter_1.next) {
      Node iter_2 = iter_1;
      Node iter_3 = iter_1.next;
      while (iter_3 != null) {
        if (iter_3.data == iter_1.data) {
          iter_2.next = iter_3.next;
        } else {
          iter_2 = iter_3;
        }
        iter_3 = iter_3.next;
      }
    }
  }

  private static Node construct_linked_list(List<Integer> list) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    for (int n : list) {
      iter.next = new Node(n);
      iter = iter.next;
    }
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