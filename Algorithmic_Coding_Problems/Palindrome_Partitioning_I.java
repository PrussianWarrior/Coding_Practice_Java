import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Palindrome partitioningGiven a string s, partition s such that every substring of the partition 
  is a palindrome.

  Return all possible palindrome partitioning of s.
  For example, given s = "aab",
   
  Return
  [
    ["aa","b"],
    ["a","a","b"]
  ]
*/

public class Palindrome_Partitioning_I {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String str = "";
          if (line.indexOf("Customized:") >= 0) {
            str = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<List<String>> all_palindromes_1 = partition_string_into_palindromes_1(str);
          long stop = System.currentTimeMillis();
          long partition_into_palindromes_execution_time_1 = stop - start;

          start = System.currentTimeMillis();
          List<List<String>> all_palindromes_2 = partition_string_into_palindromes_2(str);
          stop = System.currentTimeMillis();
          long partition_into_palindromes_execution_time_2 = stop - start;

          System.out.println("All palindromes:");
          System.out.println("METHOD 1: execution time = " + partition_into_palindromes_execution_time_1);
          print_list(all_palindromes_1);

          System.out.println("METHOD 2: execution time = " + partition_into_palindromes_execution_time_2);
          print_list(all_palindromes_2);

          if (!are_lists_of_lists_of_palindromes_equal(all_palindromes_1, all_palindromes_2)) {
            System.out.println("partition_string_into_palindromes_1 != partition_string_into_palindromes_2");
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

  private static List<List<String>> partition_string_into_palindromes_1(String str) {
    List<List<String>> all_palindromes = new ArrayList<>();
    partition_into_palindromes_execution_time_1_helper(str, 0, new ArrayList<>(), all_palindromes);
    return all_palindromes;
  }

  private static void partition_into_palindromes_execution_time_1_helper(String str, int start, 
      List<String> palindromes, List<List<String>> all_palindromes) {
    if (start >= str.length()) {
      all_palindromes.add(new ArrayList<>(palindromes));
      return;
    }

    for (int i = start + 1; i <= str.length(); i++) {
      String prefix = str.substring(start, i);
      if (is_palindrome(prefix)) {
        palindromes.add(prefix);
        partition_into_palindromes_execution_time_1_helper(str, i, palindromes, all_palindromes);
        palindromes.remove(palindromes.size() - 1);
      }
    }
  }

  private static List<List<String>> partition_string_into_palindromes_2(String str) {
    boolean[][] palindrome_map = new boolean[str.length()][str.length()];

    for (int len = 1; len <= str.length(); len++) {
      for (int start = 0; start <= str.length() - len; start++) {
        int end = start + len - 1;
        if (str.charAt(start) == str.charAt(end) && (len <= 2 || palindrome_map[start+1][end-1])) {
          palindrome_map[start][end] = true;
        }
      }
    }

    List<List<String>> all_palindromes = new ArrayList<>();
    partition_into_palindromes_execution_time_2_helper(str, 0, palindrome_map, new ArrayList<>(), all_palindromes);
    return all_palindromes;
  }

  private static void partition_into_palindromes_execution_time_2_helper(String str, int start, 
      boolean[][] palindrome_map, List<String> palindromes, List<List<String>> all_palindromes) {
    if (start == str.length()) {
      all_palindromes.add(new ArrayList<>(palindromes));
      return;
    }

    for (int i = start + 1; i <= str.length(); i++) {
      if (palindrome_map[start][i - 1]) {
        palindromes.add(str.substring(start, i));
        partition_into_palindromes_execution_time_2_helper(str, i, palindrome_map, palindromes, all_palindromes);
        palindromes.remove(palindromes.size() - 1);
      }
    }
  }

  private static boolean is_palindrome(String str) {
    for (int i = 0, j = str.length() - 1; i < j; i++, j--) {
      if (str.charAt(i) != str.charAt(j)) {
        return false;
      }
    }
    return true;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }

  private static void print_list(List<List<String>> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (String str : list.get(i)) {
        System.out.print(str + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  private static boolean are_lists_of_lists_of_palindromes_equal(List<List<String>> list_1, 
      List<List<String>> list_2) {
    if (list_1.size() != list_2.size()) {
      return false;
    }

    for (int i = 0; i < list_1.size(); i++) {
      if (!are_lists_of_palindromes_equal(list_1.get(i), list_2.get(i))) {
        return false;
      }
    }
    return true;
  }

  private static boolean are_lists_of_palindromes_equal(List<String> list_1, List<String> list_2) {
    if (list_1.size() != list_2.size()) {
      return false;
    }

    for (int i = 0; i < list_1.size(); i++) {
      if (!list_1.get(i).equals(list_2.get(i))) {
        return false;
      }
    }
    return true;
  }
}