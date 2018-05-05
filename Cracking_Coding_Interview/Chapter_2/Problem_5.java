import java.util.*;
import java.io.*;

public class Problem_5 {
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
          
          Node reverse_num_1 = generate_reverse_linked_list_of_num(list.get(0));
          Node reverse_num_2 = generate_reverse_linked_list_of_num(list.get(1));
          System.out.print("Num 1 reversed: ");
          print_linked_list(reverse_num_1);

          System.out.print("Num 2 reversed: ");
          print_linked_list(reverse_num_2);

          System.out.println("Reverse add:");
          Node reverse_sum = add_reverse(reverse_num_1, reverse_num_2);
          print_linked_list(reverse_sum);

          Node forward_num_1 = generate_forward_linked_list_of_num(list.get(0));
          Node forward_num_2 = generate_forward_linked_list_of_num(list.get(1));
          System.out.print("Num 1 forward: ");
          print_linked_list(forward_num_1);

          System.out.print("Num 2 forward: ");
          print_linked_list(forward_num_2);

          System.out.println("Forward add:");
          Node forward_sum = add_forward(forward_num_1, forward_num_2);
          print_linked_list(forward_sum);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static Node add_reverse(Node n1, Node n2) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    int remainder = 0;
    while (n1 != null || n2 != null) {
      int sum = remainder;
      if (n1 != null) {
        sum += n1.data;
        n1 = n1.next;
      }

      if (n2 != null) {
        sum += n2.data;
        n2 = n2.next;
      }

      iter.next = new Node(sum % 10);
      iter = iter.next;
      remainder = sum/10;
    }

    if (remainder > 0) {
      iter.next = new Node(remainder);
    }
    return dummy_head.next;
  }

  private static Node add_forward(Node n1, Node n2) {
    return reverse(add_reverse(reverse(n1), reverse(n2)));
  }

  private static Node reverse(Node head) {
    if (head == null || head.next == null) {
      return head;
    }

    Node curr = head;
    Node next = head.next;
    curr.next = null;

    while (next != null) {
      Node next_next = next.next;
      next.next = curr;
      curr = next;
      next = next_next;
    }
    return curr;
  }

  private static Node generate_reverse_linked_list_of_num(int n) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    while (n > 0) {
      iter.next = new Node(n % 10);
      iter = iter.next;
      n /= 10;
    }
    return dummy_head.next;
  }

  private static Node generate_forward_linked_list_of_num(int n) {
    Node head = null;
    while (n > 0) {
      Node new_node = new Node(n % 10);
      new_node.next = head;
      head = new_node;
      n /= 10;
    }
    return head;
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