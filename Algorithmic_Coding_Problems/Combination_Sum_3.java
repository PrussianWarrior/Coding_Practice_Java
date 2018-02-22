import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Find all possible combinations of k numbers that add up to a number n, given that
  only numbers from 1 to 9 can be used and each combination should be a unique set of
  numbers.

  Example 1:

  Input: k = 3, n = 7

  Output:

  [[1,2,4]]

  Example 2:

  Input: k = 3, n = 9

  Output:

  [[1,2,6], [1,3,5], [2,3,4]]
*/

public class Combination_Sum_3 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          int[] k_sum = null;
          if (line.indexOf("Customized:") >= 0) {
            k_sum = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("k = " + k_sum[0] + ", n = " + k_sum[1]);

          long start = System.currentTimeMillis();
          List<List<Integer>> all_combinations_sum_1 = find_all_combinations_sum_1(k_sum[0], k_sum[1]);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Combinations that amount to sum:");
          System.out.println("Solution 1: ");
          print_combinations(all_combinations_sum_1);
          System.out.println("Solution 1 execution time = " + execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<List<Integer>> find_all_combinations_sum_1(int k, int sum) {
    List<List<Integer>> all_combinations = new ArrayList<>();
    find_all_combinations_sum_1(1, k, sum, new ArrayList<>(), all_combinations);
    return all_combinations;
  }

  private static void find_all_combinations_sum_1(int start, int k, int sum, 
    ArrayList<Integer> temp, List<List<Integer>> all_combinations) {
    if (temp.size() == k) {
      if (sum == 0) {
        all_combinations.add(new ArrayList<>(temp));
      }
      return;
    }

    for (int num = start; num <= 9; num++) {
      if (sum < num) {
        return;
      }

      temp.add(num);
      find_all_combinations_sum_1(num + 1, k, sum - num, temp, all_combinations);
      temp.remove(temp.size() - 1);
    }
  }

  private static int[] get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] int_str = line.split(" ");
    int k = Integer.parseInt(int_str[0].trim());
    int n = Integer.parseInt(int_str[1].trim());
    return new int[]{k, n};
  }

  private static void print_combinations(List<List<Integer>> all_combinations) {
    for (int i = 0; i < all_combinations.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : all_combinations.get(i)) {
        System.out.printf("%3d", n);
      }
      System.out.println();
    }
    System.out.println();
  }
}