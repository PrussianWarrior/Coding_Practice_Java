import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a set of distinct integers, nums, return all possible subsets (the power set).
  Note: The solution set must not contain duplicate subsets.
  For example,
  If nums = [1,2,3], a solution is:
  [
    [3],
    [1],
    [2],
    [1,2,3],
    [1,3],
    [2,3],
    [1,2],
    []
  ]
*/

public class Subset_1 {
  private static class List_Length_Comparator implements Comparator<List<Integer>> {
    @Override
    public int compare(List<Integer> list_1, List<Integer> list_2) {
      return list_1.size() - list_2.size();
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

          List<Integer> set = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            set = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<List<Integer>> all_subsets_1 = find_subsets_1(set);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          List<List<Integer>> all_subsets_2 = find_subsets_2(set);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("All subsets:");
          System.out.println("Solution 1: ");
          print_subsets(all_subsets_1);
          System.out.println("Execution time = " + execution_time_soln_1);

          System.out.println("Solution 2: ");
          print_subsets(all_subsets_2);
          System.out.println("Execution time = " + execution_time_soln_2);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<List<Integer>> find_subsets_1(List<Integer> set) {
    Collections.sort(set);
    List<List<Integer>> all_subsets = new ArrayList<>();

    for (int n : set) {
      List<List<Integer>> temp = new ArrayList<>();
      for (List<Integer> list : all_subsets) {
        temp.add(new ArrayList<>(list));
      }

      for (List<Integer> list : temp) {
        list.add(n);
      }

      List<Integer> one_element_subset = new ArrayList<>();
      one_element_subset.add(n);
      temp.add(one_element_subset);
      all_subsets.addAll(temp);
    }

    all_subsets.add(new ArrayList<>());
    Collections.sort(all_subsets, new List_Length_Comparator());
    return all_subsets;
  }

  private static List<List<Integer>> find_subsets_2(List<Integer> set) {
    Collections.sort(set);
    List<List<Integer>> all_subsets = new ArrayList<>();
    all_subsets.add(new ArrayList<>());
    for (int n = 1; n <= (1 << set.size()) - 1; n++) {
      all_subsets.add(get_subset(n, set));
    }

    Collections.sort(all_subsets, new List_Length_Comparator());
    return all_subsets;
  }

  private static List<Integer> get_subset(int n, List<Integer> set) {
    List<Integer> subset = new ArrayList<>();
    int i = 0;
    while (n > 0) {
      if ((n & 1) == 1) {
        subset.add(set.get(i));
      }
      n >>= 1;
      i++;
    }
    return subset;
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

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d - %5d\n", i, list.get(i));
    }
    System.out.println();
  }

  private static void print_subsets(List<List<Integer>> all_subsets) {
    for (int i = 0; i < all_subsets.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : all_subsets.get(i)) {
        System.out.printf("%5d", n);
      }
      System.out.println();
    }
    System.out.println();
  }
}