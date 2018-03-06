import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
 
  For example,
   
  Given n = 3, there are a total of 5 unique BST's.
     1         3      3        2       1
      \       /      /        / \       \
       3     2      1        1   3       2
      /     /        \                    \
     2     1          2                    3
*/

public class Unique_BST {
  public static void main(String[] args) {
    for (int N = 1; N <= 10; N++) {
      System.out.println("N = " + N + ": " + count_num_unique_bst_1(N));
    }
  }

  private static int count_num_unique_bst_1(int N) {
    if (N <= 0) {
      return 0;
    }

    if (N <= 2) {
      return N;
    }

    int[] soln_sub_problem = new int[N + 1];
    soln_sub_problem[0] = 1;
    soln_sub_problem[1] = 1;
    soln_sub_problem[2] = 2;

    for (int n = 3; n <= N; n++) {
      for (int i = 0; i < n; i++) {
        soln_sub_problem[n] += soln_sub_problem[i] * soln_sub_problem[n - i - 1];
      }
    }
    return soln_sub_problem[N];
  }
}