import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a collection of candidate numbers (C) and a target number (T), 
  find all unique combinations in C where the candidate numbers sums to T.

  Each number in C may only be used once in the combination.

  Note:
  All numbers (including target) will be positive integers.
  The solution set must not contain duplicate combinations.
  For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8, 
  A solution set is: 
  [
    [1, 7],
    [1, 2, 5],
    [2, 6],
    [1, 1, 6]
  ]

*/

public class Combination_Sum_2 {
  private static class List_Sum {
    List<Integer> list;
    int sum;

    public List_Sum(List<Integer> list, int sum) {
      this.list = list;
      this.sum = sum;
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List_Sum list_sum = null;
          if (line.indexOf("Customized:") >= 0) {
            list_sum = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          // if (contains_duplicate(list_sum.list)) {
          //   throw new IllegalArgumentException("The input list must not contain any duplicate");
          // }

          System.out.println("List:");
          print_list_without_index(list_sum.list);
          System.out.println("Sum = " + list_sum.sum);

          long start = System.currentTimeMillis();
          List<List<Integer>> all_combinations_sum_1 = find_all_combinations_sum_1(list_sum.list, list_sum.sum);
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

  private static List<List<Integer>> find_all_combinations_sum_1(List<Integer> list, int sum) {
    Collections.sort(list);
    List<List<Integer>> all_combinations = new ArrayList<>();
    find_all_combinations_sum_1(list, 0, sum, new ArrayList<>(), all_combinations);
    return all_combinations;
  }

  private static void find_all_combinations_sum_1(List<Integer> list, int start_index, int sum, 
    ArrayList<Integer> temp, List<List<Integer>> all_combinations) {
    if (sum == 0) {
      all_combinations.add(new ArrayList<>(temp));
      return;
    }

    int prec_index = -1;
    for (int i = start_index; i < list.size(); i++) {
      if (sum < list.get(i)) {
        return;
      }
      if (prec_index >= 0 && list.get(prec_index) == list.get(i)) {
        continue;
      }
      temp.add(list.get(i));
      find_all_combinations_sum_1(list, i + 1, sum - list.get(i), temp, all_combinations);
      temp.remove(temp.size() - 1);
      prec_index = i;
    }
  }

  private static List_Sum get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    int index_of_opening_bracket = line.indexOf("[");
    int index_of_closing_bracket = line.indexOf("]");

    String string_of_int = line.substring(index_of_opening_bracket + 1, index_of_closing_bracket);
    String sum_str = line.substring(index_of_closing_bracket + 1);

    int sum = Integer.parseInt(sum_str.trim());

    List<Integer> list = new ArrayList<>();
    for (String int_str : string_of_int.split(" ")) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    Collections.sort(list);
    return new List_Sum(list, sum);
  }

  private static void print_list_with_index(List<Integer> list) {
    int longest_index = num_of_digits(list.size());
    int largest_length = largest_length(list);
    String index_width_format = "%" + longest_index + "d";
    String num_width_format = "%" + largest_length + "d";

    for (int i = 0; i < list.size(); i++) {
      System.out.printf(index_width_format + " - " + num_width_format + "\n", i, list.get(i));
    }
    System.out.println();
  }

  private static void print_list_without_index(List<Integer> list) {
    int largest_length = largest_length(list);
    String num_width_format = "%" + (largest_length + 1) + "d";
    for (int n : list) {
      System.out.printf(num_width_format, n);
    }
    System.out.println();
  }

  private static int num_of_digits(int N) {
    return (int)Math.floor(Math.log10(N)) + 1;
  }

  private static int largest_length(List<Integer> list) {
    int max_len = 0;
    for (int n : list) {
      max_len = Math.max(max_len, num_of_digits(n));
    }
    return max_len;
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

  private static boolean contains_duplicate(List<Integer> list) {
    Set<Integer> already_checked_num = new HashSet<>();
    for (int n : list) {
      if (already_checked_num.contains(n)) {
        return true;
      }
      already_checked_num.add(n);
    }
    return false;
  }
}