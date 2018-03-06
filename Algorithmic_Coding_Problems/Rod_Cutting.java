import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Problem: Find best way to cut a rod of length n
  For example, if length of the rod is 8 and the values of different pieces are given as
  following, then the maximum obtainable value is 22 (by cutting in two pieces of lengths
  2 and 6)

  length   | 1   2   3   4   5   6   7   8  
  --------------------------------------------
  price    | 1   5   8   9  10  17  17  20

  And if the prices are as following, then the maximum obtainable value is 24 (by cutting
  in eight pieces of length 1)

  length   | 1   2   3   4   5   6   7   8  
  --------------------------------------------
  price    | 3   5   8   9  10  17  17  20
*/

public class Rod_Cutting {
  private static class Best_Cut_Max_Profit {
    List<Integer> best_cuts;
    int max_profit;

    public Best_Cut_Max_Profit(List<Integer> best_cuts, int max_profit) {
      this.best_cuts = best_cuts;
      this.max_profit = max_profit;
    }
  }

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> prices = new ArrayList<>();
          if (line.indexOf("Customized") >= 0) {
            prices = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            prices = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_prices(prices);

          long start = System.currentTimeMillis();
          int max_profit_1 = find_max_profit_1(prices);
          long stop = System.currentTimeMillis();
          long max_profit_execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int max_profit_2 = find_max_profit_2(prices);
          stop = System.currentTimeMillis();
          long max_profit_execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int max_profit_3 = find_max_profit_3(prices);
          stop = System.currentTimeMillis();
          long max_profit_execution_time_soln_3 = stop - start;

          start = System.currentTimeMillis();
          Best_Cut_Max_Profit best_cut_max_profit_1 = find_best_cuts_max_profit_1(prices);
          stop = System.currentTimeMillis();
          long best_cut_max_profit_execution_time_soln_1 = stop - start;

          System.out.println("MAX PROFIT:");
          System.out.println("Method 1: " + max_profit_1 + ", execution time = " + max_profit_execution_time_soln_1);
          System.out.println("Method 2: " + max_profit_2 + ", execution time = " + max_profit_execution_time_soln_2);
          System.out.println("Method 3: " + max_profit_3 + ", execution time = " + max_profit_execution_time_soln_3);
          System.out.println("Method 4: " + best_cut_max_profit_1.max_profit + ", execution time = " 
            + best_cut_max_profit_execution_time_soln_1);
          System.out.println("The cuts that maximize profit are:");
          for (int cut : best_cut_max_profit_1.best_cuts) {
            System.out.printf("%5d: %5d\n", cut, prices.get(cut - 1));
          }
          System.out.println();

          if (max_profit_1 != max_profit_2 ||
              max_profit_1 != max_profit_3 ||
              max_profit_1 != best_cut_max_profit_1.max_profit) {
            System.out.println("FAILED");
            break;
          }

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_max_profit_1(List<Integer> prices) {
    return find_max_profit_1(prices, prices.size());
  }

  private static int find_max_profit_1(List<Integer> prices, int cut) {
    if (cut <= 0) {
      return 0;
    }

    if (cut == 1) {
      return prices.get(0);
    }

    int max_profit = 0;
    for (int i = cut; i >= 1; i--) { 
      max_profit = Math.max(max_profit, prices.get(i - 1) + find_max_profit_1(prices, cut - i));
    }

    return max_profit;
  }

  private static int find_max_profit_2(List<Integer> prices) {
    return find_max_profit_2(prices, prices.size(), new int[prices.size()]);
  }

  private static int find_max_profit_2(List<Integer> prices, int cut, int[] cache) {
    if (cut <= 0) {
      return 0;
    }

    if (cut == 1) {
      return prices.get(0);
    }

    if (cache[cut - 1] > 0) {
      // System.out.println("Returning cached result.");
      return cache[cut - 1];
    }

    int max_profit = 0;
    for (int i = cut; i >= 1; i--) {
      max_profit = Math.max(max_profit, prices.get(i - 1) + find_max_profit_2(prices, cut - i, cache));
    }

    cache[cut - 1] = max_profit;
    return max_profit;
  }

  private static int find_max_profit_3(List<Integer> prices) {
    int[] max_profit_at_cut = new int[prices.size() + 1];
    max_profit_at_cut[1] = prices.get(0);

    for (int cut = 2; cut <= prices.size(); cut++) {
      for (int i = cut; i >= 1; i--) {
        max_profit_at_cut[cut] = Math.max(max_profit_at_cut[cut], prices.get(i - 1) + max_profit_at_cut[cut - i]);
      }
    }
    return max_profit_at_cut[prices.size()];
  }

  private static Best_Cut_Max_Profit find_best_cuts_max_profit_1(List<Integer> prices) {
    int[] max_profit_at_cut = new int[prices.size() + 1];
    int[] best_cut_at_cut = new int[prices.size() + 1];
    max_profit_at_cut[1] = prices.get(0);

    for (int cut = 2; cut <= prices.size(); cut++) {
      for (int i = cut; i >= 1; i--) {
        if (max_profit_at_cut[cut] < prices.get(i - 1) + max_profit_at_cut[cut - i]) {
          max_profit_at_cut[cut] = prices.get(i - 1) + max_profit_at_cut[cut - i];
          best_cut_at_cut[cut] = i;
        }
      }
    }

    List<Integer> best_cuts = new ArrayList<>();
    int cut = prices.size();
    while (best_cut_at_cut[cut] >= 1) {
      best_cuts.add(best_cut_at_cut[cut]);
      cut -= best_cut_at_cut[cut];
    }

    return new Best_Cut_Max_Profit(best_cuts, max_profit_at_cut[prices.size()]);
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon+1));
    List<Integer> prices = new ArrayList<>();
    for (String integer_str : line.split(" ")) {
      prices.add(Integer.parseInt(integer_str));
    }
    return prices;
  }

  private static List<Integer> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] integer_str = line.split(" ");

    int len = Integer.parseInt(integer_str[0]);
    int min = Integer.parseInt(integer_str[1]);
    int max = Integer.parseInt(integer_str[2]);

    List<Integer> prices = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      int random_data = (int)(Math.random() * (max - min + 1)) + min;
      prices.add(random_data);
    }
    return prices;
  }

  private static String remove_extra_spaces_1(String line) {
    line = line.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (Character.isSpace(line.charAt(i))) {
        start = i;
      } else if (i == line.length() - 1 || Character.isSpace(line.charAt(i + 1))) {
        new_str.append(line.substring(start + 1, i + 1));
        if (i < line.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }

  private static void print_prices(List<Integer> prices) {
    for (int i = 0; i < prices.size(); i++) {
      System.out.printf("%5d: %5d\n", i + 1, prices.get(i));
    }
    System.out.println();
  }
}