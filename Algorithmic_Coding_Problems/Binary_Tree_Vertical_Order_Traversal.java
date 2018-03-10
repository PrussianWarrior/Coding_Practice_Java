import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a binary tree, return the vertical order traversal of its nodes' values. 
  (ie, from top to bottom, column by column).
 
  If two nodes are in the same row and column, the order should be from left to right.
  Examples:
   
  Given binary tree [3,9,20,null,null,15,7],
       3
     /   \
    9    20
        /  \
       15   7
   
  return its vertical order traversal as:
  [
    [9],
    [3,15],
    [20],
    [7]
  ]

  Given binary tree [3,9,20,4,5,2,7],
       3
     /   \
    9     20
   / \   /  \
  4   5 2    7

  return its vertical order traversal as:
  [
    [4],
    [9],
    [3,5,2],
    [20],
    [7]
  ]
*/

public class Binary_Tree_Vertical_Order_Traversal {
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

          Node root = build_tree(nodes);
          traverse_tree(root);

          long start = System.currentTimeMillis();
          List<List<Integer>> vertical_order_1 = vertical_order_traversal_1(root);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("VERTICAL ORDER:");
          System.out.println("Method 1: ");
          print_vertical_order(vertical_order_1);
          System.out.println();

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<List<Integer>> vertical_order_traversal_1(Node root) {
    if (root == null) {
      return new ArrayList<>();
    }

    LinkedList<Integer> vertical_pos_queue = new LinkedList<>();
    vertical_pos_queue.addLast(0);

    LinkedList<Node> node_queue = new LinkedList<>();
    node_queue.addLast(root);

    int min_pos = Integer.MAX_VALUE;
    int max_pos = Integer.MIN_VALUE;

    Map<Integer, List<Integer>> vertical_position_list_map = new HashMap<>();

    while (!node_queue.isEmpty()) {
      Node node = node_queue.removeFirst();
      int level = vertical_pos_queue.removeFirst();

      min_pos = Math.min(min_pos, level);
      max_pos = Math.max(max_pos, level);

      if (!vertical_position_list_map.containsKey(level)) {
        vertical_position_list_map.put(level, new ArrayList<>());
      }
      
      vertical_position_list_map.get(level).add(node.data);

      if (node.left != null) {
        node_queue.addLast(node.left);
        vertical_pos_queue.addLast(level - 1);
      }

      if (node.right != null) {
        node_queue.addLast(node.right);
        vertical_pos_queue.addLast(level + 1);
      }
    }

    List<List<Integer>> vertical_order = new ArrayList<>();
    for (int i = min_pos; i <= max_pos; i++) {
      if (vertical_position_list_map.containsKey(i)) {
        vertical_order.add(vertical_position_list_map.get(i));
      }
    }

    return vertical_order;
  }

  private static Node build_tree(List<String> nodes) {
    Node root = null;
    for (String node : nodes) {
      root = build_tree(root, node);
    }
    return root;
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

  private static void print_vertical_order(List<List<Integer>> vertical_order) {
    for (int i = 0; i < vertical_order.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : vertical_order.get(i)) {
        System.out.printf("%5d", n);
      }
      System.out.println();
    }
  }
}