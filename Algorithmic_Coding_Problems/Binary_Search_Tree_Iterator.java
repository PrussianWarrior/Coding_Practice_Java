import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Implement an iterator over a binary search tree (BST). Your iterator will be 
  initialized with the root node of a BST.
  Calling next() will return the next smallest number in the BST.
  Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, 
  where h is the height of the tree.
*/

public class Binary_Search_Tree_Iterator {
  private static class Node {
    Node left;
    Node right;
    int data;

    public Node(int data) {
      this.data = data;
    }
  }

  private static class BST_Iterator {
    private LinkedList<Node> stack;

    public BST_Iterator(Node root) {
      stack = new LinkedList<>();
      while (root != null) {
        stack.addFirst(root);
        root = root.left;
      }
    }

    public boolean has_next() {
      return !stack.isEmpty();
    }

    public int next() {
      Node top = stack.removeFirst();
      int returned_data = top.data;
      if (top.right != null) {
        Node curr = top.right;
        while (curr != null) {
          stack.addFirst(curr);
          curr = curr.left;
        }
      }

      return returned_data;
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

          List<Integer> input = null;
          if (line.indexOf("Customized:") >= 0) {
            input = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Node root = build_bst_tree(input);
          traverse_tree(root);
          boolean is_bst_1 = is_bst_1(root);

          System.out.println("Is the tree BST = " + is_bst_1);
          BST_Iterator bst_iterator = new BST_Iterator(root);
          while (bst_iterator.has_next()) {
            System.out.printf("%5d", bst_iterator.next());
          }
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

  private static Node build_bst_tree(List<Integer> all_integers) {
    Node root = null;
    for (int integer : all_integers) {
      root = build_bst_tree(root, integer);
    }
    return root;
  }

  private static Node build_bst_tree(Node root, int data) {
    if (root == null) {
      return new Node(data);
    }

    if (root.data == data) {
      return root;
    } else if (root.data < data) {
      root.right = build_bst_tree(root.right, data);
    } else {
      root.left = build_bst_tree(root.left, data);
    }
    return root;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] all_integer_str = line.split(" ");
    List<Integer> input = new ArrayList<>();
    for (String integer_str : all_integer_str) {
      input.add(Integer.parseInt(integer_str));
    }
    return input;
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

  private static boolean is_bst_1(Node root) {
    return is_bst_1(root, null, null);
  }

  private static boolean is_bst_1(Node node, Node left, Node right) {
    if (node == null) {
      return true;
    }
    if (left != null && left.data > node.data) {
      return false;
    }
    if (right != null && right.data < node.data) {
      return false;
    }
    return is_bst_1(node.left, left, node) && is_bst_1(node.right, node, right);
  }
}