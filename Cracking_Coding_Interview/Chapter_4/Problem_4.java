import java.util.*;

public class Problem_4 {
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
      arr[i] = (int)(Math.random() * 109) - 59;
    }
    Arrays.sort(arr);

    Node root = construct_bst(arr);
    traverse_tree(root);

    int max_depth = max_depth_1(root);
    int min_depth_1 = min_depth_1(root);
    int min_depth_2 = min_depth_2(root);

    System.out.println("Max depth = " + max_depth);
    System.out.println("Min depth 1 = " + min_depth_1);
    System.out.println("Min depth 2 = " + min_depth_2);

    boolean is_balanced = is_balanced_1(root);
    System.out.println("Binary tree is balanced = " + is_balanced);
  }

  private static boolean is_balanced_1(Node root) {
    return max_height_diff(root) != -1;
  }

  private static int max_height_diff(Node root) {
    if (root == null) {
      return 0;
    }
    int height_diff_left = max_height_diff(root.left);
    if (height_diff_left == -1) {
      return -1;
    }

    int height_diff_right = max_height_diff(root.right);
    if (height_diff_right == -1) {
      return -1;
    }

    int max_diff = Math.abs(height_diff_left - height_diff_right);
    return max_diff > 1 ? -1 : 1 + Math.max(height_diff_right, height_diff_left);
  }

  private static int max_depth_1(Node root) {
    if (root == null) {
      return 0;
    }
    return 1 + Math.max(max_depth_1(root.left), max_depth_1(root.right));
  }

  private static int min_depth_1(Node root) {
    if (root == null) {
      return 0;
    }
    if (root.left == null) {
      return min_depth_1(root.right) + 1;
    }
    if (root.right == null) {
      return min_depth_1(root.left) + 1;
    }
    return 1 + Math.min(min_depth_1(root.left), min_depth_1(root.right));
  }

  private static int min_depth_2(Node root) {
    LinkedList<Node> queue = new LinkedList<>();
    int min_depth = 0;

    if (root != null) {
      queue.add(root);
    }

    Node left_most_node = root;

    while (!queue.isEmpty()) {
      Node node = queue.removeFirst();
      if (node.left == null && node.right == null) {
        break;
      }
      if (node.left != null) {
        queue.add(node.left);
      }
      if (node.right != null) {
        queue.add(node.right);
      }
      if (node == left_most_node) {
        left_most_node = node.right != null ? node.right : node.left;
        min_depth++;
      }
    }

    return min_depth;
  }

  private static Node construct_bst(int[] arr) {
    return construct_bst(arr, 0, arr.length - 1);
  }

  private static Node construct_bst(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = start + (end-start)/2;
    Node root = new Node(arr[mid]);
    root.left = construct_bst(arr, start, mid - 1);
    root.right = construct_bst(arr, mid + 1, end);
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
}