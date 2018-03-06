import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a binary tree, return the bottom-up level order traversal of its nodes' values. 
  (ie, from left to right, level by level from leaf to root).

  For example:
  Given binary tree [3,9,20,null,null,15,7],
        3
      /   \
     9    20
        /    \
       15     7

  return its bottom-up level order traversal as:
  [
    [15,7],
    [9,20],
    [3]
  ]
*/

public class Binary_Tree_Level_Order_Traversal_II {
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

          List<String> all_data_instruction_code = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            line = br.readLine();
            while (!line.startsWith("***")) {
              all_data_instruction_code.add(line);
              line = br.readLine();
            }
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Node root = build_tree(all_data_instruction_code);
          traverse_tree(root);

          long start = System.currentTimeMillis();
          List<List<Integer>> data_all_levels_1 = level_order_traversal_1(root);
          long end = System.currentTimeMillis();
          long execution_time_1 = end - start;

          start = System.currentTimeMillis();
          List<List<Integer>> data_all_levels_2 = level_order_traversal_2(root);
          end = System.currentTimeMillis();
          long execution_time_2 = end - start;

          System.out.println("Level order traveral:");
          System.out.println("SOLUTION 1:");
          for (int i = 0; i < data_all_levels_1.size(); i++) {
            System.out.printf("%5d: ", i + 1);
            for (int data : data_all_levels_1.get(i)) {
              System.out.printf("%5d", data);
            }
            System.out.println();
          }

          System.out.println("Execution time = " + execution_time_1);

          System.out.println("SOLUTION 2:");
          for (int i = 0; i < data_all_levels_2.size(); i++) {
            System.out.printf("%5d: ", i + 1);
            for (int data : data_all_levels_2.get(i)) {
              System.out.printf("%5d", data);
            }
            System.out.println();
          }

          System.out.println("Execution time = " + execution_time_2);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<List<Integer>> level_order_traversal_1(Node root) {
    List<List<Integer>> data_all_levels = new ArrayList<>();
    LinkedList<Node> current_level_node_queue = new LinkedList<>();
    LinkedList<Node> next_level_node_queue = new LinkedList<>();

    if (root != null) {
      current_level_node_queue.addLast(root);
    }

    while (!current_level_node_queue.isEmpty()) {
      List<Integer> current_level_data = new ArrayList<>();

      while (!current_level_node_queue.isEmpty()) {
        Node first = current_level_node_queue.removeFirst();
        current_level_data.add(first.data);
        if (first.left != null) {
          next_level_node_queue.addLast(first.left);
        }
        if (first.right != null) {
          next_level_node_queue.addLast(first.right);
        }
      }

      data_all_levels.add(current_level_data);
      current_level_node_queue.addAll(next_level_node_queue);
      next_level_node_queue.clear();
    }

    int start = 0;
    int end = data_all_levels.size() - 1;
    while (start < end) {
      List<Integer> temp = data_all_levels.get(start);
      data_all_levels.set(start, data_all_levels.get(end));
      data_all_levels.set(end, temp);
      start++;
      end--;
    }

    return data_all_levels;    
  }

  private static List<List<Integer>> level_order_traversal_2(Node root) {
    List<List<Integer>> data_all_levels = new ArrayList<>();
    pre_order_traversal_1(root, 0, data_all_levels);

    int start = 0;
    int end = data_all_levels.size() - 1;
    while (start < end) {
      List<Integer> temp = data_all_levels.get(start);
      data_all_levels.set(start, data_all_levels.get(end));
      data_all_levels.set(end, temp);
      start++;
      end--;
    }

    return data_all_levels;
  }

  private static void pre_order_traversal_1(Node node, int level, 
      List<List<Integer>> data_all_levels) {
    if (node == null) {
      return;
    }

    if (data_all_levels.size() == level) {
      data_all_levels.add(new ArrayList<Integer>());
    }
    data_all_levels.get(level).add(node.data);
    pre_order_traversal_1(node.left, level + 1, data_all_levels);
    pre_order_traversal_1(node.right, level + 1, data_all_levels);
  }

  private static Node build_tree(List<String> all_data_instruction_code) {
    Node root = null;
    for (String data_instruction_code : all_data_instruction_code) {
      root = build_tree(root, data_instruction_code);
    }
    return root;
  }

  private static Node build_tree(Node root, String data_instruction_code) {
    data_instruction_code = remove_extra_spaces_1(data_instruction_code);
    int index_of_colon = data_instruction_code.indexOf(":");

    String data_part = data_instruction_code.substring(0, index_of_colon).trim();
    String instruction_part = data_instruction_code.substring(index_of_colon + 1).trim();

    if (root == null) {
      return new Node(Integer.parseInt(data_part));
    } else {
      Node parent = null;
      Node curr = root;
      boolean right = true;
      for (int i = 0; i < instruction_part.length(); i++) {
        parent = curr;
        if (instruction_part.charAt(i) == '0') {
          curr = curr.left;
          right = false;
        } else if (instruction_part.charAt(i) == '1') {
          curr = curr.right;
          right = true;
        } else {
          throw new IllegalArgumentException("Cannot recognize the instruction code \"" + 
            instruction_part.charAt(i) + "\"");
        }
      }

      if (right) {
        parent.right = new Node(Integer.parseInt(data_part));
      } else {
        parent.left = new Node(Integer.parseInt(data_part));
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
}