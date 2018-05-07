import java.util.*;

public class Problem_4 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    System.out.println("Bianry representation of N = " + Integer.toBinaryString(N));

    int larger_num_1 = find_larger_num_with_same_num_of_ones_1(N);
    int larger_num_2 = find_larger_num_with_same_num_of_ones_2(N);
    System.out.println("Next larger number:");
    System.out.println("Method 1: " + larger_num_1 + ": " + Integer.toBinaryString(larger_num_1));
    System.out.println("Method 2: " + larger_num_2 + ": " + Integer.toBinaryString(larger_num_2));

    if (larger_num_1 != larger_num_2) {
      System.out.println("find_larger_num_with_same_num_of_ones_1 does not yield the same result " + 
        "as find_larger_num_with_same_num_of_ones_2.");
      return;
    }

    int smaller_num_1 = find_smaller_num_with_same_num_of_ones_1(N);
    int smaller_num_2 = find_smaller_num_with_same_num_of_ones_2(N);
    System.out.println("Previous smaller number:");
    System.out.println("Method 1: " + smaller_num_1 + ": " + Integer.toBinaryString(smaller_num_1));
    System.out.println("Method 2: " + smaller_num_2 + ": " + Integer.toBinaryString(smaller_num_2));

    if (smaller_num_1 != smaller_num_2) {
      System.out.println("find_smaller_num_with_same_num_of_ones_1 does not yield the same result " + 
        "as find_smaller_num_with_same_num_of_ones_2.");
      return;
    }
  }

  private static int find_larger_num_with_same_num_of_ones_1(int N) {
    if (N == 0) {
      return -1;
    }
    int num_of_rightmost_0s = 0;
    int copy_N = N;
    while (copy_N != 0 && (copy_N & 1) == 0) {
      num_of_rightmost_0s++;
      copy_N >>= 1;
    }
    
    int num_of_1s = 0;
    while (copy_N != 0 && (copy_N & 1) != 0) {
      num_of_1s++;
      copy_N >>= 1;
    }

    if (num_of_1s + num_of_rightmost_0s == 32) {
      return -1;
    }

    N |= 1 << (num_of_rightmost_0s + num_of_1s);
    N &= ~(1 << (num_of_1s + num_of_rightmost_0s) - 1);
    N |= (1 << (num_of_1s - 1)) - 1;
    return N;
  }

  private static int find_larger_num_with_same_num_of_ones_2(int N) {
    if (N == 0) {
      return -1;
    }

    int num_of_rightmost_0s = 0;
    int copy_N = N;
    while (copy_N != 0 && (copy_N & 1) == 0) {
      num_of_rightmost_0s++;
      copy_N >>= 1;
    }

    int num_of_1s = 0;
    while (copy_N != 0 && (copy_N & 1) != 0) {
      num_of_1s++;
      copy_N >>= 1;
    }

    if (num_of_1s + num_of_rightmost_0s == 32) {
      return -1;
    }

    return N + (1 << num_of_rightmost_0s) + (1 << (num_of_1s - 1)) - 1;
  }

  private static int find_smaller_num_with_same_num_of_ones_1(int N) {
    if (N == 0) {
      return -1;
    }

    int num_of_rightmost_1s = 0;
    int copy_N = N;
    while (copy_N != 0 && (copy_N & 1) != 0) {
      num_of_rightmost_1s++;
      copy_N >>= 1;
    }

    int num_of_0s = 0;
    while (copy_N != 0 && (copy_N & 1) == 0) {
      num_of_0s++;
      copy_N >>= 1;
    }

    if (num_of_rightmost_1s + num_of_0s == 32) {
      return -1;
    }

    N &= ~((1 << (num_of_rightmost_1s + num_of_0s + 1)) - 1);
    int mask = (1 << (num_of_rightmost_1s + 1)) - 1;
    mask <<= num_of_0s - 1;
    return N | mask;
  }

  private static int find_smaller_num_with_same_num_of_ones_2(int N) {
    if (N == 0) {
      return -1;
    }

    int num_of_rightmost_1s = 0;
    int copy_N = N;
    while (copy_N != 0 && (copy_N & 1) != 0) {
      num_of_rightmost_1s++;
      copy_N >>= 1;
    }

    int num_of_0s = 0;
    while (copy_N != 0 && (copy_N & 1) == 0) {
      num_of_0s++;
      copy_N >>= 1;
    }

    if (num_of_rightmost_1s + num_of_0s == 32) {
      return -1;
    }
    return N - (1 << num_of_rightmost_1s) - (1 << (num_of_0s - 1)) + 1;
  }
}