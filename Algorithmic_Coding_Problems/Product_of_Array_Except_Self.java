import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an array of n integers where n > 1, nums, return an array output such 
  that output[i] is equal to the product of all the elements of nums except nums[i].
 
  Solve it without division and in O(n).
  For example, given [1,2,3,4], return [24,12,8,6].
   
  Follow up:
  Could you solve it with constant space complexity? (Note: The output array does not 
  count as extra space for the purpose of space complexity analysis.)
*/

public class Product_of_Array_Except_Self {
  public static void main(String[] args) {
    int counter = 1;
    for (int n = 2; n <= 8; n++) {
      System.out.printf("CASE = %5d\n", counter++);
      int[] arr = generate_input_arr(n);
      System.out.println("BEFORE MULTIPLICATION");
      print_arr(arr);

      int[] prod_arr_1 = prod_arr_except_self_1(arr);
      int[] prod_arr_2 = prod_arr_except_self_2(arr);
      int[] prod_arr_3 = prod_arr_except_self_3(arr);

      System.out.println("Product of Array Except Self");
      System.out.println("METHOD 1");
      print_arr(prod_arr_1);

      System.out.println("METHOD 2");
      print_arr(prod_arr_2);

      System.out.println("METHOD 3");
      print_arr(prod_arr_3);

      if (!are_arr_equal(prod_arr_1, prod_arr_2)) {
        System.out.println("prod_arr_except_self_1 != prod_arr_except_self_2");
        break;
      }

      if (!are_arr_equal(prod_arr_1, prod_arr_3)) {
        System.out.println("prod_arr_except_self_1 != prod_arr_except_self_3");
        break;
      }

      System.out.println("------------------------------------------------------------------------------------");

    }
  }

  private static int[] prod_arr_except_self_1(int[] arr) {
    int[] prod_arr = new int[arr.length];
    Arrays.fill(prod_arr, 1);

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        if (j != i) {
          prod_arr[i] *= arr[j];
        }
      }
    }
    return prod_arr;
  }

  private static int[] prod_arr_except_self_2(int[] arr) {
    int[] left_to_right = new int[arr.length];
    int[] right_to_left = new int[arr.length];

    left_to_right[0] = 1;
    right_to_left[arr.length - 1] = 1;

    for (int i = 1; i < arr.length; i++) {
      left_to_right[i] = left_to_right[i - 1] * arr[i - 1];
    }

    for (int i = arr.length - 2; i >= 0; i--) {
      right_to_left[i] = right_to_left[i + 1] * arr[i + 1];
    }

    int[] prod_arr = new int[arr.length];

    for (int i = 0; i < arr.length; i++) {
      prod_arr[i] = left_to_right[i] * right_to_left[i];
    }

    return prod_arr;
  }

  private static int[] prod_arr_except_self_3(int[] arr) {
    int[] prod_arr = new int[arr.length];

    prod_arr[0] = 1;
    for (int i = 1; i < arr.length; i++) {
      prod_arr[i] = prod_arr[i - 1] * arr[i - 1];
    }

    int temp = arr[arr.length - 1];
    for (int i = arr.length - 2; i >= 0; i--) {
      prod_arr[i] *= temp;
      temp *= arr[i];
    }
    return prod_arr;
  }

  private static void print_arr(int[] arr) {
    for (int n : arr) {
      System.out.print(n + " ");
    }
    System.out.println();
  }

  private static boolean are_arr_equal(int[] arr_1, int[] arr_2) {
    if (arr_1.length != arr_2.length) {
      return false;
    }
    for (int i = 0; i < arr_1.length; i++) {
      if (arr_1[i] != arr_2[i]) {
        return false;
      }
    }

    return true;
  }

  private static int[] generate_input_arr(int N) {
    int[] arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = i + 1;
    }
    return arr;
  }
}