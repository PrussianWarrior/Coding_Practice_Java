import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a collection of numbers that might contain duplicates, return all possible unique permutations.

  For example,
  [1,1,2] have the following unique permutations:
  [
    [1,1,2],
    [1,2,1],
    [2,1,1]
  ]
*/

public class Permutation_2 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> list = null;
          if (line.indexOf("Customized:") >= 0) {
            list = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<List<Integer>> all_permutations_1 = find_all_permutations_1(list);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("ALL PERMUTATIONS:");
          System.out.println("Solution 1:");
          print_lists(all_permutations_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<List<Integer>> find_all_permutations_1(List<Integer> list) {
    List<List<Integer>> all_combinations = new ArrayList<>();
    TreeMap<Integer, Integer> num_count = new TreeMap<>();
    for (int n : list) {
      int count = num_count.containsKey(n) ? num_count.get(n) : 0;
      num_count.put(n, count + 1);
    }
    find_all_permutations_1(num_count, list.size(), new ArrayList<>(), all_combinations);
    return all_combinations;
  }

  private static void find_all_permutations_1(TreeMap<Integer, Integer> num_count, int num_int, 
      List<Integer> temp, List<List<Integer>> all_permutations) {
    if (temp.size() == num_int) {
      all_permutations.add(new ArrayList<>(temp));
      return;
    }

    for (int num : num_count.keySet()) {
      int count = num_count.get(num);
      if (count > 0) {
        num_count.put(num, count - 1);
        temp.add(num);
        find_all_permutations_1(num_count, num_int, temp, all_permutations);
        temp.remove(temp.size() - 1);
        num_count.put(num, count);
      }
    }
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();

    List<Integer> list = new ArrayList<>();
    for (String int_str : line.split(" ")) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    Collections.sort(list);
    return list;
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

  private static void print_lists(List<List<Integer>> all_combinations) {
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