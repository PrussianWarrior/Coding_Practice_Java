import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a positive integer n, break it into the sum of at least two positive integers and maximize the 
  product of those integers. Return the maximum product you can get.

  For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).

  Note: You may assume that n is not less than 2 and not larger than 58.
*/

public class Integer_Break {
  private static class List_Sum {
    List<Integer> list;
    int sum;

    public List_Sum(List<Integer> list, int sum) {
      this.list = list;
      this.sum = sum;
    }
  }

  public static void main(String[] args) {
    for (int N = 1; N <= 20; N++) {
      System.out.println("N = " + N);

      int max_product_1 = break_integer_max_product_1(N);
      int max_product_2 = break_integer_max_product_2(N);
      int max_product_3 = break_integer_max_product_3(N);
      int max_product_4 = break_integer_max_product_4(N);

      System.out.println("Max product:");
      System.out.println("SOLUTION 1: " + max_product_1);
      System.out.println("SOLUTION 2: " + max_product_2);
      System.out.println("SOLUTION 3: " + max_product_3);
      System.out.println("SOLUTION 4: " + max_product_4);

      if (max_product_1 != max_product_2 ||
          max_product_1 != max_product_3 ||
          max_product_1 != max_product_4) {
        System.out.println("FAILED");
        break;
      }
      System.out.println("-----------------------------------------------------------------------------------------------");
    }
  }

  private static int break_integer_max_product_1(int N) {
    if (N <= 3) {
      return N - 1;
    }

    int max_product = 0;
    for (int i = 1; i < N; i++) {
      max_product = Math.max(max_product, Math.max(i * (N - i), i * break_integer_max_product_1(N - i)));
    }
    return max_product;
  }

  private static int break_integer_max_product_2(int N) {
    int[] cache = new int[N + 1];
    return break_integer_max_product_2(N, cache);
  }

  private static int break_integer_max_product_2(int N, int[] cache) {
    if (N <= 3) {
      return N - 1;
    }
    if (cache[N] > 0) {
      // System.out.println("Returning cached result for N = " + N);
      return cache[N];
    }

    int max_product = 0;
    for (int i = 1; i < N; i++) {
      max_product = Math.max(max_product, Math.max(i * (N - i), i * break_integer_max_product_2(N - i, cache)));
    }
    cache[N] = max_product;
    return max_product;
  }

  private static int break_integer_max_product_3(int N) {
    if (N <= 3) {
      return N - 1;
    }
    int[] soln_subproblem = new int[N + 1];
    soln_subproblem[2] = 1;
    soln_subproblem[3] = 2;
    for (int n = 4; n <= N; n++) {
      for (int i = 1; i < n; i++) {
        soln_subproblem[n] = Math.max(soln_subproblem[n], Math.max(i * (n - i), i * soln_subproblem[n - i]));
      }
    }
    return soln_subproblem[N];
  }

  private static int break_integer_max_product_4(int N) {
    if (N <= 3) {
      return N - 1;
    }
    int max_product = 1;
    while (N > 4) {
      max_product *= 3;
      N -= 3;
    }
    return max_product * N;
  }
}