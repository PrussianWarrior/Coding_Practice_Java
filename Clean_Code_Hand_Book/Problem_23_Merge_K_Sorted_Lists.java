import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_23_Merge_K_Sorted_Lists {
  private static class Node {
    int data;
    Node next;
    Node(int data) {
      this.data = data;
    }
  }

  private static final Comparator<Node> LIST_COMPARATOR = new Comparator<Node>() {
    @Override
    public int compare(Node n1, Node n2) {
      return n1.data - n2.data;
    }
  };

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;

        while (line != null) {
          System.out.printf("%5d\n", counter++);
          List<Node> all_sorted_lists = process_input(line);
          List<Node> all_sorted_lists_clone = clone(all_sorted_lists);
          int total_len = 0;
          for (Node list : all_sorted_lists) {
            int[] len_each_list = {0};
            print_list(list, len_each_list);
            System.out.println("List is " + (is_list_sorted(list) ? " sorted" : " not sorted"));
            System.out.println();
            total_len += len_each_list[0];
          }
          System.out.println("Total number of elements = " + total_len + "\n");
          
          Node soln_1 = merge_K_sorted_list_1(all_sorted_lists);
          Node soln_2 = merge_K_sorted_list_1(all_sorted_lists_clone);
          boolean is_soln_1_sorted = is_list_sorted(soln_1);
          boolean is_soln_2_sorted = is_list_sorted(soln_2);

          int[] soln_1_len = {0};
          System.out.println("Solution 1:");
          print_list(soln_1, soln_1_len);
          System.out.println("Soln_1 is " + (is_soln_1_sorted ? " sorted" : " not sorted"));
          System.out.println("Soln_1 length = " + soln_1_len[0] + "\n");

          int[] soln_2_len = {0};
          System.out.println("Solution 2:");
          print_list(soln_2, soln_2_len);
          System.out.println("Soln_2 is " + (is_soln_2_sorted ? " sorted" : " not sorted"));
          System.out.println("Soln_2 length = " + soln_2_len[0] + "\n");

          if (!is_soln_1_sorted ||
              !is_soln_2_sorted
          ) {
            System.out.println("FAILED");
            break;
          }

          if (soln_1_len[0] != total_len ||
              soln_2_len[0] != total_len
          ) {
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

  public static Node merge_K_sorted_list_1(List<Node> list) {
    if (list.isEmpty()) {
      return null;
    }
    
    Queue<Node> pq = new PriorityQueue<>(list.size(), LIST_COMPARATOR);
    for (Node node : list) {
      pq.add(node);
    }

    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    while (!pq.isEmpty()) {
      Node top = pq.poll();
      iter.next = top;
      iter = iter.next;
      if (top.next != null) {
        pq.add(top.next);
      }
    }
    return dummy_head.next; 
  }

  private static Node merge_K_sorted_list_2(List<Node> list) {
    if (list.isEmpty()) {
      return null;
    }
    int end = list.size() - 1;
    while (end > 0) {
      int start = 0;
      while (start < end) {
        list.set(start, merge_2_sorted_list(list.get(start), list.get(end)));
        start++;
        end--;
      }
    }
    return list.get(0);
  }

  private static Node merge_2_sorted_list(Node node_1, Node node_2) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    while (node_1 != null && node_2 != null) {
      if (node_1.data <= node_2.data) {
        iter.next = node_1;
        node_1 = node_1.next;
      } else {
        iter.next = node_2;
        node_2 = node_2.next;
      }
      iter = iter.next;
    }

    if (node_1 != null) {
      iter.next = node_1;
    }
    if (node_2 != null) {
      iter.next = node_2;
    }
    return dummy_head.next;
  }

  private static List<Node> process_input(String line) {
    String[] len_min_max_all_lists = line.split("\\|");    
    List<Node> sorted_lists = new ArrayList<>();
    for (String str : len_min_max_all_lists) {
      // System.out.println(str);
      String[] len_min_max_str = str.trim().split(",");
      int[] len_min_max_int = new int[len_min_max_str.length];
      for (int i = 0; i < len_min_max_str.length; i++) {
        len_min_max_int[i] = Integer.parseInt(len_min_max_str[i].trim());
      }
      sorted_lists.add(generate_sorted_list(len_min_max_int[0], len_min_max_int[1], len_min_max_int[2]));
    }
    return sorted_lists;
  }

  private static Node generate_sorted_list(int len, int min, int max) {
    int[] temp = new int[len];
    for (int i = 0; i < len; i++) {
      temp[i] = (int)(Math.random() * (max - min + 1)) + min;
    }
    Arrays.sort(temp);

    Node head = new Node(0);
    Node iter = head;
    for (int n : temp) {    
      iter.next = new Node(n);
      iter = iter.next;
    }
    return head.next;
  }

  private static boolean is_list_sorted(Node head) {
    Node prec = null;
    while (head != null) {
      if (prec != null) {
        if (prec.data > head.data) {
          return false;
        }
      }
      prec = head;
      head = head.next;
    }
    return true;
  }

  private static List<Node> clone(List<Node> list) {
    List<Node> clone = new ArrayList<>();
    for (Node node : list) {
      clone.add(clone(node));
    }
    return clone;
  }

  private static Node clone(Node head) {
    Node dummy_head = new Node(0);
    Node iter = dummy_head;
    while (head != null) {
      iter.next = new Node(head.data);
      head = head.next;
      iter = iter.next;
    }
    return dummy_head.next;
  }

  private static void print_list(Node head, int[] len) {
    int counter = 1;
    while (head != null) {
      len[0]++;
      System.out.printf("%5d", head.data);
      if (counter % 20 == 0) {
        System.out.println();
      }
      counter++;
      head = head.next;
    }
    System.out.println();
  }
}
