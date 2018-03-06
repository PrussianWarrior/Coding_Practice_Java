import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  You are a professional robber planning to rob houses along a street. Each house has a certain amount 
  of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses 
  have security system connected and it will automatically contact the police if two adjacent houses 
  were broken into on the same night.

  Given a list of non-negative integers representing the amount of money of each house, determine the 
  maximum amount of money you can rob tonight without alerting the police.
*/

public class House_Robber_I {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> list = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            list = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            list = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(list);

          long start = System.currentTimeMillis();
          int max_sum_sub_sequence_1 = find_max_sum_sub_sequence_1(list);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int max_sum_sub_sequence_2 = find_max_sum_sub_sequence_2(list);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int max_sum_sub_sequence_3 = find_max_sum_sub_sequence_3(list);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          start = System.currentTimeMillis();
          int max_sum_sub_sequence_4 = find_max_sum_sub_sequence_4(list);
          stop = System.currentTimeMillis();
          long execution_time_soln_4 = stop - start;

          System.out.println("Closest sum:");
          System.out.println("Solution 1 = " + max_sum_sub_sequence_1 + ", execution time = " 
            + execution_time_soln_1);
          System.out.println("Solution 2 = " + max_sum_sub_sequence_2 + ", execution time = " 
            + execution_time_soln_2);
          System.out.println("Solution 3 = " + max_sum_sub_sequence_3 + ", execution time = " 
            + execution_time_soln_3);
          System.out.println("Solution 4 = " + max_sum_sub_sequence_4 + ", execution time = " 
            + execution_time_soln_4);

          if (max_sum_sub_sequence_1 != max_sum_sub_sequence_2 ||
              max_sum_sub_sequence_1 != max_sum_sub_sequence_3 ||
              max_sum_sub_sequence_1 != max_sum_sub_sequence_4) {
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

  private static int find_max_sum_sub_sequence_1(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("List cannot be empty");
    }
    return find_max_sum_sub_sequence_1(list, list.size() - 1);
  }

  private static int find_max_sum_sub_sequence_1(List<Integer> list, int end_index) {
    if (end_index < 0) {
      return 0;
    }

    if (end_index == 0) {
      return list.get(0);
    }

    if (end_index == 1) {
      return Math.max(list.get(0), list.get(1));
    }

    int sum_end_element_included = list.get(end_index) + find_max_sum_sub_sequence_1(list, end_index - 2);
    int sum_end_element_excluded = find_max_sum_sub_sequence_1(list, end_index - 1);
    return Math.max(sum_end_element_included, sum_end_element_excluded);
  }

  private static int find_max_sum_sub_sequence_2(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("List cannot be empty");
    }
    Map<Integer, Integer> cache = new HashMap<>();
    return find_max_sum_sub_sequence_2(list, list.size() - 1, cache);
  }

  private static int find_max_sum_sub_sequence_2(List<Integer> list, int end_index, Map<Integer, Integer> cache) {
    if (end_index < 0) {
      return 0;
    }

    if (end_index == 0) {
      return list.get(0);
    }

    if (end_index == 1) {
      return Math.max(list.get(0), list.get(1));
    }

    if (cache.containsKey(end_index)) {
      // System.out.println("Returning cached result.");
      return cache.get(end_index);
    }

    int sum_end_element_included = list.get(end_index) + find_max_sum_sub_sequence_2(list, end_index - 2, cache);
    int sum_end_element_excluded = find_max_sum_sub_sequence_2(list, end_index - 1, cache);
    int max_sum = Math.max(sum_end_element_included, sum_end_element_excluded);
    cache.put(end_index, max_sum);
    return max_sum;
  }

  private static int find_max_sum_sub_sequence_3(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("List cannot be empty.");
    }

    if (list.size() == 1) {
      return list.get(0);
    }

    if (list.size() == 2) {
      return Math.max(list.get(0), list.get(1));
    }

    int[] max_sum_at_position = new int[list.size()];
    max_sum_at_position[0] = list.get(0);
    max_sum_at_position[1] = Math.max(list.get(0), list.get(1));
    for (int i = 2; i < list.size(); i++) {
      max_sum_at_position[i] = Math.max(max_sum_at_position[i - 1], max_sum_at_position[i - 2] + list.get(i));
    }
    return max_sum_at_position[list.size() - 1];
  }

  private static int find_max_sum_sub_sequence_4(List<Integer> list) {
    int odd = 0;
    int even = 0;
    for (int i = 0; i < list.size(); i++) {
      if (i % 2 == 0) {
        even += list.get(i);
        even = Math.max(even, odd);
      } else {
        odd += list.get(i);
        odd = Math.max(odd, even);
      }
    }
    return Math.max(even, odd);
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] integer_strings = line.split(" ");
    
    List<Integer> list = new ArrayList<>();
    for (String int_str : integer_strings) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    return list;
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

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}