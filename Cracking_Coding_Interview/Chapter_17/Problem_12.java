import java.util.*;

public class Problem_12 {
  private static class BiNode {
    int data;
    BiNode node_1;
    BiNode node_2;
    
    public BiNode(int data) {
      this.data = data;
    }
  }

  public static void main(String[] args) {
    int[] arr_1 = {1,2,3,4,5,6,7};//8,9,10,11,12,13,14,15};

    BiNode root = construct_binary_tree(arr_1);
    traverse_tree(root);

    System.out.println("Doubly linked list converted from binary tree is:");
    BiNode head_dll = convert_binary_tree_to_dll_1(root);
    print_list(head_dll);

  }

  private static BiNode convert_binary_tree_to_dll_1(BiNode root) {
    BiNode node = convert_to_circular(root);
    node.node_1.node_2 = null;
    node.node_1 = null;
    return node;
  }

  private static BiNode convert_to_circular(BiNode node) {
    if (node == null) {
      return null;
    }

    BiNode part_1 = convert_to_circular(node.node_1);
    BiNode part_2 = convert_to_circular(node.node_2);

    if (part_1 == null && part_2 == null) {
      node.node_1 = node;
      node.node_2 = node;
      return node;
    }

    BiNode part_2_tail = part_2.node_1;

    if (part_1 != null) {
      concat(part_1.node_1, node);
    } else {
      concat(part_2.node_1, node);
    }

    if (part_2 != null) {
      concat(node, part_2);
    } else {
      concat(node, part_1);
    }

    if (part_1 != null && part_2 != null) {
      concat(part_2_tail, part_1);
    }
    
    return part_1 != null ? part_1 : node;
  }

  private static void concat(BiNode node_1, BiNode node_2) {
    node_1.node_2 = node_2;
    node_2.node_1 = node_1;
  }

  private static BiNode construct_binary_tree(int[] arr) {
    return construct_binary_tree(arr, 0, arr.length - 1);
  }

  private static BiNode construct_binary_tree(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }
    int mid = start + (end - start)/2;
    BiNode root = new BiNode(arr[mid]);
    root.node_1 = construct_binary_tree(arr, start, mid - 1);
    root.node_2 = construct_binary_tree(arr, mid + 1, end);
    return root;
  }

  private static void print_list(BiNode head) {
    for (BiNode iter = head; iter != null; iter = iter.node_2) {
      System.out.printf("%5d", iter.data);
    }
    System.out.println();
  }

  private static void traverse_tree(BiNode root) {
    System.out.println("Pre order traversal:");
    for (int n : pre_order_traversal(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("In order traversal:");
    for (int n : in_order_traversal(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();

    System.out.println("Post order traversal:");
    for (int n : post_order_traversal(root)) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }

  private static List<Integer> pre_order_traversal(BiNode root) {
    LinkedList<BiNode> stack = new LinkedList<>();
    List<Integer> pre_order_list = new ArrayList<>();
    if (root != null) {
      stack.addFirst(root);
    }

    while (!stack.isEmpty()) {
      BiNode top = stack.removeFirst();
      pre_order_list.add(top.data);
      if (top.node_2 != null) {
        stack.addFirst(top.node_2);
      }
      if (top.node_1 != null) {
        stack.addFirst(top.node_1);
      }
    }
    return pre_order_list;
  }

  private static List<Integer> in_order_traversal(BiNode root) {
    LinkedList<BiNode> stack = new LinkedList<>();
    List<Integer> in_order_list = new ArrayList<>();
    BiNode curr = root;

    while (curr != null || !stack.isEmpty()) {
      if (curr != null) {
        stack.addFirst(curr);
        curr = curr.node_1;
      } else {
        BiNode top = stack.removeFirst();
        in_order_list.add(top.data);
        curr = top.node_2;
      }
    }
    return in_order_list;
  }

  private static List<Integer> post_order_traversal(BiNode root) {
    LinkedList<BiNode> stack = new LinkedList<>();
    List<Integer> post_order_list = new ArrayList<>();
    if (root != null) {
      stack.addFirst(root);
    }

    BiNode prev = null;
    while (!stack.isEmpty()) {
      BiNode top = stack.getFirst();
      if (prev == null || prev.node_1 == top || prev.node_2 == top) {
        if (top.node_1 != null) {
          stack.addFirst(top.node_1);
        } else if (top.node_2 != null) {
          stack.addFirst(top.node_2);
        } else {
          stack.removeFirst();
          post_order_list.add(top.data);
        }
      } else if (top.node_1 == prev) {
        if (top.node_2 != null) {
          stack.addFirst(top.node_2);
        } else {
          stack.removeFirst();
          post_order_list.add(top.data);
        }
      } else {
        stack.removeFirst();
        post_order_list.add(top.data);
      }
      prev = top;
    }
    return post_order_list;
  }
}