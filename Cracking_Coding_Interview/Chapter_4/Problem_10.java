import java.util.*;

public class Problem_10 {
  private static class Node {
    int data;
    Node left, right;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    int N_1 = 15;
    int[] arr_1 = new int[N_1];
    for (int i = 0; i < N_1; i++) {
      arr_1[i] = i + 1;
    }

    Node tree_1 = construct_binary_tree(arr_1);
    System.out.println("Tree 1:");
    traverse_tree(tree_1);
    
    int N_2 = 7;
    int[] arr_2 = new int[N_2];
    int[] arr_3 = new int[N_2];
    int[] arr_4 = new int[N_2];

    for (int i = 0; i < N_2; i++) {
      arr_2[i] = i + 1;
      arr_3[i] = i + 9;
      arr_4[i] = i + 21;
    }

    Node tree_2 = construct_binary_tree(arr_2);
    System.out.println("Tree 2:");
    traverse_tree(tree_2);

    Node tree_3 = construct_binary_tree(arr_3);
    System.out.println("Tree 3:");
    traverse_tree(tree_3);

    Node tree_4 = construct_binary_tree(arr_4);
    System.out.println("Tree 4:");
    traverse_tree(tree_4);

    boolean is_tree_2_subtree_of_tree_1 = is_tree_2_subtree_of_tree_1(tree_1, tree_2);
    boolean is_tree_3_subtree_of_tree_1 = is_tree_2_subtree_of_tree_1(tree_1, tree_3);
    boolean is_tree_4_subtree_of_tree_1 = is_tree_2_subtree_of_tree_1(tree_1, tree_4);

    System.out.println("Tree 2 is subtree of tree 1 = " + is_tree_2_subtree_of_tree_1);
    System.out.println("Tree 3 is subtree of tree 1 = " + is_tree_3_subtree_of_tree_1);
    System.out.println("Tree 4 is subtree of tree 1 = " + is_tree_4_subtree_of_tree_1);
  }

  private static boolean is_tree_2_subtree_of_tree_1(Node tree_1, Node tree_2) {
    if (tree_1 == null && tree_2 == null) {
      return true;
    }

    if ((tree_1 == null && tree_2 != null) || 
        (tree_1 != null && tree_2 == null)) {
      return false;
    }

    if (tree_1.data == tree_2.data) {
      return is_tree_2_subtree_of_tree_1(tree_1.left, tree_2.left) && 
             is_tree_2_subtree_of_tree_1(tree_1.right, tree_2.right);
    }

    return is_tree_2_subtree_of_tree_1(tree_1.left, tree_2) ||
           is_tree_2_subtree_of_tree_1(tree_1.right, tree_2);
  }

  private static Node construct_bst_1(List<Integer> list) {
    Node root = null;
    for (int n : list) {
      root = construct_bst_1(root, n);
    }
    return root;
  }

  private static Node construct_bst_1(Node root, int data) {
    if (root == null) {
      return new Node(data);
    }
    if (root.data == data) {
      root.data = data;
    } else if (root.data < data) {
      root.right = construct_bst_1(root.right, data);
    } else {
      root.left = construct_bst_1(root.left, data);
    }
    return root;
  }

  private static Node construct_binary_tree(int[] arr) {
    return construct_binary_tree(arr, 0, arr.length - 1);
  }

  private static Node construct_binary_tree(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = start + (end-start)/2;
    Node root = new Node(arr[mid]);
    root.left = construct_binary_tree(arr, start, mid - 1);
    root.right = construct_binary_tree(arr, mid + 1, end);
    return root;
  }

  private static void traverse_tree(Node root) {
    System.out.println("Pre order traversal method 1:");
    for (int n : pre_order_traversal_1(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("Pre order traversal method 2:");
    for (int n : pre_order_traversal_2(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("In order traversal method 1:");
    for (int n : in_order_traversal_1(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("In order traversal method 2:");
    for (int n : in_order_traversal_2(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("Post order traversal method 1:");
    for (int n : post_order_traversal_1(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("Post order traversal method 2:");
    for (int n : post_order_traversal_2(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }

  private static List<Integer> pre_order_traversal_1(Node root) {
    LinkedList<Node> stack = new LinkedList<>();
    List<Integer> pre_order_list = new ArrayList<>();

    if (root != null) {
      stack.addFirst(root);
    }

    while (!stack.isEmpty()) {
      Node top = stack.removeFirst();
      pre_order_list.add(top.data);
      if (top.right != null) {
        stack.addFirst(top.right);
      }
      if (top.left != null) {
        stack.addFirst(top.left);
      }
    }

    return pre_order_list;
  }

  private static List<Integer> pre_order_traversal_2(Node root) {
    List<Integer> pre_order_list = new ArrayList<>();
    pre_order_traversal_2(root, pre_order_list);
    return pre_order_list;
  }

  private static void pre_order_traversal_2(Node root, List<Integer> pre_order_list) {
    if (root == null) {
      return;
    }
    pre_order_list.add(root.data);
    pre_order_traversal_2(root.left, pre_order_list);
    pre_order_traversal_2(root.right, pre_order_list);
  }

  private static List<Integer> in_order_traversal_1(Node root) {
    Node curr = root;
    LinkedList<Node> stack = new LinkedList<>();
    List<Integer> in_order_list = new ArrayList<>();

    while (curr != null || !stack.isEmpty()) {
      if (curr != null) {
        stack.addFirst(curr);
        curr = curr.left;
      } else {
        Node top = stack.removeFirst();
        in_order_list.add(top.data);
        curr = top.right;
      }
    }

    return in_order_list;
  }

  private static List<Integer> in_order_traversal_2(Node root) {
    List<Integer> in_order_list = new ArrayList<>();
    in_order_traversal_2(root, in_order_list);
    return in_order_list;
  }

  private static void in_order_traversal_2(Node root, List<Integer> in_order_list) {
    if (root == null) {
      return;
    }
    in_order_traversal_2(root.left, in_order_list);
    in_order_list.add(root.data);
    in_order_traversal_2(root.right, in_order_list);
  }

  private static List<Integer> post_order_traversal_1(Node root) {
    LinkedList<Node> stack = new LinkedList<>();
    List<Integer> post_order_list = new ArrayList<>();
    if (root != null) {
      stack.addFirst(root);
    }

    Node prec = null;
    while (!stack.isEmpty()) {
      Node top = stack.getFirst();
      if (prec == null || prec.left == top || prec.right == top) {
        if (top.left != null) {
          stack.addFirst(top.left);
        } else if (top.right != null) {
          stack.addFirst(top.right);
        } else {
          stack.removeFirst();
          post_order_list.add(top.data);
        }
      } else if (top.left == prec) {
        if (top.right != null) {
          stack.addFirst(top.right);
        } else {
          stack.removeFirst();
          post_order_list.add(top.data);
        }
      } else {
        stack.removeFirst();
        post_order_list.add(top.data);
      }
      prec = top;
    }

    return post_order_list;
  }

  private static List<Integer> post_order_traversal_2(Node root) {
    List<Integer> post_order_list = new ArrayList<>();
    post_order_traversal_2(root, post_order_list);
    return post_order_list;
  }

  private static void post_order_traversal_2(Node root, List<Integer> post_order_list) {
    if (root == null) {
      return;
    }
    post_order_traversal_2(root.left, post_order_list);
    post_order_traversal_2(root.right, post_order_list);
    post_order_list.add(root.data);
  }

  private static void print_list(List<Integer> list) {
    for (int n : list) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }

  private static boolean is_sorted(List<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i - 1) > list.get(i)) {
        return false;
      }
    }
    return true;
  }
}