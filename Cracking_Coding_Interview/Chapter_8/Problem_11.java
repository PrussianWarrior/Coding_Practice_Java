import java.util.*;

public class Problem_11 {
  public static void main(String[] args) {
    int N_cents = Integer.parseInt(args[0]);
    int[] coins = {1,5,10,25};

    int method_1_result = num_of_ways_of_representing_N_cents_1(coins, N_cents);
    int method_2_result = num_of_ways_of_representing_N_cents_2(coins, N_cents);
    System.out.println("Number of ways of representing " + N_cents + " cents:");
    System.out.println("Method 1: " + method_1_result);
    System.out.println("Method 2: " + method_2_result);
  }

  private static int num_of_ways_of_representing_N_cents_1(int[] coins, int N_cents) {
    int[] num_of_ways_of_representing_n_cents = new int[N_cents+1];
    num_of_ways_of_representing_n_cents[0] = 1;
    for (int coin : coins) {
      for (int n = coin; n <= N_cents; n++) {
        num_of_ways_of_representing_n_cents[n] += num_of_ways_of_representing_n_cents[n-coin];
      }
    }

    // for (int n = 1; n <= N_cents; n++) {
    //   System.out.printf("%5d: %5d\n", n, num_of_ways_of_representing_n_cents[n]);
    // }
    // System.out.println();

    return num_of_ways_of_representing_n_cents[N_cents];
  }

  private static int num_of_ways_of_representing_N_cents_2(int[] coins, int N_cents) {
    return num_of_ways_of_representing_N_cents_2(coins, N_cents, coins.length - 1);
  }

  private static int num_of_ways_of_representing_N_cents_2(int[] coins, int N_cents, int index) {
    if (N_cents < 0 || index < 0) {
      return 0;
    }
    if (N_cents == 0) {
      return 1;
    }
    return num_of_ways_of_representing_N_cents_2(coins, N_cents - coins[index], index) + 
           num_of_ways_of_representing_N_cents_2(coins, N_cents, index - 1);
  }
}