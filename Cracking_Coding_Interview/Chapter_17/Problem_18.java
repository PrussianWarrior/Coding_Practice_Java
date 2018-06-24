import java.util.*;

public class Problem_18 {
  private static class Heap_Node {
    int index;
    int list_id;

    public Heap_Node(int index, int list_id) {
      this.index = index;
      this.list_id = list_id;
    }
  }

  private static class Heap_Node_Comparator implements Comparator<Heap_Node> {
    @Override
    public int compare(Heap_Node node_1, Heap_Node node_2) {
      return node_1.index - node_2.index;
    }
  }

  public static void main(String[] args) {
    int[] small_arr_1 = {1, 5, 9};
    int[] big_arr_1 = {7, 5, 9, 9, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};   

    System.out.println("small_arr_1");
    print_arr(small_arr_1);
    System.out.println("big_arr_1");
    print_arr(big_arr_1);

    int[] shortest_subarr_1 = find_shortest_subarr_1(small_arr_1, big_arr_1);
    int[] shortest_subarr_2 = find_shortest_subarr_2(small_arr_1, big_arr_1);
    int[] shortest_subarr_3 = find_shortest_subarr_3(small_arr_1, big_arr_1);
    int[] shortest_subarr_4 = find_shortest_subarr_4(small_arr_1, big_arr_1);

    System.out.println("Shortest sub array containing all the number in small_arr_1:");
    System.out.println("Method 1: [" + shortest_subarr_1[0] + ", " + shortest_subarr_1[1] + "]");
    System.out.println("Method 2: [" + shortest_subarr_2[0] + ", " + shortest_subarr_2[1] + "]");
    System.out.println("Method 3: [" + shortest_subarr_3[0] + ", " + shortest_subarr_3[1] + "]");
    System.out.println("Method 4: [" + shortest_subarr_4[0] + ", " + shortest_subarr_4[1] + "]");

    if ((shortest_subarr_1[0] != shortest_subarr_2[0]) || (shortest_subarr_1[1] != shortest_subarr_2[1])) {
      System.out.println("FAILED. find_shortest_subarr_1 does not yield the same result as find_shortest_subarr_2");
    }

    if ((shortest_subarr_1[0] != shortest_subarr_3[0]) || (shortest_subarr_1[1] != shortest_subarr_3[1])) {
      System.out.println("FAILED. find_shortest_subarr_1 does not yield the same result as find_shortest_subarr_3");
    }

    if ((shortest_subarr_1[0] != shortest_subarr_4[0]) || (shortest_subarr_1[1] != shortest_subarr_4[1])) {
      System.out.println("FAILED. find_shortest_subarr_1 does not yield the same result as find_shortest_subarr_4");
    }
  }

  private static int[] find_shortest_subarr_1(int[] small_arr, int[] big_arr) {
    int best_start = -1;
    int best_end = -1;
    for (int i = 0; i < big_arr.length; i++) {
      int end_start_at_i = find_end_of_subarr(small_arr, big_arr, i);
      if (end_start_at_i == -1) {
        break;
      }
      if (best_start == -1 || end_start_at_i - i < best_end - best_start) {
        best_end = end_start_at_i;
        best_start = i;
      }
    }
    return new int[]{best_start, best_end};
  }

  private static int find_end_of_subarr(int[] small_arr, int[] big_arr, int start_index) {
    int end = -1;
    for (int n : small_arr) {
      int first_index_of_n = -1;
      for (int i = start_index; i < big_arr.length; i++) {
        if (big_arr[i] == n) {
          first_index_of_n = i;
          break;
        }
      }
      if (first_index_of_n == -1) {
        return -1;
      }
      end = Math.max(end, first_index_of_n);
    }
    return end;
  }

  private static int[] find_shortest_subarr_2(int[] small_arr, int[] big_arr) {
    int[][] end_positions = find_end_positions(small_arr, big_arr);
    int[] closure = find_closure(end_positions);

    int best_start = -1;
    int best_end = -1;
    for (int i = 0; i < closure.length; i++) {
      if (closure[i] == -1) {
        break;
      }

      if (best_start == -1 || closure[i] - i < best_end - best_start) {
        best_start = i;
        best_end = closure[i];
      }
    }
    return new int[]{best_start, best_end};
  }

  private static int[][] find_end_positions(int[] small_arr, int[] big_arr) {
    int[][] end_positions = new int[small_arr.length][big_arr.length];
    for (int small_arr_index = 0; small_arr_index < small_arr.length; small_arr_index++) {
      end_positions[small_arr_index] = get_end_positions(big_arr, small_arr[small_arr_index]);
    }
    return end_positions;
  }

  private static int[] get_end_positions(int[] big_arr, int target) {
    int index = -1;
    int[] end_positions = new int[big_arr.length];
    for (int i = big_arr.length - 1; i >= 0; i--) {
      if (big_arr[i] == target) {
        index = i;
      }
      end_positions[i] = index;
    }
    return end_positions;
  }

  private static int[] find_closure(int[][] end_positions) {
    int length = end_positions[0].length;
    int[] closure = new int[length];
    for (int i = length - 1; i >= 0; i--) {
      for (int j = 0; j < end_positions.length; j++) {
        if (end_positions[j][i] == -1) {
          closure[i] = -1;
          break;
        }
        closure[i] = Math.max(closure[i], end_positions[j][i]);
      }
    }
    return closure;
  }

  private static int[] find_shortest_subarr_3(int[] small_arr, int[] big_arr) {
    List<LinkedList<Integer>> element_indices = find_element_indices(small_arr, big_arr);
    if (element_indices == null) {
      return new int[]{-1, -1};
    }
    return find_shortest_subarr(element_indices);
  }

  private static int[] find_shortest_subarr(List<LinkedList<Integer>> element_indices) {
    PriorityQueue<Heap_Node> min_heap = new PriorityQueue<Heap_Node>(new Heap_Node_Comparator());
    int max = Integer.MIN_VALUE;

    for (int i = 0; i < element_indices.size(); i++) {
      int head = element_indices.get(i).removeFirst();
      min_heap.add(new Heap_Node(head, i));
      max = Math.max(max, head);
    }

    if (min_heap.isEmpty()) {
      return new int[]{-1,-1};
    }
    int min = min_heap.peek().index;
    int best_start = min;
    int best_end = max;

    while (!min_heap.isEmpty()) {
      Heap_Node node = min_heap.poll();
      LinkedList<Integer> list = element_indices.get(node.list_id);

      min = node.index;
      if (max - min < best_end - best_start) {
        best_start = min;
        best_end = max;
      }

      if (list.isEmpty()) {
        break;
      }

      node.index = list.removeFirst();
      min_heap.add(node);
      max = Math.max(max, node.index);
    }

    return new int[]{best_start, best_end};
  }

  private static int[] find_shortest_subarr_4(int[] small_arr, int[] big_arr) {
    Map<Integer, LinkedList<Integer>> element_indices = new HashMap<>();
    for (int n : small_arr) {
      element_indices.put(n, new LinkedList<>());
    }

    for (int i = 0; i < big_arr.length; i++) {
      if (element_indices.containsKey(big_arr[i])) {
        element_indices.get(big_arr[i]).add(i);
      }
    }

    for (int n : small_arr) {
      if (element_indices.get(n).isEmpty()) {
        return new int[]{-1, -1};
      }
    }

    PriorityQueue<Heap_Node> min_heap = new PriorityQueue<>(new Heap_Node_Comparator());
    int max = Integer.MIN_VALUE;

    for (int n : small_arr) {
      int head = element_indices.get(n).removeFirst();
      max = Math.max(max, head);
      min_heap.add(new Heap_Node(head, n));
    }

    int min = min_heap.peek().index;
    int best_start = min;
    int best_end = max;

    while (!min_heap.isEmpty()) {
      Heap_Node node = min_heap.poll();
      LinkedList<Integer> list = element_indices.get(node.list_id);

      min = node.index;
      if (max - min < best_end - best_start) {
        best_start = min;
        best_end = max;
      }

      if (list.isEmpty()) {
        break;
      }

      node.index = list.removeFirst();
      min_heap.add(node);
      max = Math.max(max, node.index);
    }

    return new int[]{best_start, best_end};
  }

  private static List<LinkedList<Integer>> find_element_indices(int[] small_arr, int[] big_arr) {
    List<LinkedList<Integer>> element_indices = new ArrayList<>();
    for (int n : small_arr) {
      LinkedList<Integer> indices = new LinkedList<>();
      for (int i = 0; i < big_arr.length; i++) {
        if (n == big_arr[i]) {
          indices.add(i);
        }
      }
      if (indices.isEmpty()) {
        return null;
      }
      element_indices.add(indices);
    }
    return element_indices;
  }

  private static void print_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%5d. %5d\n", i, arr[i]);
    }
    System.out.println();
  }
}