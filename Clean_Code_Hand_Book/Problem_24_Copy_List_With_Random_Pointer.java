import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_24_Copy_List_With_Random_Pointer {
  private static class Random_Node {
    int data;
    Random_Node next;
    Random_Node random;
    Random_Node(int data) {
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
          System.out.printf("%5d: %-1s\n", counter++, line);
          Random_Node node = process_input(line);
          print_list(node);

          Random_Node soln_1 = clone_1(node);
          Random_Node soln_2 = clone_2(node);
          Random_Node soln_3 = clone_3(node);

          System.out.println("Solution 1");
          print_list(soln_1);
          System.out.println("Solution 2");
          print_list(soln_2);
          System.out.println("Solution 3");
          print_list(soln_3);

          if (!equal(node, soln_1) ||
              !equal(node, soln_2) ||
              !equal(node, soln_3)
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

  public static Random_Node clone_1(Random_Node node) {
    Random_Node dummy_head = new Random_Node(0);
    Random_Node iter = dummy_head;
    Map<Random_Node, Random_Node> cache = new HashMap<>();

    for (Random_Node node_iter = node; node_iter != null; node_iter = node_iter.next) {
      iter.next = new Random_Node(node_iter.data);
      cache.put(node_iter, node_iter.random);
      iter = iter.next;
    }

    iter = dummy_head;
    for (Random_Node node_iter = node; node_iter != null; node_iter = node_iter.next) {
      Random_Node random_node = cache.get(node_iter);
      iter.next.random = random_node != null ? new Random_Node(random_node.data) : null;
      iter = iter.next;
    }
    return dummy_head.next;
  }

  public static Random_Node clone_2(Random_Node node) {
    Random_Node dummy_head = new Random_Node(0);
    Random_Node iter = dummy_head;
    Map<Random_Node, Random_Node> cache = new HashMap<>();
    for (Random_Node node_iter = node; node_iter != null; node_iter = node_iter.next) {
      iter.next = new Random_Node(node_iter.data);
      cache.put(node_iter, iter.next);
      iter = iter.next;
    }

    iter = dummy_head;
    for (Random_Node node_iter = node; node_iter != null; node_iter = node_iter.next) {
      iter.next.random = cache.get(node_iter.random);
      iter = iter.next;
    }
    return dummy_head.next;
  }

  public static Random_Node clone_3(Random_Node node) {
    Random_Node node_iter = node;
    while (node_iter != null) {
      Random_Node node_iter_next = node_iter.next;
      Random_Node new_node = new Random_Node(node_iter.data);
      node_iter.next = new_node;
      new_node.next = node_iter_next;
      node_iter = node_iter_next;
    }

    node_iter = node;
    while (node_iter != null) {
      node_iter.next.random = node_iter.random != null ? node_iter.random.next : null;
      node_iter = node_iter.next.next;
    }

    Random_Node dummy_head = new Random_Node(0);
    dummy_head.next = node != null ? node.next : null;
    node_iter = node;
    while (node_iter != null) {
      Random_Node node_iter_next = node_iter.next;
      node_iter.next = node_iter_next.next;
      node_iter = node_iter.next;
      node_iter_next.next = node_iter != null ? node_iter.next : null;
    }
    return dummy_head.next;
  }

  private static Random_Node process_input(String line) {
    String[] len_min_max = line.split(" ");    
    int len = Integer.parseInt(len_min_max[0].trim());
    int min = Integer.parseInt(len_min_max[1].trim());
    int max = Integer.parseInt(len_min_max[2].trim());

    Random_Node[] random_node_arr = new Random_Node[len];

    for (int i = 0; i < len; i++) {
      int data = (int)(Math.random() * (max - min + 1)) + min;
      random_node_arr[i] = new Random_Node(data);
    }

    for (int i = 0; i < len; i++) {      
      int random_index = (int)(Math.random() * len);
      random_node_arr[i].random = random_node_arr[random_index];
      if (i < len - 1) {
        random_node_arr[i].next = random_node_arr[i+1];
      }
    }

    for (int i = 1; i <= len/6; i++) {
      int random_index = (int)(Math.random() * len);
      random_node_arr[i].random = null;
    }

    return random_node_arr[0];
  }

  private static void print_list(Random_Node head) {
    int counter = 1;
    while (head != null) {
      System.out.printf("%5d", head.data);
      if (head.random != null) {
        System.out.printf("(%5d)", head.random.data);
      } else {
        System.out.printf("(%5s)", "null");
      }
      if (counter % 10 == 0) {
        System.out.println();
      }
      counter++;
      head = head.next;
    }
    System.out.println();
  }

  private static boolean equal(Random_Node node_1, Random_Node node_2) {
    Random_Node i_1 = node_1;
    Random_Node i_2 = node_2;
    while (i_1 != null && i_2 != null) {
      if (i_1.data != i_2.data || 
          (i_1.random != null && i_2.random == null) ||
          (i_1.random == null && i_2.random != null) ||
          (i_1.random != null && i_2.random != null && i_1.random.data != i_2.random.data)
      ) {
        return false;
      }
      i_1 = i_1.next;
      i_2 = i_2.next;
    }
    return i_1 == null && i_2 == null;
  }
}
