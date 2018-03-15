import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  You are given coins of different denominations and a total amount of money amount. 
  Write a function to compute the fewest number of coins that you need to make up that amount. 
  If that amount of money cannot be made up by any combination of the coins, return -1.
 
  Example 1:
  coins = [1, 2, 5], amount = 11
  return 3 (11 = 5 + 5 + 1)
  Example 2:
  coins = [2], amount = 3
  return -1.
  Note:
  You may assume that you have an infinite number of each kind of coin.
*/

public class Coin_Change {
  private static class Coins_Amount {
    List<Integer> coins;
    int amount;

    public Coins_Amount(List<Integer> coins, int amount) {
      this.coins = coins;
      this.amount = amount;
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

          Coins_Amount coins_amount = null;
          if (line.indexOf("Customized:") >= 0) {
            coins_amount = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          int min_num_coins_1 = find_min_num_of_coin_changes_1(coins_amount.coins, coins_amount.amount);          
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int min_num_coins_2 = find_min_num_of_coin_changes_2(coins_amount.coins, coins_amount.amount);          
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("MIN NUMBER OF COINS NEEDED FOR CHANGE");
          System.out.println("METHOD 1: " + min_num_coins_1 + ", execution time = " + execution_time_soln_1);
          System.out.println("METHOD 2: " + min_num_coins_2 + ", execution time = " + execution_time_soln_2);

          if (min_num_coins_1 != min_num_coins_2) {
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

  private static int find_min_num_of_coin_changes_1(List<Integer> coins, int amount) {
    if (amount <= 0) {
      return -1;
    }

    int[] min_num_coins = new int[amount + 1];
    Arrays.fill(min_num_coins, Integer.MAX_VALUE);
    min_num_coins[0] = 0;

    for (int n = 0; n <= amount; n++) {
      for (int coin : coins) {
        if (n + coin <= amount && min_num_coins[n] != Integer.MAX_VALUE) {
          min_num_coins[n + coin] = Math.min(min_num_coins[n + coin], min_num_coins[n] + 1);
        }
      }
    }
    return min_num_coins[amount] == Integer.MAX_VALUE ? -1 : min_num_coins[amount];
  }

  private static int find_min_num_of_coin_changes_2(List<Integer> coins, int amount) {
    if (amount <= 0) {
      return -1;
    }

    int[] min_num_coins = new int[amount + 1];
    Arrays.fill(min_num_coins, Integer.MAX_VALUE);
    min_num_coins[0] = 0;

    for (int n = 1; n <= amount; n++) {
      for (int coin : coins) {
        if (n >= coin) {
          if (min_num_coins[n - coin] != Integer.MAX_VALUE) {
            min_num_coins[n] = Math.min(min_num_coins[n], min_num_coins[n - coin] + 1);
          }
        }
      }
    }
    return min_num_coins[amount] == Integer.MAX_VALUE ? -1 : min_num_coins[amount];
  }

  private static Coins_Amount get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1);
    int index_of_opening_square_bracket = line.indexOf("[");
    int index_of_closing_square_bracket = line.indexOf("]");
    String coin_str = remove_extra_spaces_1(line.substring(
        index_of_opening_square_bracket + 1, 
        index_of_closing_square_bracket));

    int amount = Integer.parseInt(line.substring(index_of_closing_square_bracket + 1).trim());

    List<Integer> coins = new ArrayList<>();

    for (String str : coin_str.split(" ")) {
      coins.add(Integer.parseInt(str));
    }
    return new Coins_Amount(coins, amount);
  }

  private static List<Integer> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] min_max_len_k = line.split(" ");
    int min = Integer.parseInt(min_max_len_k[0].trim());
    int max = Integer.parseInt(min_max_len_k[1].trim());
    int len = Integer.parseInt(min_max_len_k[2].trim());

    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      int random_num = (int)(Math.random() * (max - min + 1) + min);
      list.add(random_num);
    }

    return list;
  }

  private static String remove_extra_spaces_1(String str) {
    str = str.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || Character.isSpace(str.charAt(i + 1))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i < str.length() - 1) {
          new_str.append(" ");
        }
      }
    }

    return new_str.toString();
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}