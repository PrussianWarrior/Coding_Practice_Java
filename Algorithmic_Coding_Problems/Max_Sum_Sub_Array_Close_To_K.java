import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an array, find the maximum sum of subarray close to k but not larger than k.
*/

public class Max_Sum_Sub_Array_Close_To_K {
  private static class List_K {
    List<Integer> list;
    int k;

    public List_K(List<Integer> list, int k) {
      this.list = list;
      this.k = k;
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

          List_K input = null;
          if (line.indexOf("Customized:") >= 0) {
            
          } else if (line.indexOf("Randomized:") >= 0) {
            input = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(input.list);
          System.out.println("k = " + input.k);

          long start = System.currentTimeMillis();
          int closest_sum_1 = find_max_sum_sub_arr_close_to_k_1(input.list, input.k);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int closest_sum_2 = find_max_sum_sub_arr_close_to_k_2(input.list, input.k);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("Closest sum:");
          System.out.println("Solution 1 = " + closest_sum_1);
          System.out.println("Solution 2 = " + closest_sum_2);

          if (closest_sum_1 != closest_sum_2) {
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

  private static int find_max_sum_sub_arr_close_to_k_1(List<Integer> list, int k) {
    int max_diff = Integer.MAX_VALUE;
    int closest_sum = 0;
    for (int i_1 = 0; i_1 < list.size(); i_1++) {
      int sum = list.get(i_1);
      if (sum == k) {
        closest_sum = sum;
        break;
      }

      if (k > sum && (k - sum) < max_diff) {
        max_diff = k - sum;
        closest_sum = sum;
      }

      for (int i_2 = i_1 + 1; i_2 < list.size(); i_2++) {
        sum += list.get(i_2);

        if (sum == k) {
          return sum;
        }

        if (k > sum && (k - sum) < max_diff) {
          max_diff = k - sum;
          closest_sum = sum;
        }
      }
    }

    return closest_sum;
  }

  private static int find_max_sum_sub_arr_close_to_k_2(List<Integer> list, int k) {
    TreeSet<Integer> cumulative_sum = new TreeSet<>();
    int closest_sum = Integer.MIN_VALUE;
    cumulative_sum.add(0);
    int sum = 0;
    for (int n : list) {
      sum += n;
      Integer ceiling_value = cumulative_sum.ceiling(sum - k);
      if (ceiling_value != null) {
        closest_sum = Math.max(closest_sum, sum - ceiling_value);
      }

      cumulative_sum.add(sum);
    }

    return closest_sum;
  }

  private static List_K get_customized_input(String line) {
    return null;
  }

  private static List_K get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] min_max_len_k = line.split(" ");
    int min = Integer.parseInt(min_max_len_k[0].trim());
    int max = Integer.parseInt(min_max_len_k[1].trim());
    int len = Integer.parseInt(min_max_len_k[2].trim());
    int k   = Integer.parseInt(min_max_len_k[3].trim());

    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      int random_num = (int)(Math.random() * (max - min + 1) + min);
      list.add(random_num);
    }

    return new List_K(list, k);
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}