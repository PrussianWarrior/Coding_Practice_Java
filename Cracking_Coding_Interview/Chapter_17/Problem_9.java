import java.util.*;

public class Problem_9 {
  public static void main(String[] args) {
    int kth = Integer.parseInt(args[0]);

    int kth_multiple_of_3_5_7_method_1 = find_kth_multiple_of_3_5_7_method_1(kth);

    System.out.println("The " + kth + " multiple whose factors rae 3,5,7 is: ");
    System.out.println("Method 1: " + kth_multiple_of_3_5_7_method_1);
  }

  private static int find_kth_multiple_of_3_5_7_method_1(int kth) {
    if (kth < 0) {
      return 0;
    }
    int val = 1;
    LinkedList<Integer> multiple_of_3_queue = new LinkedList<>();
    LinkedList<Integer> multiple_of_5_queue = new LinkedList<>();
    LinkedList<Integer> multiple_of_7_queue = new LinkedList<>();
    multiple_of_3_queue.add(1);

    for (int i = 0; i <= kth; i++) {
      int first_of_multiple_of_3_queue = multiple_of_3_queue.isEmpty() ? Integer.MAX_VALUE : multiple_of_3_queue.getFirst();
      int first_of_multiple_of_5_queue = multiple_of_5_queue.isEmpty() ? Integer.MAX_VALUE : multiple_of_5_queue.getFirst();
      int first_of_multiple_of_7_queue = multiple_of_7_queue.isEmpty() ? Integer.MAX_VALUE : multiple_of_7_queue.getFirst();

      val = Math.min(first_of_multiple_of_3_queue, Math.min(first_of_multiple_of_5_queue, first_of_multiple_of_7_queue));
      if (val == first_of_multiple_of_3_queue) {
        multiple_of_3_queue.removeFirst();
        multiple_of_3_queue.add(3 * val);
        multiple_of_5_queue.add(5 * val);
      } else if (val == first_of_multiple_of_5_queue) {
        multiple_of_5_queue.removeFirst();
        multiple_of_5_queue.add(5 * val);
      } else if (val == first_of_multiple_of_7_queue) {
        multiple_of_7_queue.removeFirst();
      }
      multiple_of_7_queue.add(7 * val);
    }

    return val;
  }
}