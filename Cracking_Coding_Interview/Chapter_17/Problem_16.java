
public class Problem_16 {
  public static void main(String[] args) {
    int[] arr_1 = {30, 15, 60, 75, 45, 15, 15, 45};
    System.out.println("arr_1");
    print_arr(arr_1);

    int max_sum_non_contiguous_arr_1_method_1 = find_max_sum_non_contiguous_arr_1(arr_1);
    int max_sum_non_contiguous_arr_1_method_2 = find_max_sum_non_contiguous_arr_2(arr_1);
    int max_sum_non_contiguous_arr_1_method_3 = find_max_sum_non_contiguous_arr_3(arr_1);
    int max_sum_non_contiguous_arr_1_method_4 = find_max_sum_non_contiguous_arr_4(arr_1);

    System.out.println("Max sum non contiguous array:");
    System.out.println("Method 1: " + max_sum_non_contiguous_arr_1_method_1);
    System.out.println("Method 2: " + max_sum_non_contiguous_arr_1_method_2);
    System.out.println("Method 3: " + max_sum_non_contiguous_arr_1_method_3);
    System.out.println("Method 4: " + max_sum_non_contiguous_arr_1_method_4);

    if (max_sum_non_contiguous_arr_1_method_1 != max_sum_non_contiguous_arr_1_method_2) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_2");
      return;
    }

    if (max_sum_non_contiguous_arr_1_method_1 != max_sum_non_contiguous_arr_1_method_3) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_3");
      return;
    }

    if (max_sum_non_contiguous_arr_1_method_1 != max_sum_non_contiguous_arr_1_method_4) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_4");
      return;
    }

    System.out.println("_________________________________________________________________________________________");
    int N = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);
    int max = Integer.parseInt(args[2]);

    int[] arr_2 = generate_random_arr(N, min, max);
    System.out.println("arr_2");
    print_arr(arr_2);

    int max_sum_non_contiguous_arr_2_method_1 = find_max_sum_non_contiguous_arr_1(arr_2);
    int max_sum_non_contiguous_arr_2_method_2 = find_max_sum_non_contiguous_arr_2(arr_2);
    int max_sum_non_contiguous_arr_2_method_3 = find_max_sum_non_contiguous_arr_3(arr_2);
    int max_sum_non_contiguous_arr_2_method_4 = find_max_sum_non_contiguous_arr_4(arr_2);

    System.out.println("Max sum non contiguous array:");
    System.out.println("Method 1: " + max_sum_non_contiguous_arr_2_method_1);
    System.out.println("Method 2: " + max_sum_non_contiguous_arr_2_method_2);
    System.out.println("Method 3: " + max_sum_non_contiguous_arr_2_method_3);
    System.out.println("Method 4: " + max_sum_non_contiguous_arr_2_method_4);

    if (max_sum_non_contiguous_arr_2_method_1 != max_sum_non_contiguous_arr_2_method_2) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_2");
      return;
    }

    if (max_sum_non_contiguous_arr_2_method_1 != max_sum_non_contiguous_arr_2_method_3) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_3");
      return;
    }

    if (max_sum_non_contiguous_arr_2_method_1 != max_sum_non_contiguous_arr_2_method_4) {
      System.out.println("find_max_sum_non_contiguous_arr_1 does not yield the same result as " + 
        "find_max_sum_non_contiguous_arr_4");
      return;
    }
  }

  private static int find_max_sum_non_contiguous_arr_1(int[] arr) {
    return find_max_sum_non_contiguous_arr_1(arr, arr.length - 1);
  }

  private static int find_max_sum_non_contiguous_arr_1(int[] arr, int index) {
    if (index < 0) {
      return 0;
    }
    if (index == 0) {
      return arr[0];
    }
    if (index == 1) {
      return Math.max(arr[0], arr[1]);
    }
    int sum_with_index = arr[index] + find_max_sum_non_contiguous_arr_1(arr, index - 2);
    int sum_without_index = find_max_sum_non_contiguous_arr_1(arr, index - 1);
    return Math.max(sum_with_index, sum_without_index);
  }

  private static int find_max_sum_non_contiguous_arr_2(int[] arr) {
    return find_max_sum_non_contiguous_arr_2(arr, new int[arr.length + 1], arr.length - 1);
  }

  private static int find_max_sum_non_contiguous_arr_2(int[] arr, int[] max_sum_ending_at_index, int index) {
    if (index < 0) {
      return 0;
    }
    if (index == 0) {
      return arr[0];
    }
    if (index == 1) {
      return Math.max(arr[0], arr[1]);
    }
    if (max_sum_ending_at_index[index] > 0) {
      System.out.println("Returning cached result for index = " + index);
      return max_sum_ending_at_index[index];
    }
    int sum_with_index = arr[index] + find_max_sum_non_contiguous_arr_2(arr, max_sum_ending_at_index, index - 2);
    int sum_without_index = find_max_sum_non_contiguous_arr_2(arr, max_sum_ending_at_index, index - 1);
    max_sum_ending_at_index[index] = Math.max(sum_with_index, sum_without_index);
    return max_sum_ending_at_index[index];
  }

  private static int find_max_sum_non_contiguous_arr_3(int[] arr) {
    int[] max_sum_ending_at_index = new int[arr.length];
    max_sum_ending_at_index[0] = arr[0];
    max_sum_ending_at_index[1] = Math.max(arr[0], arr[1]);
    for (int i = 2; i < arr.length; i++) {
      max_sum_ending_at_index[i] = Math.max(max_sum_ending_at_index[i - 1], max_sum_ending_at_index[i - 2] + arr[i]);
    }
    return max_sum_ending_at_index[arr.length - 1];
  }

  private static int find_max_sum_non_contiguous_arr_4(int[] arr) {
    int even = 0;
    int odd = 0;
    for (int i = 0; i < arr.length; i++) {
      if (i % 2 == 0) {
        even += arr[i];
        even = Math.max(even, odd);
      } else {
        odd += arr[i];
        odd = Math.max(even, odd);
      }
    }
    return Math.max(even, odd);
  }

  private static int[] generate_random_arr(int N, int min, int max) {
    int[] rand_arr = new int[N];
    for (int i = 0; i < N; i++) {
      rand_arr[i] = (int)(Math.random() * (max - min + 1)) + min;
    }
    return rand_arr;
  }

  private static void print_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%5d: %5d\n", i, arr[i]);
    }
    System.out.println();
  }
}