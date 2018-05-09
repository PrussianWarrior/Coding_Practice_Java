import java.util.*;

public class Problem_3 {
  public static void main(String[] args) {
    int[] arr_1 = {-40, -20, -1, 1, 2, 3, 5, 7, 9, 12, 13};
    System.out.println("Array 1:");
    print_arr(arr_1);

    int magic_index_method_1 = find_magic_index_1(arr_1);
    int magic_index_method_2 = find_magic_index_distinct_elements_1(arr_1);
    int magic_index_method_3 = find_magic_index_distinct_elements_2(arr_1);
    int magic_index_method_4 = find_magic_index_nondistinct_elements_1(arr_1);

    System.out.println("Magic index:");
    System.out.println("                     find_magic_index_1 method: " + magic_index_method_1);
    System.out.println("   find_magic_index_distinct_elements_1 method: " + magic_index_method_2);
    System.out.println("   find_magic_index_distinct_elements_2 method: " + magic_index_method_3);
    System.out.println("find_magic_index_nondistinct_elements_1 method: " + magic_index_method_4);

    System.out.println("__________________________________________________________________________________");

    int[] arr_2 = {-10, -5, 2, 2, 2, 3, 4, 7, 9, 12, 13};
    System.out.println("Array 2:");
    print_arr(arr_2);
    magic_index_method_1 = find_magic_index_1(arr_2);
    magic_index_method_4 = find_magic_index_nondistinct_elements_1(arr_2);

    System.out.println("Magic index:");
    System.out.println("                     find_magic_index_1 method: " + magic_index_method_1);
    System.out.println("find_magic_index_nondistinct_elements_1 method: " + magic_index_method_4);

    System.out.println("__________________________________________________________________________________");
  }

  private static int find_magic_index_1(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == i) {
        return i;
      }
    }
    return -1;
  }

  private static int find_magic_index_distinct_elements_1(int[] arr) {
    if (!is_sorted(arr)) {
      throw new IllegalArgumentException("ERROR: the input array is not sorted. The problem dictates that " + 
        "the input array must be sorted.");
    }
    return find_magic_index_distinct_elements_1(arr, 0, arr.length - 1);
  }

  private static int find_magic_index_distinct_elements_1(int[] arr, int start, int end) {
    if (start > end) {
      return -1;
    }
    int mid = start + (end - start)/2;
    if (arr[mid] == mid) {
      return mid;
    } else if (arr[mid] > mid) {
      return find_magic_index_distinct_elements_1(arr, start, mid - 1);
    } else {
      return find_magic_index_distinct_elements_1(arr, mid + 1, end);
    }
  }

  private static int find_magic_index_distinct_elements_2(int[] arr) {
    if (!is_sorted(arr)) {
      throw new IllegalArgumentException("ERROR: the input array is not sorted. The problem dictates that " + 
        "the input array must be sorted.");
    }
    int start = 0;
    int end = arr.length - 1;
    while (start <= end) {
      int mid = start + (end - start)/2;
      if (arr[mid] == mid) {
        return mid;
      } else if (arr[mid] > mid) {
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }
    return -1;
  }

  private static int find_magic_index_nondistinct_elements_1(int[] arr) {
    if (!is_sorted(arr)) {
      throw new IllegalArgumentException("ERROR: the input array is not sorted. The problem dictates that " + 
        "the input array must be sorted.");
    }

    return find_magic_index_nondistinct_elements_1(arr, 0, arr.length - 1);
  }

  private static int find_magic_index_nondistinct_elements_1(int[] arr, int start, int end) {
    if (start > end) {
      return -1;
    }

    int mid = start + (end - start)/2;
    if (arr[mid] == mid) {
      return mid;
    }

    int bound_on_left_search = Math.min(mid - 1, arr[mid]);
    int left_search_result = find_magic_index_nondistinct_elements_1(arr, start, bound_on_left_search);
    if (left_search_result >= 0) {
      return left_search_result;
    }

    int bound_on_right_search = Math.max(mid + 1, arr[mid]);
    int right_search_result = find_magic_index_nondistinct_elements_1(arr, bound_on_right_search, end);
    return right_search_result;
  }

  private static boolean is_sorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i - 1] > arr[i]) {
        return false;
      }
    }
    return true;
  }

  private static void print_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%5d: %5d\n", i, arr[i]);
    }
    System.out.println();
  }
}