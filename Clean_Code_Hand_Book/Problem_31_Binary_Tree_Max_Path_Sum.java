import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_31_Binary_Tree_Max_Path_Sum {
  private static class TreeNode<T extends Comparable> {
    T data;
    TreeNode left, right;
    public TreeNode(T data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          String[] list = process_input(line);
          TreeNode root = build_bst(list);
          traverse_bst(root);

          int min_depth_soln_1 = min_depth_bin_tree_1(root);
          int min_depth_soln_2 = min_depth_bin_tree_2(root);

          System.out.println("Min depth of tree");
          System.out.println("SOLUTION 1: " + min_depth_soln_1);
          System.out.println("SOLUTION 2: " + min_depth_soln_2);

          int max_depth_soln_1 = max_depth_bin_tree_1(root);
          System.out.println("Max depth of tree");
          System.out.println("SOLUTION 1: " + max_depth_soln_1);

          boolean is_balanced_soln_1 = is_balanced_1(root);
          boolean is_balanced_soln_2 = is_balanced_2(root);

          System.out.println("Tree is balanced:");
          System.out.println("Solution 1: " + is_balanced_soln_1);
          System.out.println("Solution 2: " + is_balanced_soln_2);

          if (min_depth_soln_1 != min_depth_soln_2) {
            System.out.println("FAILED: Min height results");
            break;
          }

          if (is_balanced_soln_1 != is_balanced_soln_2) {
            System.out.println("FAILED: balanced check results");
            break;
          }

          int max_sum_soln_1 = max_path_sum_1(root);

          System.out.println("MAX PATH SUM: ");
          System.out.println("Solution 1: " + max_sum_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }   
  }

  private static int max_path_sum_1(TreeNode<Integer> root) {
    int[] max_sum = {Integer.MIN_VALUE};
    max_path_sum_1(root, max_sum);
    return max_sum[0];
  }

  private static int max_path_sum_1(TreeNode<Integer> node, int[] max_sum) {
    if (node == null) {
      return 0;
    }
    int max_sum_left_subtree = max_path_sum_1(node.left, max_sum);
    int max_sum_right_subtree = max_path_sum_1(node.right, max_sum);
    max_sum[0] = Math.max(max_sum[0], max_sum_left_subtree + max_sum_right_subtree + node.data);
    int returned_value = node.data + Math.max(max_sum_right_subtree, max_sum_left_subtree);
    return returned_value > 0 ? returned_value : 0;
  }

  private static TreeNode build_bst(String[] input) {
    if (input == null) {
      throw new IllegalArgumentException("Input list cannot be null");
    }
    if (input.length == 0) {
      return null;
    }

    TreeNode root = new TreeNode(Integer.parseInt(input[0]));
    for (int i = 1; i < input.length; i++) {
      TreeNode prec = null;
      TreeNode ptr = root;
      boolean left = false;
      String data = extract_data(input[i]);
      String branching_instruction = extract_branching_instruction(input[i]);

      for (char c : branching_instruction.toCharArray()) {
        prec = ptr;
        if (c == '0') {
          ptr = ptr.left;
          left = true;
        } else if (c == '1') {
          ptr = ptr.right;
          left = false;
        } else {
          throw new IllegalArgumentException("The branching instruction code must be either 0 or 1");
        }
      }

      if (left) {
        prec.left = new TreeNode(Integer.parseInt(data));
      } else {
        prec.right = new TreeNode(Integer.parseInt(data));
      }
    }
    return root;
  }
  /*  
    Faulty method. Resolution required.
  */
  private static TreeNode build_bst_1(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("Input list cannot be null");
    }

    if (!is_pow_of_2(list.size() + 1)) {
      throw new IllegalArgumentException("The size of input list + 1 must be a power of 2.");
    }

    List<TreeNode> queue = new ArrayList<>();

    int tree_level = (int)(Math.log(list.size() + 1)/Math.log(2));
    System.out.println("Tree_level = " + tree_level);
    for (int i = 0; i < tree_level; i++) {
      int num_of_node = 1 << i;
      for (int j = (1 << i) - 1; j < list.size(); j++) {
        System.out.println("j = " + j);
        if (list.get(j) == null) {
          continue;
        }

        if (j == 0) {
          queue.add(new TreeNode(list.get(j)));
        } else {
          System.out.println("(j - 1)/2 = " + (j - 1)/2);
          TreeNode parent = queue.get((j - 1)/2);
          System.out.println("parent.data = " + parent.data);
          if (j % 2 == 1) {
            parent.left = new TreeNode(list.get(j));
            System.out.println("parent.left.data = " + parent.left.data);
            queue.add(parent.left);
          } else {
            parent.right = new TreeNode(list.get(j));
            System.out.println("parent.right.data = " + parent.right.data);
            queue.add(parent.right);
          }
        }
      }
    }

    System.out.println("Queue.size = " + queue.size());
    for (TreeNode n : queue) {
      System.out.printf("%5d", n.data);
    }
    System.out.println();

    System.out.println("In order traversal inside build_bst");
    for (TreeNode node : in_order_traverse_1(queue.get(0))) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();
    return queue.get(0);
  }

  private static boolean is_balanced_1(TreeNode root) {
    if (root == null) {
      return true;
    }
    return Math.abs(max_depth_bin_tree_1(root.left) - max_depth_bin_tree_1(root.right)) <= 1 &&
           is_balanced_1(root.left) &&
           is_balanced_1(root.right);
  }

  private static boolean is_balanced_2(TreeNode root) {
    return check_height(root) != -1;
  }

  private static int check_height(TreeNode node) {
    if (node == null) {
      return 0;
    }
    int left_subtree_height = check_height(node.left);
    if (left_subtree_height == -1) {
      return -1;
    }
    int right_subtree_height = check_height(node.right);
    if (right_subtree_height == -1) {
      return -1;
    }
    return Math.abs(right_subtree_height - left_subtree_height) > 1 ? -1 : 
           Math.max(left_subtree_height, right_subtree_height) + 1;
  }

  private static int min_depth_bin_tree_1(TreeNode node) {
    if (node == null) {
      return 0;
    }
    if (node.left == null) {
      return min_depth_bin_tree_1(node.right) + 1;
    }
    if (node.right == null) {
      return min_depth_bin_tree_1(node.left) + 1;
    }
    return Math.min(min_depth_bin_tree_1(node.left), min_depth_bin_tree_1(node.right)) + 1;
  }

  private static int min_depth_bin_tree_2(TreeNode root) {
    LinkedList<TreeNode> queue = new LinkedList<>();
    if (root != null) {
      queue.add(root);
    }
    int min_depth = root == null ? 0 : 1;
    TreeNode right_most = root;
    
    while (!queue.isEmpty()) {
      TreeNode node = queue.removeFirst();
      if (node.left == null && node.right == null) {
        break;
      }
      if (node.left != null) {
        queue.add(node.left);
      }
      if (node.right != null) {
        queue.add(node.right);
      }
      if (right_most == node) {
        min_depth++;
        right_most = node.right != null ? node.right : node.left;
      }
    }
    return min_depth;
  }

  private static int max_depth_bin_tree_1(TreeNode node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(max_depth_bin_tree_1(node.left), max_depth_bin_tree_1(node.right));
  }

  private static String[] process_input(String line) {
    String[] words = line.split(" ");
    for (int i = 0; i < words.length; i++) {
      words[i] = words[i].trim();
    }
    return words;
  }

  private static List<TreeNode> in_order_traverse_1(TreeNode root) {
    List<TreeNode> in_order_list = new ArrayList<>();
    in_order_traverse_1(root, in_order_list);
    return in_order_list;
  }

  private static void in_order_traverse_1(TreeNode root, List<TreeNode> in_order_list) {
    if (root == null) {
      return;
    }
    in_order_traverse_1(root.left, in_order_list);
    in_order_list.add(root);
    in_order_traverse_1(root.right, in_order_list);
  }

  private static List<TreeNode> in_order_traverse_2(TreeNode root) {
    List<TreeNode> in_order_list = new ArrayList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();
    TreeNode ptr = root;

    while (!stack.isEmpty() || ptr != null) {
      if (ptr != null) {
        stack.addFirst(ptr);
        ptr = ptr.left;
      } else {
        TreeNode top = stack.removeFirst();
        in_order_list.add(top);
        ptr = top.right;
      }
    }
    return in_order_list;
  }

  private static List<TreeNode> in_order_traverse_3(TreeNode root) {
    List<TreeNode> in_order_list = new ArrayList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();
    TreeNode ptr = root;

    while (ptr != null) {
      stack.addFirst(ptr);
      ptr = ptr.left;
    }

    while (!stack.isEmpty()) {
      TreeNode top = stack.removeFirst();
      in_order_list.add(top);

      if (top.right != null) {
        ptr = top.right;
        while (ptr != null) {
          stack.addFirst(ptr);
          ptr = ptr.left;
        }
      }
    }
    return in_order_list;
  }

  private static List<TreeNode> pre_order_traverse_1(TreeNode root) {
    List<TreeNode> pre_order_list = new ArrayList<>();
    pre_order_traverse_1(root, pre_order_list);
    return pre_order_list;
  }

  private static void pre_order_traverse_1(TreeNode root, List<TreeNode> pre_order_list) {
    if (root == null) {
      return;
    }
    pre_order_list.add(root);
    pre_order_traverse_1(root.left, pre_order_list);
    pre_order_traverse_1(root.right, pre_order_list);
  }

  private static List<TreeNode> pre_order_traverse_2(TreeNode root) {
    List<TreeNode> pre_order_list = new ArrayList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();
    if (root != null) {
      stack.addFirst(root);
    }

    while (!stack.isEmpty()) {
      TreeNode top = stack.removeFirst();
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

  private static List<TreeNode> post_order_traverse_1(TreeNode root) {
    List<TreeNode> post_order_list = new ArrayList<>();
    post_order_traverse_1(root, post_order_list);
    return post_order_list; 
  }

  private static void post_order_traverse_1(TreeNode root, List<TreeNode> post_order_list) {
    if (root == null) {
      return;
    }
    post_order_traverse_1(root.left, post_order_list);
    post_order_traverse_1(root.right, post_order_list);
    post_order_list.add(root);
  }

  private static List<TreeNode> post_order_traverse_2(TreeNode root) {
    List<TreeNode> post_order_list = new ArrayList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();
    if (root != null) {
      stack.addFirst(root);
    }
    TreeNode prev_node = null;
    while (!stack.isEmpty()) {
      TreeNode top = stack.getFirst();
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

  private static void traverse_bst(TreeNode root) {
    List<TreeNode> pre_order_1  = pre_order_traverse_1(root);
    List<TreeNode> pre_order_2  = pre_order_traverse_2(root);
    List<TreeNode> in_order_1   = in_order_traverse_1(root);
    List<TreeNode> in_order_2   = in_order_traverse_2(root);
    List<TreeNode> in_order_3   = in_order_traverse_3(root);
    List<TreeNode> post_order_1 = post_order_traverse_1(root);
    List<TreeNode> post_order_2 = post_order_traverse_2(root);

    System.out.println("Pre order:");
    System.out.println("Method 1:");
    for (TreeNode node : pre_order_1) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Method 2:");
    for (TreeNode node : pre_order_2) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    if (!check_list_for_equality(pre_order_1, pre_order_2)) {
      System.out.println("NOT EQUAL");
      return;
    }

    System.out.println("In order:");
    System.out.println("Method 1:");
    for (TreeNode node : in_order_1) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Method 2:");
    for (TreeNode node : in_order_2) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Method 3:");
    for (TreeNode node : in_order_3) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    if (!check_list_for_equality(in_order_1, in_order_2) || 
        !check_list_for_equality(in_order_1, in_order_3)
    ) {
      System.out.println("NOT EQUAL");
      return;
    }

    System.out.println("Post order:");
    System.out.println("Method 1:");
    for (TreeNode node : post_order_1) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    System.out.println("Method 2:");
    for (TreeNode node : post_order_2) {
      System.out.printf("%5d", node.data);
    }
    System.out.println();

    if (!check_list_for_equality(post_order_1, post_order_2)) {
      System.out.println("NOT EQUAL");
    }
  }

  private static boolean check_list_for_equality(List<TreeNode> list_1, List<TreeNode> list_2) {
    int i_1 = 0;
    int i_2 = 0;
    while (i_1 < list_1.size() && i_2 < list_2.size()) {
      int comp = list_1.get(i_1).data.compareTo(list_2.get(i_2).data);
      if (comp != 0) {
        return false;
      }
      i_1++;
      i_2++;
    }
    return i_1 == list_1.size() && i_2 == list_2.size();
  }

  private static boolean is_pow_of_2(int n) {
    return (n & (n-1)) == 0;
  }

  private static String extract_branching_instruction(String code) {
    int index_of_opening_parenthese = code.indexOf("(");
    int index_of_closing_parenthese = code.indexOf(")");
    return code.substring(index_of_opening_parenthese + 1, index_of_closing_parenthese).trim();
  }

  private static String extract_data(String code) {
    int index_of_opening_parenthese = code.indexOf("(");
    return code.substring(0, index_of_opening_parenthese).trim();
  }
}