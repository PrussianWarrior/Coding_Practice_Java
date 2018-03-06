import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  After robbing those houses on that street, the thief has found himself a new place for his thievery 
  so that he will not get too much attention. This time, all houses at this place are arranged in a 
  circle. That means the first house is the neighbor of the last one. Meanwhile, the security system 
  for these houses remain the same as for those in the previous street.

  Given a list of non-negative integers representing the amount of money of each house, determine the 
  maximum amount of money you can rob tonight without alerting the police.
*/

public class House_Robber_II {
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
      throw new IllegalArgumentException("The input list to find_max_sum_sub_sequence_1 cannot be empty.");
    }
    int max_sum_1st_included_last_excluded = find_max_sum_sub_sequence_1(list, 0, list.size() - 2);
    int max_sum_1st_excluded_last_included = find_max_sum_sub_sequence_1(list, 1, list.size() - 1);
    return Math.max(max_sum_1st_included_last_excluded, max_sum_1st_excluded_last_included);
  }

  private static int find_max_sum_sub_sequence_1(List<Integer> list, int start_index, int end_index) {
    if (start_index > end_index) {
      return 0;
    }
    if (start_index == end_index) {
      return list.get(start_index);
    }
    if (start_index + 1 == end_index) {
      return Math.max(list.get(start_index), list.get(end_index));
    }

    int max_sum_last_included = find_max_sum_sub_sequence_1(list, start_index, end_index - 2) + list.get(end_index);
    int max_sum_last_excluded = find_max_sum_sub_sequence_1(list, start_index, end_index - 1);
    return Math.max(max_sum_last_included, max_sum_last_excluded);
  }

  private static int find_max_sum_sub_sequence_2(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("The input list to find_max_sum_sub_sequence_2 cannot be empty.");
    }
    int max_sum_1st_included_last_excluded = find_max_sum_sub_sequence_2(list, 0, list.size() - 2, new HashMap<>());
    int max_sum_1st_excluded_last_included = find_max_sum_sub_sequence_2(list, 1, list.size() - 1, new HashMap<>());
    return Math.max(max_sum_1st_included_last_excluded, max_sum_1st_excluded_last_included);
  }

  private static int find_max_sum_sub_sequence_2(List<Integer> list, int start_index, int end_index, 
      Map<Integer, Integer> cache) {
    if (start_index > end_index) {
      return 0;
    }
    if (start_index == end_index) {
      return list.get(start_index);
    }
    if (start_index + 1 == end_index) {
      return Math.max(list.get(start_index), list.get(end_index));
    }
    if (cache.containsKey(end_index)) {
      return cache.get(end_index);
    }

    int max_sum_last_included = find_max_sum_sub_sequence_2(list, start_index, end_index - 2, cache) + list.get(end_index);
    int max_sum_last_excluded = find_max_sum_sub_sequence_2(list, start_index, end_index - 1, cache);
    int result = Math.max(max_sum_last_included, max_sum_last_excluded);
    cache.put(end_index, result);
    return result;
  }

  private static int find_max_sum_sub_sequence_3(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("The input list to find_max_sum_sub_sequence_3 cannot be empty.");
    }
    if (list.size() == 1) {
      return list.get(0);
    }
    if (list.size() == 2) {
      return Math.max(list.get(0), list.get(1));
    }
    int max_sum_1st_included_last_excluded = find_max_sum_sub_sequence_3(list, 0, list.size() - 2);
    int max_sum_1st_excluded_last_included = find_max_sum_sub_sequence_3(list, 1, list.size() - 1);

    return Math.max(max_sum_1st_included_last_excluded, max_sum_1st_excluded_last_included);
  }

  private static int find_max_sum_sub_sequence_3(List<Integer> list, int start_index, int end_index) {
    int len = end_index - start_index + 1;
    int[] max_sum_at_position = new int[len];
    max_sum_at_position[0] = list.get(start_index);
    max_sum_at_position[1] = Math.max(list.get(start_index), list.get(start_index + 1));
    for (int i = 2; i < len; i++) {
      max_sum_at_position[i] = Math.max(
        max_sum_at_position[i - 1],
        max_sum_at_position[i - 2] + list.get(start_index + i)
      );
    }
    return max_sum_at_position[len - 1];
  }

  private static int find_max_sum_sub_sequence_4(List<Integer> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("The input list to find_max_sum_sub_sequence_4 cannot be empty.");
    }
    if (list.size() == 1) {
      return list.get(0);
    }
    if (list.size() == 2) {
      return Math.max(list.get(0), list.get(1));
    }

    int max_sum_1st_included_last_excluded = find_max_sum_sub_sequence_4(list, 0, list.size() - 2);
    int max_sum_1st_excluded_last_included = find_max_sum_sub_sequence_4(list, 1, list.size() - 1);
    return Math.max(max_sum_1st_included_last_excluded, max_sum_1st_excluded_last_included);
  }

  private static int find_max_sum_sub_sequence_4(List<Integer> list, int start_index, int end_index) {
    int odd = 0;
    int even = 0;
    for (int i = start_index; i <= end_index; i++) {
      if (i % 2 == 0) {
        even += list.get(i);
        even = Math.max(odd, even);
      } else {
        odd += list.get(i);
        odd = Math.max(odd, even);
      }
    }
    return Math.max(odd, even);
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

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}