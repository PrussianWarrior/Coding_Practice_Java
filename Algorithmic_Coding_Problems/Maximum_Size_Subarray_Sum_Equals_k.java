import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an array nums and a target value k, find the maximum length of a subarray that sums to k.
  If there isn't one, return 0 instead.

  Note:
  The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer range.

  Example 1:
  Given nums = [1, -1, 5, -2, 3], k = 3,
  return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)
*/

public class Maximum_Size_Subarray_Sum_Equals_k {
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
            input = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            input = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(input.list);
          System.out.println("k = " + input.k);

          long start = System.currentTimeMillis();
          int max_length_1 = find_max_subarray_size_sum_equals_to_k_1(input.list, input.k);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int max_length_2 = find_max_subarray_size_sum_equals_to_k_2(input.list, input.k);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("Max size of subarray with sum = k:");
          System.out.println("Solution 1 = " + max_length_1);
          System.out.println("Solution 2 = " + max_length_2);

          if (max_length_1 != max_length_2) {
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

  private static int find_max_subarray_size_sum_equals_to_k_1(List<Integer> list, int k) {
    int max_length = 0;
    for (int i_1 = 0; i_1 < list.size(); i_1++) {
      int sum = list.get(i_1);
      if (sum == k) {
        max_length = Math.max(max_length, 1);
      }
      for (int i_2 = i_1 + 1; i_2 < list.size(); i_2++) {
        sum += list.get(i_2);
        if (sum == k) {
          max_length = Math.max(max_length, i_2 - i_1 + 1);
        }
      }
    }
    return max_length;
  }

  private static int find_max_subarray_size_sum_equals_to_k_2(List<Integer> list, int k) {
    Map<Integer, Integer> cumulative_sum = new HashMap<>();

    int max_length = 0;
    int sum = 0;

    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i);

      if (sum == k) {
        max_length = Math.max(max_length, i + 1);
      }

      if (cumulative_sum.containsKey(sum - k)) {
        max_length = Math.max(max_length, i - cumulative_sum.get(sum - k) + 1);
      }

      if (!cumulative_sum.containsKey(sum)) {
        cumulative_sum.put(sum, i + 1);
      }
    }

    return max_length;
  }

  private static List_K get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] list_k = line.split(",");
    int k = Integer.parseInt(list_k[1].trim());
    int index_of_opening_square_bracket = list_k[0].indexOf("[");
    int index_of_closing_square_bracket = list_k[0].indexOf("]");
    list_k[0] = list_k[0].substring(index_of_opening_square_bracket + 1, 
        index_of_closing_square_bracket).trim();
    List<Integer> list = new ArrayList<>();
    for (String int_str : list_k[0].split(" ")) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    return new List_K(list, k);
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