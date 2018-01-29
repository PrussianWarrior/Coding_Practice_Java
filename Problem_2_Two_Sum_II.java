import java.lang.*;
import java.util.*;

public class Problem_2_Two_Sum_II {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    boolean keep_running = true;
    do {
      // int L = Integer.parseInt(args[0]);
      // int min = Integer.parseInt(args[1]);
      // int max = Integer.parseInt(args[2]);
      int L = in.nextInt();
      int min = in.nextInt();
      int max = in.nextInt();
      int[] rand_arr = rand_arr_generator(L, min, max);
      Arrays.sort(rand_arr);
      print(rand_arr);
      System.out.println("Enter a target sum");
      int target = in.nextInt();
      int[] soln_1 = solution_1(rand_arr, target);
      int[] soln_2 = solution_2(rand_arr, target);
      int[] soln_3 = solution_3(rand_arr, target);
      if (soln_1 != null) {
        System.out.println(soln_1[0] + " + " + soln_1[1]);
        System.out.println(rand_arr[soln_2[0]] + " + " + rand_arr[soln_2[1]]);
        System.out.println(rand_arr[soln_3[0]] + " + " + rand_arr[soln_3[1]]);
      } else {
        System.out.println("There do not exist 2 numbers in rand_arr that amount to " + target);
      }

      System.out.println("Type Y/y to continue. Type anything else to quit");
      char choice = in.next().charAt(0);
      keep_running = choice == 'Y' || choice == 'y';
    } while (keep_running);

  }

  public static int[] solution_1(int[] arr, int target) {
    HashSet<Integer> already_encountered_num = new HashSet<>();
    for (int n : arr) {
      if (already_encountered_num.contains(target - n)) {
        return new int[]{n, target-n};
      }
      already_encountered_num.add(n);
    }
    return null;
  }

  public static int[] solution_2(int[] arr, int target) {
    HashMap<Integer, Integer> already_encountered_num = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      if (already_encountered_num.containsKey(target - arr[i])) {
        return new int[]{i, already_encountered_num.get(target-arr[i])};
      }
      already_encountered_num.put(arr[i], i);
    }
    return null;
  }

  public static int[] solution_3(int[] arr, int target) {
    assert isSorted(arr);
    int left_ptr = 0;
    int right_ptr = arr.length - 1;
    while (left_ptr < right_ptr) {
      int sum = arr[left_ptr] + arr[right_ptr];
      if (sum == target) {
        return new int[]{left_ptr, right_ptr};
      } else if (sum < target) {
        left_ptr++;
      } else {
        right_ptr--;
      }
    }
    return null;
  }

  private static int[] rand_arr_generator(int L, int min, int max) {
    // Random rand_generator = new Random();
    int[] rand_arr = new int[L];
    for (int i = 0; i < L; i++) {
      rand_arr[i] = (int)(Math.random() * (max - min + 1)) + min;
    }
    return rand_arr;
  }

  private static void print(int[] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.printf("%5d", A[i]);
      if ((i + 1) % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
  }

  private static boolean isSorted(int[] A) {
    assert A != null;
    for (int i = 1; i < A.length; i++) {
      if (A[i] < A[i-1]) {
        return false;
      }
    }
    return true;
  }
}