import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

  The update(i, val) function modifies nums by updating the element at index i to val.
  Example:
  Given nums = [1, 3, 5]

  sumRange(0, 2) -> 9
  update(1, 2)
  sumRange(0, 2) -> 8
  Note:
  The array is only modifiable by the update function.
  You may assume the number of calls to update and sumRange function is distributed evenly.
*/

public class Range_Sum_Query_Mutable {
  private static class Segment_Tree {
    private static class Segment_Tree_Node {
      int sum;
      int start;
      int end;
      Segment_Tree_Node left;
      Segment_Tree_Node right;

      public Segment_Tree_Node(int start, int end) {
        this.start = start;
        this.end = end;
      }
    }

    Segment_Tree_Node root;

    public Segment_Tree(List<Integer> list) {
      root = build_segment_tree(list, 0, list.size() - 1);
    }

    public int range_sum(int start, int end) {
      if (start > end) {
        throw new IllegalArgumentException("ERROR: start must not > end");
      }

      if (root.end < start || root.start > end) {
        throw new IllegalArgumentException("ERROR: out of bound exception. the valid range is [" 
          + root.start + ", " + root.end + "]");
      }
      return range_sum(root, start, end);
    }

    private int range_sum(Segment_Tree_Node node, int start, int end) {
      if (node.start == start && node.end == end) {
        return node.sum;
      }
      int mid = node.start + (node.end - node.start)/2;
      if (end <= mid) {
        return range_sum(node.left, start, end);
      } else if (start >= mid + 1) {
        return range_sum(node.right, start, end);
      }

      return range_sum(node.left, start, mid) + range_sum(node.right, mid + 1, end);
    }

    public void update(int index, int new_val) {
      update(root, index, new_val);
    }

    private void update(Segment_Tree_Node node, int index, int new_val) {
      if (node.start == node.end) {
        node.sum = new_val;
        return;
      }

      int mid = node.start + (node.end - node.start)/2;
      if (index <= mid) {
        update(node.left, index, new_val);
      } else {
        update(node.right, index, new_val);
      }
      node.sum = node.left.sum + node.right.sum;
    }

    private Segment_Tree_Node build_segment_tree(List<Integer> list, int start, int end) {
      if (start > end) {
        return null;
      }

      Segment_Tree_Node root = new Segment_Tree_Node(start, end);
      if (start == end) {
        root.sum = list.get(start);
        return root;
      }

      int mid = start + (end - start)/2;
      root.left = build_segment_tree(list, start, mid);
      root.right = build_segment_tree(list, mid + 1, end);
      root.sum = root.left.sum + root.right.sum;
      return root;
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
            list = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(list);

          Segment_Tree segment_tree_1 = new Segment_Tree(list);

          Scanner input_reader = new Scanner(System.in);
          String keep_going = "";

          do {
            int choice = input_reader.nextInt();

            switch (choice) {
              case 1:
                System.out.println("Type in the start and end values for the range sum: ");
                int start = input_reader.nextInt();
                int end = input_reader.nextInt();
                System.out.println("range sum [" + start + ", " + end + "] = " + segment_tree_1.range_sum(start, end));
                break;

              case 2:
                System.out.println("Type in the index and new value: ");
                int index = input_reader.nextInt();
                int new_val = input_reader.nextInt();
                segment_tree_1.update(index, new_val);
                break;
            }

            keep_going = input_reader.next();
          } while (keep_going.charAt(0) == 'y' || keep_going.charAt(0) == 'Y');

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] num_str = line.split(" ");

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < num_str.length; i++) {
      list.add(Integer.parseInt(num_str[i]));
    }
    return list;
  } 

  private static List<Integer> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] len_min_max = line.split(" ");
    int list_len = Integer.parseInt(len_min_max[0]);
    int min = Integer.parseInt(len_min_max[1]);
    int max = Integer.parseInt(len_min_max[2]);

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < list_len; i++) {
      int random_num = (int)(Math.random() * (max - min + 1)) + min;
      list.add(random_num);
    }
    return list;
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}