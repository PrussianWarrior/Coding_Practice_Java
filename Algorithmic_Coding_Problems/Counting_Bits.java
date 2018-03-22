import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  You are a professional robber planning to rob houses along a street. Each house has a certain amount 
  of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses 
  have security system connected and it will automatically contact the police if two adjacent houses 
  were broken into on the same night.

  Given a list of non-negative integers representing the amount of money of each house, determine the 
  maximum amount of money you can rob tonight without alerting the police.
*/

public class Counting_Bits {
  public static void main(String[] args) {
    for (int n = 1; n <= 30; n++) {
      System.out.println("N = " + n);
      int[] num_bit_1_arr_1 = count_bits_1(n);
      int[] num_bit_1_arr_2 = count_bits_2(n);
      int[] num_bit_1_arr_3 = count_bits_3(n);

      System.out.print("SOLUTION 1: ");
      print_arr(num_bit_1_arr_1);
      System.out.print("SOLUTION 2: ");
      print_arr(num_bit_1_arr_2);
      System.out.print("SOLUTION 3: ");
      print_arr(num_bit_1_arr_3);

      if (!are_arr_equal(num_bit_1_arr_1, num_bit_1_arr_2)) {
        System.out.println("SOLUTION 1 != SOLUTION 2");
        break;
      }

      if (!are_arr_equal(num_bit_1_arr_1, num_bit_1_arr_3)) {
        System.out.println("SOLUTION 1 != SOLUTION 3");
        break;
      }

      System.out.println("________________________________________________________________________" + 
        "________________________");
    }
  }

  private static int[] count_bits_1(int n) {
    int[] bit_1_arr = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      bit_1_arr[i] = count_num_bit_1(i);
    }
    return bit_1_arr;
  }

  private static int[] count_bits_2(int n) {
    int[] bit_1_arr = new int[n + 1];
    int pow_of_2 = 1;
    int prev_num = 1;
    for (int i = 1; i <= n; i++) {
      if (i == pow_of_2) {
        pow_of_2 <<= 1;
        prev_num = 1;
        bit_1_arr[i] = 1;
      } else {
        bit_1_arr[i] = bit_1_arr[prev_num++] + 1;
      }
    }

    return bit_1_arr;
  }

  private static int[] count_bits_3(int n) {
    int[] bit_1_arr = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      bit_1_arr[i] = bit_1_arr[i >> 1] + (i & 1);
    }
    return bit_1_arr;
  }

  private static int count_num_bit_1(int n) {
    int count = 0;
    for (; n != 0; n >>= 1) {
      if ((n & 1) != 0) {
        count++;
      }
    }
    return count;
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

  private static void print_arr(int[] arr) {
    for (int n : arr) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }
}