import java.util.*;

public class Problem_1 {
  public static void main(String[] args) {
    for (int n = 1; n <= 10; n++) {
      System.out.println("Number of ways of climbing" + n + " stairs using 1,2,3 steps:");
      int num_of_ways_to_climb_N_stairs_1 = num_of_ways_to_climb_N_stairs_1(n);
      int num_of_ways_to_climb_N_stairs_2 = num_of_ways_to_climb_N_stairs_2(n);
      int num_of_ways_to_climb_N_stairs_3 = num_of_ways_to_climb_N_stairs_3(n);

      System.out.println("Method 1 = " + num_of_ways_to_climb_N_stairs_1);
      System.out.println("Method 2 = " + num_of_ways_to_climb_N_stairs_2);
      System.out.println("Method 3 = " + num_of_ways_to_climb_N_stairs_3);

      if (num_of_ways_to_climb_N_stairs_1 != num_of_ways_to_climb_N_stairs_2) {
        System.out.println("FAILED. Method 1 does not yield the same result as method 2.");
        break;
      }

      if (num_of_ways_to_climb_N_stairs_1 != num_of_ways_to_climb_N_stairs_3) {
        System.out.println("FAILED. Method 1 does not yield the same result as method 3.");
        break;
      }

      System.out.println("_____________________________________________________________________________");
    }
  }

  private static int num_of_ways_to_climb_N_stairs_1(int N) {
    if (N <= 0) {
      return 0;
    }
    if (N <= 2) {
      return N;
    }
    return num_of_ways_to_climb_N_stairs_1_helper(N);
  }

  private static int num_of_ways_to_climb_N_stairs_1_helper(int N) {
    if (N == 0) {
      return 1; 
    }
    if (N <= 2) {
      return N;
    }

    return num_of_ways_to_climb_N_stairs_1_helper(N - 3) + 
           num_of_ways_to_climb_N_stairs_1_helper(N - 2) +
           num_of_ways_to_climb_N_stairs_1_helper(N - 1);
  }

  private static int num_of_ways_to_climb_N_stairs_2(int N) {
    if (N <= 2) {
      return N;
    }
    int[] memoized = new int[N + 1];
    memoized[0] = 1;
    memoized[1] = 1;
    memoized[2] = 2;
    return num_of_ways_to_climb_N_stairs_2(N, memoized);
  }

  private static int num_of_ways_to_climb_N_stairs_2(int N, int[] memoized) {
    if (N < 0) {
      return 0;
    }
    if (memoized[N] > 0) {
      System.out.println("Return memoized result for N = " + N);
      return memoized[N];
    }

    int count = num_of_ways_to_climb_N_stairs_2(N - 3, memoized) + 
      num_of_ways_to_climb_N_stairs_2(N - 2, memoized) + 
      num_of_ways_to_climb_N_stairs_2(N - 1, memoized);
    memoized[N] = count;
    return count;
  }

  private static int num_of_ways_to_climb_N_stairs_3(int N) {
    if (N <= 0) {
      return 0;
    }
    if (N <= 2) {
      return N;
    }

    int[] partial_soln = new int[N + 1];
    partial_soln[0] = 1;
    partial_soln[1] = 1;
    partial_soln[2] = 2;
    for (int n = 3; n <= N; n++) {
      partial_soln[n] = partial_soln[n - 3] + partial_soln[n - 2] + partial_soln[n - 1];
    }
    return partial_soln[N];
  }
}