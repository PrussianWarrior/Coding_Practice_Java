import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  You are given a binary tree in which each node contains an integer value.

  Find the number of paths that sum to a given value.

  The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).

  The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

  Example:

  root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

        10
       /  \
      5   -3
     / \    \
    3   2   11
   / \   \
  3  -2   1

  Return 3. The paths that sum to 8 are:

  1.  5 -> 3
  2.  5 -> 2 -> 1
  3. -3 -> 11
*/

public class Path_Sum_III {
  private static class Tree_Sum {
    Node root;
    int sum;

    public Tree_Sum(Node root, int sum) {
      this.root = root;
      this.sum = sum;
    }
  }

  private static class Node {
    Node left;
    Node right;
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

          List<String> nodes = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            line = br.readLine();
            while (!line.startsWith("**")) {
              nodes.add(line);
              line = br.readLine();
            }
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Tree_Sum tree_sum = build_tree(nodes);
          traverse_tree(tree_sum.root);
          System.out.println("Sum = " + tree_sum.sum);

          // long start = System.currentTimeMillis();
          // int num_path_sum_1 = count_paths_sum_1(tree_sum.root, tree_sum.sum);
          // long stop = System.currentTimeMillis();
          // long execution_time_soln_1 = stop - start;

          // start = System.currentTimeMillis();
          // int num_path_sum_2 = count_paths_sum_2(tree_sum.root, tree_sum.sum);
          // stop = System.currentTimeMillis();
          // long execution_time_soln_2 = stop - start;

          long start = System.currentTimeMillis();
          int num_path_sum_3 = count_paths_sum_3(tree_sum.root, tree_sum.sum);
          long stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          System.out.println("NUMBER OF PATHS SUM = " + tree_sum.sum);
          // System.out.println("Method 1: " + num_path_sum_1 + ", execution time = " + execution_time_soln_1);
          // System.out.println("Method 2: " + num_path_sum_2 + ", execution time = " + execution_time_soln_2);
          System.out.println("Method 3: " + num_path_sum_3 + ", execution time = " + execution_time_soln_3);
          
          // if (num_path_sum_1 != num_path_sum_2 ||
          //     num_path_sum_1 != num_path_sum_3) {
          //   System.out.println("FAILED");
          //   break;
          // }

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  /*
    Method 1 doesn't work. Needs analysis
  */
  private static int count_paths_sum_1(Node root, int target_sum) {
    int[] num_path = {0};
    count_paths_sum_1_helper(root, 0, target_sum, num_path);
    return num_path[0];
  }

  private static void count_paths_sum_1_helper(Node node, int running_sum, int target_sum, 
      int[] num_path) {
    System.out.println("Running sum = " + running_sum);
    if (node == null) {
      return;
    }

    if (running_sum == target_sum) {
      num_path[0]++;
    }

    count_paths_sum_1_helper(node.left, running_sum + node.data, target_sum, num_path);
    count_paths_sum_1_helper(node.right, running_sum + node.data, target_sum, num_path);
    count_paths_sum_1_helper(node.left, node.data, target_sum, num_path);
    count_paths_sum_1_helper(node.right, node.data, target_sum, num_path);
  }

  /*
    Method 2 doesn't work. Needs analysis
  */
  private static int count_paths_sum_2(Node root, int sum) {
    return count_paths_sum_2_helper(root, 0, sum);
  }

  private static int count_paths_sum_2_helper(Node node, int running_sum, int target_sum) {
    if (node == null) {
      return 0;
    }

    int num_path = running_sum == target_sum ? 1 : 0;
    num_path += count_paths_sum_2_helper(node.left, running_sum + node.data, target_sum);
    num_path += count_paths_sum_2_helper(node.right, running_sum + node.data, target_sum);
    num_path += count_paths_sum_2_helper(node.left, node.data, target_sum);
    num_path += count_paths_sum_2_helper(node.right, node.data, target_sum);
    return num_path;
  }

  private static int count_paths_sum_3(Node root, int target_sum) {
    Map<Integer, Integer> running_sum_freq = new HashMap<>();
    running_sum_freq.put(0, 1);
    return count_paths_sum_3_helper(root, running_sum_freq, 0, target_sum);
  }

  private static int count_paths_sum_3_helper(Node node, Map<Integer, Integer> running_sum_freq, int running_sum, 
      int target_sum) {
    if (node == null) {
      return 0;
    }

    running_sum += node.data;
    int num_path = running_sum_freq.containsKey(running_sum - target_sum) ? 
      running_sum_freq.get(running_sum - target_sum) : 0;
    int count = running_sum_freq.containsKey(running_sum) ? running_sum_freq.get(running_sum) : 0;
    running_sum_freq.put(running_sum, count + 1);
    num_path += count_paths_sum_3_helper(node.left, running_sum_freq, running_sum, target_sum) + 
                count_paths_sum_3_helper(node.right, running_sum_freq, running_sum, target_sum);
    running_sum_freq.put(running_sum, count);
    return num_path;
  }

  private static Tree_Sum build_tree(List<String> nodes) {
    Node root = null;
    int sum = 0;
    for (String node : nodes) {
      if (node.startsWith("Sum")) {
        sum = get_sum(node);
      } else {
        root = build_tree(root, node);
      }
    }
    return new Tree_Sum(root, sum);
  }

  private static Node build_tree(Node root, String line) {
    line = remove_extra_spaces_1(line);
    int index_of_colon = line.indexOf(":");
    int data = Integer.parseInt(line.substring(0, index_of_colon).trim());
    String traverse_instruction = line.substring(index_of_colon + 1).trim();

    if (root == null) {
      root = new Node(data);
    } else {
      Node parent = null;
      Node current = root;
      boolean right = true;
      for (int i = 0; i < traverse_instruction.length(); i++) {
        parent = current;
        char c = traverse_instruction.charAt(i);
        if (c == '0') {
          right = false;
          current = current.left;
        } else if (c == '1') {
          right = true;
          current = current.right;
        } else {
          throw new IllegalArgumentException("Cannot recognize instruction character " + c);
        }
      }

      if (right) {
        parent.right = new Node(data);
      } else {
        parent.left = new Node(data);
      }
    }

    return root;
  }

  private static int get_sum(String line) {
    int index_of_colon = line.indexOf(":");
    String sum = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    return Integer.parseInt(sum);
  }

  private static String remove_extra_spaces_1(String line) {
    line = line.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (Character.isSpace(line.charAt(i))) {
        start = i;
      } else if (i == line.length() - 1 || Character.isSpace(line.charAt(i + 1))) {
        new_str.append(line.substring(start + 1, i + 1));
        if (i < line.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }

  private static void traverse_tree(Node root) {
    System.out.println("Pre order traversal method 1:");
    for (Node node : pre_order_traversal_1(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Pre order traversal method 2:");
    for (Node node : pre_order_traversal_2(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("In order traversal method 1:");
    for (Node node : in_order_traversal_1(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("In order traversal method 2:");
    for (Node node : in_order_traversal_2(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Post order traversal method 1:");
    for (Node node : post_order_traversal_1(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Post order traversal method 2:");
    for (Node node : post_order_traversal_2(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();
  }

  private static List<Node> pre_order_traversal_1(Node root) {
    List<Node> pre_order_list = new ArrayList<>();
    pre_order_traversal_1(root, pre_order_list);
    return pre_order_list;
  }

  private static void pre_order_traversal_1(Node root, List<Node> pre_order_list) {
    if (root == null) {
      return;
    }

    pre_order_list.add(root);
    pre_order_traversal_1(root.left, pre_order_list);
    pre_order_traversal_1(root.right, pre_order_list);
  }

  private static List<Node> pre_order_traversal_2(Node root) {
    LinkedList<Node> stack = new LinkedList<>();
    List<Node> pre_order_list = new ArrayList<>();
    if (root != null) {
      stack.addFirst(root);
    }

    while (!stack.isEmpty()) {
      Node top = stack.removeFirst();
      pre_order_list.add(top);
      if (top.right != null) {
        stack.addFirst(top.right);
      }

      if (top.left != null) {
        stack.addFirst(top.left);
      }
    }
    return pre_order_list;
  }

  private static List<Node> in_order_traversal_1(Node root) {
    List<Node> in_order_list = new ArrayList<>();
    in_order_traversal_1(root, in_order_list);
    return in_order_list;
  }

  private static void in_order_traversal_1(Node root, List<Node> in_order_list) { 
    if (root == null) {
      return;
    }
    in_order_traversal_1(root.left, in_order_list);
    in_order_list.add(root);
    in_order_traversal_1(root.right, in_order_list);
  }

  private static List<Node> in_order_traversal_2(Node root) {
    List<Node> in_order_list = new ArrayList<>();
    LinkedList<Node> stack = new LinkedList<>();
    Node curr = root;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.addFirst(curr);
        curr = curr.left;
      } else {
        Node top = stack.removeFirst();
        in_order_list.add(top);
        if (top.right != null) {
          curr = top.right;
        }
      }
    }
    return in_order_list;
  }

  private static List<Node> post_order_traversal_1(Node root) {
    List<Node> post_order_list = new ArrayList<>();
    post_order_traversal_1(root, post_order_list);
    return post_order_list;
  }

  private static void post_order_traversal_1(Node root, List<Node> post_order_list) {
    if (root == null) {
      return;
    }
    post_order_traversal_1(root.left, post_order_list);
    post_order_traversal_1(root.right, post_order_list);
    post_order_list.add(root);
  }

  private static List<Node> post_order_traversal_2(Node root) {
    List<Node> post_order_list = new ArrayList<>();
    LinkedList<Node> stack = new LinkedList<>();
    if (root != null) {
      stack.addFirst(root);
    }
    Node prev_node = null;

    while (!stack.isEmpty()) {
      Node top = stack.getFirst();
      if (prev_node == null || prev_node.left == top || prev_node.right == top) {
        if (top.left != null) {
          stack.addFirst(top.left);
        } else if (top.right != null) {
          stack.addFirst(top.right);
        } else {
          post_order_list.add(top);
          stack.removeFirst();
        }
      } else if (top.left == prev_node) {
        if (top.right != null) {
          stack.addFirst(top.right);
        } else {
          post_order_list.add(top);
          stack.removeFirst();
        }
      } else {
        post_order_list.add(top);
        stack.removeFirst();
      }
      prev_node = top;
    }

    return post_order_list;
  }
}