import java.util.*;

public class Problem_12 {
  private static class Node {
    int data;
    Node left, right;

    public Node(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    int[] arr_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    Node tree_1 = construct_binary_tree(arr_1);
    System.out.println("Tree 1:");
    traverse_tree(tree_1);
    
    int target_sum = 15;
    int num_path_equals_target_sum_1 = count_num_of_path_sum_1(tree_1, target_sum);
    int num_path_equals_target_sum_2 = count_num_of_path_sum_2(tree_1, target_sum);

    System.out.println("Number of tree paths that amount to " + target_sum);
    System.out.println("Method 1: " + num_path_equals_target_sum_1);
    System.out.println("Method 2: " + num_path_equals_target_sum_2);

    if (num_path_equals_target_sum_1 != num_path_equals_target_sum_2) {
      System.out.println("METHOD 1 does not yield the same result as METHOD 2.");
    }
  }

  private static int count_num_of_path_sum_1(Node root, int target) {
    if (root == null) {
      return 0;
    }
    int num_path_from_root = count_num_path_from_node(root, target, 0);
    int num_path_from_left_subtree = count_num_of_path_sum_1(root.left, target);
    int num_path_from_right_subtree = count_num_of_path_sum_1(root.right, target);
    return num_path_from_root + num_path_from_left_subtree + num_path_from_right_subtree;
  }

  private static int count_num_path_from_node(Node node, int target, int running_sum) {
    if (node == null) {
      return 0;
    }
    running_sum += node.data;
    int count = 0;
    if (running_sum == target) {
      count++;
    }
    count += count_num_path_from_node(node.left, target, running_sum);
    count += count_num_path_from_node(node.right, target, running_sum);
    return count;
  }

  private static int count_num_of_path_sum_2(Node root, int target) {
    Map<Integer, Integer> prefix_sum_count = new HashMap<>();
    prefix_sum_count.put(0, 1);
    return count_num_of_path_sum_2(root, prefix_sum_count, target, 0);
  }

  private static int count_num_of_path_sum_2(Node node, Map<Integer, Integer> prefix_sum_count, 
      int target, int running_sum) {
    if (node == null) {
      return 0;
    }
    running_sum += node.data;
    int new_count_running_sum = prefix_sum_count.containsKey(running_sum) ? 
        prefix_sum_count.get(running_sum) + 1 : 1;
    prefix_sum_count.put(running_sum, new_count_running_sum);
    int count = prefix_sum_count.containsKey(running_sum - target) ? prefix_sum_count.get(running_sum - target) : 0;
    count += count_num_of_path_sum_2(node.left, prefix_sum_count, target, running_sum);
    count += count_num_of_path_sum_2(node.right, prefix_sum_count, target, running_sum);
    prefix_sum_count.put(running_sum, new_count_running_sum - 1);
    return count;
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