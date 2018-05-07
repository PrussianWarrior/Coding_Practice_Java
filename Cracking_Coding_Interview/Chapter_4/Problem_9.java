import java.util.*;

public class Problem_9 {
  private static class Node {
    int data;
    Node left, right;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int[] arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = i + 1;
    }

    Arrays.sort(arr);
    Node bst_root_1 = construct_binary_tree(arr);
    System.out.println("Tree 1:");
    traverse_tree(bst_root_1);

    boolean is_bst_1 = is_bst_1(bst_root_1);
    System.out.println("Tree is bst = " + is_bst_1);
    System.out.println();

    List<List<Integer>> all_add_orders = get_add_order_for_bst_1(bst_root_1);
    System.out.println("All add order are:");
    for (int i = 0; i < all_add_orders.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : all_add_orders.get(i)) {
        System.out.printf("%5d", n);
      }
      System.out.println();
      // Node bst_root = construct_bst_1(all_add_orders.get(i));
      // traverse_tree(bst_root);
      // boolean is_bst = is_sorted(in_order_traversal_1(bst_root));
      // System.out.println("This add order yields a bst = " + is_bst);
      // if (!is_bst) {
      //   System.out.println("Uh oh. The tree created by this order ");
      // }
      System.out.println("____________________________________________________________________________________\n");
    }
    System.out.println();
  }

  private static List<List<Integer>> get_add_order_for_bst_1(Node root) {
    List<List<Integer>> all_add_orders = new ArrayList<>();
    if (root == null) {
      all_add_orders.add(new ArrayList<>());
      return all_add_orders;
    }

    List<List<Integer>> add_order_left = get_add_order_for_bst_1(root.left);
    List<List<Integer>> add_order_right = get_add_order_for_bst_1(root.right);
    for (List<Integer> left_order : add_order_left) {
      for (List<Integer> right_order : add_order_right) {
        LinkedList<Integer> temp = new LinkedList<>();
        List<List<Integer>> add_order = new ArrayList<>();
        int[] i1 = {0};
        int[] i2 = {0};
        find_order(left_order, right_order, i1, i2, temp, add_order, root.data);
        all_add_orders.addAll(add_order);
      }
    }
    return all_add_orders;
  }

  private static void find_order(List<Integer> left, List<Integer> right, int[] i1, int[] i2,
     LinkedList<Integer> temp, List<List<Integer>> all_add_orders, int root_data) {
    if (i1[0] == left.size() && i2[0] == right.size()) {
      List<Integer> add_order = new ArrayList<>();
      add_order.add(root_data);
      add_order.addAll(temp);
      all_add_orders.add(add_order);
      return;
    }

    if (i1[0] < left.size()) {
      temp.add(left.get(i1[0]++));
      find_order(left, right, i1, i2, temp, all_add_orders, root_data);
      temp.removeLast();
      i1[0]--;
    }

    if (i2[0] < right.size()) {
      temp.add(right.get(i2[0]++));
      find_order(left, right, i1, i2, temp, all_add_orders, root_data);
      temp.removeLast();
      i2[0]--;
    }
  }

  private static boolean is_bst_1(Node root) {
    return is_bst_1(root, null, null);
  }

  private static boolean is_bst_1(Node root, Node min, Node max) {
    if (root == null) {
      return true;
    }
    if (min != null && min.data > root.data) {
      return false;
    }
    if (max != null && max.data < root.data) {
      return false;
    }
    return is_bst_1(root.left, min, root) && is_bst_1(root.right, root, max);
  }

  private static boolean is_bst_2(Node root) {
    return is_bst_2(root, null, null);
  }

  private static boolean is_bst_2(Node root, Node min, Node max) {
    if (root == null) {
      return true;
    }
    return (min == null || (min != null && min.data <= root.data)) && 
           (max == null || (max != null && max.data >= root.data)) &&
           is_bst_2(root.left, min, root) && is_bst_2(root.right, root, max);
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