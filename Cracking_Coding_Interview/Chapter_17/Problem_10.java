import java.util.*;

public class Problem_10 {
  public static void main(String[] args) {
    int[] arr_1 = {1,4,6,7,8,9,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,2,2,2};

    int majority_element_arr_1_method_1 = find_majority_element_1(arr_1);
    int majority_element_arr_1_method_2 = find_majority_element_2(arr_1);
    int majority_element_arr_1_method_3 = find_majority_element_3(arr_1);

    System.out.println("Majority element");
    System.out.println("Method 1: " + majority_element_arr_1_method_1);
    System.out.println("Method 2: " + majority_element_arr_1_method_2);
    System.out.println("Method 3: " + majority_element_arr_1_method_3);

    if (majority_element_arr_1_method_1 != majority_element_arr_1_method_2) {
      System.out.println("FAILED. find_majority_element_1 does not yield the same result as find_majority_element_2");
      return;
    }

    if (majority_element_arr_1_method_1 != majority_element_arr_1_method_3) {
      System.out.println("FAILED. find_majority_element_1 does not yield the same result as find_majority_element_3");
      return;
    }
    System.out.println("__________________________________________________________________________________________________");

    int[] arr_2 = {1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,4,5,6,4,5,7,8,9,7,8,9};

    int majority_element_arr_2_method_1 = find_majority_element_1(arr_2);
    int majority_element_arr_2_method_2 = find_majority_element_2(arr_2);
    int majority_element_arr_2_method_3 = find_majority_element_3(arr_2);

    System.out.println("Majority element");
    System.out.println("Method 1: " + majority_element_arr_2_method_1);
    System.out.println("Method 2: " + majority_element_arr_2_method_2);
    System.out.println("Method 3: " + majority_element_arr_2_method_3);

    if (majority_element_arr_2_method_1 != majority_element_arr_2_method_2) {
      System.out.println("FAILED. find_majority_element_1 does not yield the same result as find_majority_element_2");
      return;
    }

    if (majority_element_arr_2_method_1 != majority_element_arr_2_method_3) {
      System.out.println("FAILED. find_majority_element_1 does not yield the same result as find_majority_element_3");
      return;
    }
    System.out.println("__________________________________________________________________________________________________");
  }

  private static int find_majority_element_1(int[] arr) {
    Map<Integer, Integer> num_count = new HashMap<>();
    for (int n : arr) {
      int count = num_count.containsKey(n) ? num_count.get(n) : 0;
      num_count.put(n, count + 1);
    }

    for (int n : num_count.keySet()) {
      if (num_count.get(n) > arr.length/2) {
        return n;
      }
    }
    return -1;
  }

  private static int find_majority_element_2(int[] arr) {
    Arrays.sort(arr);
    int middle_element = arr[arr.length/2];
    int count = 0;
    for (int n : arr) {
      if (n == middle_element) {
        count++;
      }
    }
    return count > arr.length/2 ? middle_element : -1;
  }

  private static int find_majority_element_3(int[] arr) {
    int stack_size = 0;
    int current_stack_element = -1;
    for (int n : arr) {
      if (stack_size == 0) {
        stack_size = 1;
        current_stack_element = n;
      } else {
        if (n == current_stack_element) {
          stack_size++;
        } else {
          stack_size--;
        }
      }
    }

    int candidate = current_stack_element;
    int count = 0;
    for (int n : arr) {
      if (n == candidate) {
        count++;
      }
    }
    return count > arr.length/2 ? candidate : -1;
  }
}