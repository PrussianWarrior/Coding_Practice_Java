import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
 
  For example,
   
  Given n = 3, there are a total of 5 unique BST's.
     1         3      3        2       1
      \       /      /        / \       \
       3     2      1        1   3       2
      /     /        \                    \
     2     1          2                    3
*/

public class Unique_BST_II {
  private static class Node {
    Node left;
    Node right;
    int data;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    boolean keep_going = true;
    for (int N = 1; N <= 5 && keep_going; N++) {
      List<Node> all_valid_bst_1 = construct_all_valid_BST_1(N);
      System.out.println("N = " + N);
      for (int i = 0; i < all_valid_bst_1.size(); i++) {
        boolean is_bst_1 = is_bst_1(all_valid_bst_1.get(i));
        System.out.println("Tree " + (i + 1) + ":");
        traverse_tree(all_valid_bst_1.get(i));
        System.out.println("\nVALID BST = " + is_bst_1);

        if (!is_bst_1) {
          keep_going = false;
          System.out.println("FAILED");
          break;
        }
      }
      System.out.println("_____________________________________________________________________" + 
        "______________________________________________");

    }
  }

  private static List<Node> construct_all_valid_BST_1(int N) {
    if (N <= 0) {
      return new ArrayList<Node>();
    }
    return construct_all_valid_BST_1(1, N);
  }

  private static List<Node> construct_all_valid_BST_1(int start, int end) {
    List<Node> all_bst = new ArrayList<>();
    if (start > end) {
      all_bst.add(null);
      return all_bst;
    }

    for (int i = start; i <= end; i++) {
      List<Node> all_left_bst = construct_all_valid_BST_1(start, i - 1);
      List<Node> all_right_bst = construct_all_valid_BST_1(i + 1, end);
      for (Node left_bst : all_left_bst) {
        for (Node right_bst : all_right_bst) {
          Node root = new Node(i);
          root.left = left_bst;
          root.right = right_bst;
          all_bst.add(root);
        }
      }
    }

    return all_bst;
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
    if (right != null && node.data > right.data) {
      return false;
    }
    return is_bst_1(node.left, left, node) && is_bst_1(node.right, node, right);
  }

  private static void traverse_tree(Node root) {
    System.out.println("Pre order traversal method 2:");
    for (Node node : pre_order_traversal_2(root)) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("In order traversal method 2:");
    for (Node node : in_order_traversal_2(root)) {
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
    if (root == null) {
      return new ArrayList<>();
    }

    LinkedList<Node> stack = new LinkedList<>();
    List<Node> pre_order_list = new ArrayList<>();
    stack.addFirst(root);

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
    LinkedList<Node> stack = new LinkedList<>();
    List<Node> in_order_list = new ArrayList<>();
    Node curr = root;

    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.addFirst(curr);
        curr = curr.left;
      } else {
        Node top = stack.removeFirst();
        in_order_list.add(top);
        if (top.right != null) {
          stack.addFirst(top.right);
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
    if (root == null) {
      return new ArrayList<Node>();
    }

    LinkedList<Node> stack = new LinkedList<>();
    stack.addFirst(root);
    List<Node> post_order_list = new ArrayList<>();
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
      } else if (top.right == prev_node) {
        post_order_list.add(top);
        stack.removeFirst();
      }
      prev_node = top;
    }

    return post_order_list;
  }
}