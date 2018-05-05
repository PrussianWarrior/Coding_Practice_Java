import java.util.*;
import java.io.*;

public class Problem_3 {
  private static class Node {
    Node next;
    int data;

    public Node(int data) {
      this.data = data;
    }
  }

  private static class List_Kth {
    List<Integer> list;
    int kth;

    public List_Kth(List<Integer> list, int kth) {
      this.list = new ArrayList<>(list);
      this.kth = kth;
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

          List_Kth list_kth = null;
          if (line.indexOf("Customized:") >= 0) {
            list_kth = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }
          
          Node linked_list_1 = construct_linked_list(list_kth.list);
          System.out.println("Before deleting " + list_kth.kth + " node from last.");
          print_linked_list(linked_list_1);

          delete_kth_node_from_last(linked_list_1, list_kth.kth);
          System.out.println("After deleting " + list_kth.kth + " node from last.");
          print_linked_list(linked_list_1);          

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static void delete_kth_node_from_last(Node head, int kth) {
    if (head == null) {
      return;
    }

    Node advance = head;
    for (int i = 1; i <= kth && advance != null; i++) {
      advance = advance.next;
    }

    if (advance == null) {
      return;
    }

    Node follow = head;
    Node follow_follow = null;
    while (advance.next != null) {
      follow_follow = follow;
      follow = follow.next;
      advance = advance.next;
    }

    follow_follow.next = follow.next;
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

  private static List_Kth get_customized_input(String line) {
    int index_of_opening_bracket = line.indexOf("[");
    int index_of_closing_bracket = line.indexOf("]");

    String list = remove_extra_spaces(line.substring(index_of_opening_bracket + 1, index_of_closing_bracket).trim());
    int kth = Integer.parseInt(line.substring(index_of_closing_bracket + 1).trim());

    String[] words = list.split(" ");
    List<Integer> list_of_integers = new ArrayList<>();
    for (String word : words) {
      list_of_integers.add(Integer.parseInt(word));
    }

    return new List_Kth(list_of_integers, kth);
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