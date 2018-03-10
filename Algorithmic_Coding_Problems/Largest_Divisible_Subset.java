import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of 
  elements in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.
 
  If there are multiple solutions, return any subset is fine.
  Example 1:
  nums: [1,2,3]
  Result: [1,2] (of course, [1,3] will also be ok)

  Example 2:
  nums: [1,2,4,8]
  Result: [1,2,4,8]
*/

public class Largest_Divisible_Subset {
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
          List<Integer> largest_divisible_subset_1 = find_largest_divisible_subset_1(list);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          List<Integer> largest_divisible_subset_2 = find_largest_divisible_subset_2(list);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("Largest divisible subset:");
          System.out.println("SOLUTION 1: ");
          print_list(largest_divisible_subset_1);
          System.out.println("Execution time = " + execution_time_soln_1 + "\n");

          System.out.println("SOLUTION 2: ");
          print_list(largest_divisible_subset_2);
          System.out.println("Execution time = " + execution_time_soln_2 + "\n");

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<Integer> find_largest_divisible_subset_1(List<Integer> list) {
    Collections.sort(list);
    List<Integer> largest_divisible_subset = new ArrayList<>();
    largest_divisible_subset.add(0);
    find_largest_divisible_subset_1_helper(list, largest_divisible_subset, new ArrayList<>(), 0);
    return largest_divisible_subset.size() == 1 ? new ArrayList<>() : largest_divisible_subset;
  }

  private static void find_largest_divisible_subset_1_helper(List<Integer> list, List<Integer> largest_divisible_subset, 
    List<Integer> temp, int start_index) {
    if (largest_divisible_subset.size() < temp.size()) {
      largest_divisible_subset.clear();
      largest_divisible_subset.addAll(temp);
    }

    for (int i = start_index; i < list.size(); i++) {
      if (temp.isEmpty()) {
        temp.add(list.get(i));
        find_largest_divisible_subset_1_helper(list, largest_divisible_subset, temp, i + 1);
        temp.remove(temp.size() - 1);
      } else {
        int last = temp.get(temp.size() - 1);
        if (list.get(i) % last == 0) {
          temp.add(list.get(i));
          find_largest_divisible_subset_1_helper(list, largest_divisible_subset, temp, i + 1);
          temp.remove(temp.size() - 1);
        }
      }
    }
  }

  private static List<Integer> find_largest_divisible_subset_2(List<Integer> list) {
    if (list.size() <= 1) {
      return new ArrayList<>();
    }

    Collections.sort(list);
    int max_divisible_subset_size = 0;
    int end_index_largest_divisible_subset_size = -1;

    int[] size_largest_divisible_subset_at_index = new int[list.size()];
    int[] end_index_largest_divisible_subset_at_index = new int[list.size()];

    Arrays.fill(size_largest_divisible_subset_at_index, 1);
    Arrays.fill(end_index_largest_divisible_subset_at_index, -1);

    for (int i = 0; i < list.size(); i++) {
      for (int j = i - 1; j >= 0; j--) {
        if (list.get(i) % list.get(j) == 0 && 
            size_largest_divisible_subset_at_index[i] < size_largest_divisible_subset_at_index[j] + 1) {
          size_largest_divisible_subset_at_index[i] = size_largest_divisible_subset_at_index[j] + 1;
          end_index_largest_divisible_subset_at_index[i] = j;
        }
      }

      if (size_largest_divisible_subset_at_index[i] > max_divisible_subset_size) {
        max_divisible_subset_size = size_largest_divisible_subset_at_index[i];
        end_index_largest_divisible_subset_size = i;
      }
    }

    List<Integer> largest_divisible_subset = new ArrayList<>();
    int i = end_index_largest_divisible_subset_size;
    while (i >= 0) {
      largest_divisible_subset.add(list.get(i));
      i = end_index_largest_divisible_subset_at_index[i];
    }
    return largest_divisible_subset;
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
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] integer_strings = line.split(" ");

    List<Integer> list = new ArrayList<>();
    for (String int_str : integer_strings) {
      list.add(Integer.parseInt(int_str));
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
      } else if (i == line.length() - 1 || Character.isSpace(line.charAt(i+1))) {
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