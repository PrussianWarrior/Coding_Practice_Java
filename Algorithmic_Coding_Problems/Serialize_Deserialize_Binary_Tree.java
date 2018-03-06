import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Serialization is the process of converting a data structure or object into a sequence of 
  bits so that it can be stored in a file or memory buffer, or transmitted across a network 
  connection link to be reconstructed later in the same or another computer environment.
 
  Design an algorithm to serialize and deserialize a binary tree. There is no restriction on 
  how your serialization/deserialization algorithm should work. You just need to ensure that 
  a binary tree can be serialized to a string and this string can be deserialized to the 
  original tree structure.
   
  For example, you may serialize the following tree
       1
     /   \
    2     3
        /   \
       4     5


  as "[1,2,3,null,null,4,5]
*/

public class Serialize_Deserialize_Binary_Tree {
  private static final String DELIMITER = "_";
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

          List<String> nodes = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            line = br.readLine();
            while (!line.startsWith("**")) {
              nodes.add(line);
              line = br.readLine();
            }
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          Node root = build_tree(nodes);
          traverse_tree(root);

          long serialize_start = System.currentTimeMillis();
          String serialized_tree_1 = serialize_tree_1(root);
          long serialize_stop = System.currentTimeMillis();
          long serialize_execution_time_1 = serialize_stop - serialize_start;

          long deserialize_start = System.currentTimeMillis();
          Node deserialized_tree_1 = deserialize_tree_1(serialized_tree_1);
          long deserialize_stop = System.currentTimeMillis();
          long deserialize_execution_time_1 = deserialize_stop - deserialize_start;
          
          serialize_start = System.currentTimeMillis();
          String serialized_tree_2 = serialize_tree_2(root);
          serialize_stop = System.currentTimeMillis();
          long serialize_execution_time_2 = serialize_stop - serialize_start;

          deserialize_start = System.currentTimeMillis();
          Node deserialized_tree_2 = deserialize_tree_2(serialized_tree_2);
          deserialize_stop = System.currentTimeMillis();
          long deserialize_execution_time_2 = deserialize_stop - deserialize_start;

          // serialize_start = System.currentTimeMillis();
          // String serialized_tree_3 = serialize_tree_3(root);
          // serialize_stop = System.currentTimeMillis();
          // long serialize_execution_time_3 = serialize_stop - serialize_start;

          // deserialize_start = System.currentTimeMillis();
          // Node deserialized_tree_3 = deserialize_tree_3(serialized_tree_3);
          // deserialize_stop = System.currentTimeMillis();
          // long deserialize_execution_time_3 = deserialize_stop - deserialize_start;

          System.out.println("Serialization:");
          System.out.println("Method 1: " + serialized_tree_1 + ", execution time = " + 
            serialize_execution_time_1);
          System.out.println("Method 2: " + serialized_tree_2 + ", execution time = " + 
            serialize_execution_time_2);
          // System.out.println("Method 3: " + serialized_tree_3 + ", execution time = " + 
          //   serialize_execution_time_3);
  
          System.out.println("Deserialization:");
          System.out.println("Method 1:");
          traverse_tree(deserialized_tree_1);
          System.out.println();
          System.out.println("Method 2:");
          traverse_tree(deserialized_tree_2);
          System.out.println();

          if (!are_trees_identical_1(deserialized_tree_1, deserialized_tree_2)) {
            System.out.println("Deserialization failed");
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

  /*
    Serialize by level order traversal.
  */
  private static String serialize_tree_1(Node root) {
    if (root == null) {
      return "";
    }

    LinkedList<Node> queue = new LinkedList<>();
    queue.addLast(root);
    StringBuilder serialized_tree = new StringBuilder();

    while (!queue.isEmpty()) {
      Node first = queue.removeFirst();
      if (first != null) {
        serialized_tree.append(first.data).append(DELIMITER);
        queue.addLast(first.left);
        queue.addLast(first.right);
      } else {
        serialized_tree.append("#").append(DELIMITER);
      }
    }
    return serialized_tree.toString().substring(0, serialized_tree.length() - 1);
  }

  /*
    Deserialize by level order traversal
  */
  private static Node deserialize_tree_1(String str) {
    if (str.isEmpty()) {
      return null;
    }

    String[] array_of_data = str.split(DELIMITER);
    int index = 0;
    Node root = new Node(Integer.parseInt(array_of_data[index++]));
    
    LinkedList<Node> queue = new LinkedList<>();
    queue.addLast(root);

    while (index < array_of_data.length) {
      Node first = queue.removeFirst();

      if (!array_of_data[index].equals("#")) {
        first.left = new Node(Integer.parseInt(array_of_data[index]));
        queue.addLast(first.left);
      }
      index++;

      if (!array_of_data[index].equals("#")) {
        first.right = new Node(Integer.parseInt(array_of_data[index]));
        queue.addLast(first.right);
      }
      index++;
    }

    return root;
  }

  private static String serialize_tree_2(Node root) {
    if (root == null) {
      System.out.println("Return null");
      return "";
    }

    LinkedList<Node> stack = new LinkedList<>();
    stack.addFirst(root);

    StringBuilder serialized_tree = new StringBuilder();
    while (!stack.isEmpty()) {
      Node first = stack.removeFirst();
      if (first != null) {
        serialized_tree.append(first.data).append(DELIMITER);
        stack.addFirst(first.right);
        stack.addFirst(first.left);
      } else {
        serialized_tree.append("#").append(DELIMITER);
      }
    }

    return serialized_tree.toString().substring(0, serialized_tree.length() - 1);
  }

  private static Node deserialize_tree_2(String str) {
    if (str.isEmpty()) {
      return null;
    }

    String[] array_of_data = str.split(DELIMITER);
    int[] index = {0};
    return deserialize_tree_2(array_of_data, index);
  }

  private static Node deserialize_tree_2(String[] array_of_data, int[] index) {
    if (array_of_data[index[0]].equals("#")) {
      return null;
    }
    Node root = new Node(Integer.parseInt(array_of_data[index[0]]));
    index[0]++;
    root.left = deserialize_tree_2(array_of_data, index);
    index[0]++;
    root.right = deserialize_tree_2(array_of_data, index);
    return root;
  }

  private static Node build_tree(List<String> nodes) {
    Node root = null;
    for (String node : nodes) {
      root = build_tree(root, node);
    }
    return root;
  }

  private static String serialize_tree_3(Node root) {
    if (root == null) {
      return "";
    }

    LinkedList<Node> stack = new LinkedList<>();
    StringBuilder str = new StringBuilder();
    Node curr = root;
    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.addFirst(curr);
        curr = curr.left;
      } else {
        str.append("#").append(DELIMITER);
        Node top = stack.removeFirst();
        str.append(top.data).append(DELIMITER);
        if (top.right != null) {
          curr = top.right;
        }
      }
    }

    return str.toString().substring(0, str.length() - 1);
  }

  private static Node deserialize_tree_3(String str) {
    if (str.isEmpty()) {
      return null;
    }

    String[] array_of_data = str.split(DELIMITER);
    int[] index = {0};
    return deserialize_tree_3(array_of_data, index);
  }

  private static Node deserialize_tree_3(String[] array_of_data, int[] index) {
    if (array_of_data[index[0]].equals("#")) {
      return null;
    }

    Node left = deserialize_tree_3(array_of_data, index);
    index[0]++;
    Node root = new Node(Integer.parseInt(array_of_data[index[0]]));
    index[0]++;
    Node right = deserialize_tree_3(array_of_data, index);
    root.left = left;
    root.right = right;
    return root;
  }

  private static Node build_tree(Node root, String line) {
    line = remove_extra_spaces_1(line);
    // System.out.println(line);
    int index_of_colon = line.indexOf(":");
    String instruction_code = line.substring(index_of_colon + 1).trim();
    int data = Integer.parseInt(line.substring(0, index_of_colon).trim());

    if (root == null) {
      root = new Node(data);
    } else {
      Node parent = null;
      Node curr = root;
      boolean right = true;
      for (int i = 0; i < instruction_code.length(); i++) {
        char code = instruction_code.charAt(i);
        parent = curr;
        if (code == '0') {
          right = false;
          curr = curr.left;
        } else {
          right = true;
          curr = curr.right;
        }
      }

      if (right) {
        parent.right = new Node(data);
      } else {
        parent.left = new Node(data);
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

  private static boolean are_trees_identical_1(Node root_1, Node root_2) {
    if (root_1 == null && root_2 == null) {
      return true;
    }

    if (root_1 == null && root_2 != null) {
      return false;
    }

    if (root_1 != null && root_2 == null) {
      return false;
    }

    if (root_1.data != root_2.data) {
      return false;
    }

    return are_trees_identical_1(root_1.left, root_2.left) &&
           are_trees_identical_1(root_1.right, root_2.right);
  }
}