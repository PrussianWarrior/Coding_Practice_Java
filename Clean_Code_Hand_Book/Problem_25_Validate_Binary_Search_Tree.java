import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_25_Validate_Binary_Search_Tree {
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
          System.out.printf("%5d: %-1s\n", counter++, line);
          int tree_building_method = in.nextInt();
          List<Integer> data = process_input(line);
          TreeNode root = build_bst(data, tree_building_method);
          traverse_bst(root);

          boolean soln_1 = is_valid_bst_1(root);
          boolean soln_2 = is_valid_bst_2(root);

          System.out.println("Solution 1: " + soln_1);
          System.out.println("Solution 2: " + soln_2);

          if (soln_1 != soln_2) {
            System.out.println("FAILED");
            break;
          }
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }   
  }

  private static TreeNode build_bst(List<Integer> list, int tree_building_method) {
    TreeNode root = null;
    switch (tree_building_method) {
      case 1:
        for (int n : list) {
          root = insert_1(root, n);
        }
        break;
      case 2:
        for (int n : list) {
          root = insert_2(root, n);
        }
        break;
    }
    return root;
  }

  public static boolean is_valid_bst_1(TreeNode root) {
    return is_valid_bst_1(root, null, null);
  }

  private static boolean is_valid_bst_1(TreeNode root, TreeNode left, TreeNode right) {
    if (root == null) {
      return true;
    }
    if (left != null && left.data.compareTo(root.data) > 0) {
      return false;
    }
    if (right != null && right.data.compareTo(root.data) < 0) {
      return false;
    }
    return is_valid_bst_1(root.left, left, root) && is_valid_bst_1(root.right, root, right);
  }

  public static boolean is_valid_bst_2(TreeNode root) {
    return is_valid_bst_2(root, null, null);
  }

  private static boolean is_valid_bst_2(TreeNode root, TreeNode left, TreeNode right) {
    if (root == null) {
      return true;
    }
    return (left == null || left.data.compareTo(root.data) <= 0) &&
           (right == null || right.data.compareTo(root.data) >= 0) &&
           is_valid_bst_2(root.left, left, root) &&
           is_valid_bst_2(root.right, root, right);
  }

  private static TreeNode insert_1(TreeNode root, Comparable data) {
    if (root == null) {
      return new TreeNode(data);
    }
    TreeNode ptr = root;
    for (;;) {
      int comp = ptr.data.compareTo(data);
      if (comp == 0) {
        return root;
      } else if (comp < 0) {
        if (ptr.right == null) {
          ptr.right = new TreeNode(data);
          return root;
        }
        ptr = ptr.right;
      } else {
        if (ptr.left == null) {
          ptr.left = new TreeNode(data);
          return root;
        }
        ptr = ptr.left;
      }
    }
  }

  private static TreeNode insert_2(TreeNode node, Comparable data) {
    if (node == null) {
      return new TreeNode(data);
    }
    int comp = node.data.compareTo(data);
    if (comp == 0) {
      return node;
    } else if (comp < 0) {
      node.right = insert_2(node.right, data);
    } else {
      node.left = insert_2(node.left, data);
    }
    return node;
  }
  
  private static TreeNode construct_BST_from_sorted_array(int[] arr) {
    assert is_arr_sorted(arr);
    return construct_BST_from_sorted_array(arr, 0, arr.length - 1);
  }

  private static TreeNode construct_BST_from_sorted_array(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = start + (end - start)/2;
    TreeNode root_sub_tree = new TreeNode(arr[mid]);
    root_sub_tree.left = construct_BST_from_sorted_array(arr, start, mid - 1);
    root_sub_tree.right = construct_BST_from_sorted_array(arr, mid + 1, end);
    return root_sub_tree;
  }

  private static boolean is_arr_sorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i-1] > arr[i]) {
        return false;
      }
    }
    return true;
  }

  private static List<Integer> process_input(String line) {
    String[] words = line.split(" ");
    List<Integer> list = new ArrayList<>();
    for (String num : words) {
      list.add(Integer.parseInt(num.trim()));
    }
    return list;
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
}