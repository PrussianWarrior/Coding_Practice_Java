import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a string s, partition s such that every substring of the partition is a palindrome.
  Return the minimum cuts needed for a palindrome partitioning of s.
  For example, given s = "aab",
  Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
*/

public class Palindrome_Partitioning_II {
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

          // long start = System.currentTimeMillis();
          // int min_cut_1 = find_min_cut_to_partition_into_palindromes_1(str);
          // long stop = System.currentTimeMillis();
          // long partition_into_palindromes_execution_time_1 = stop - start;

          long start = System.currentTimeMillis();
          int min_cut_2 = find_min_cut_to_partition_into_palindromes_2(str);
          long stop = System.currentTimeMillis();
          long partition_into_palindromes_execution_time_2 = stop - start;

          start = System.currentTimeMillis();
          int min_cut_3 = find_min_cut_to_partition_into_palindromes_3(str);
          stop = System.currentTimeMillis();
          long partition_into_palindromes_execution_time_3 = stop - start;

          start = System.currentTimeMillis();
          int min_cut_4 = find_min_cut_to_partition_into_palindromes_4(str);
          stop = System.currentTimeMillis();
          long partition_into_palindromes_execution_time_4 = stop - start;

          System.out.println("Min number of cuts required to partition string into palindromic components:");
          // System.out.println("METHOD 1: " + min_cut_1 + ", execution time = " + partition_into_palindromes_execution_time_1);
          System.out.println("METHOD 2: " + min_cut_2 + ", execution time = " + partition_into_palindromes_execution_time_2);
          System.out.println("METHOD 3: " + min_cut_3 + ", execution time = " + partition_into_palindromes_execution_time_3);
          // System.out.println("METHOD 4: " + min_cut_4 + ", execution time = " + partition_into_palindromes_execution_time_4);

          // if (min_cut_1 != min_cut_2) {
          //   System.out.println("find_min_cut_to_partition_into_palindromes_1 != find_min_cut_to_partition_into_palindromes_2");
          //   break;
          // }

          if (min_cut_2 != min_cut_3) {
            System.out.println("find_min_cut_to_partition_into_palindromes_1 != find_min_cut_to_partition_into_palindromes_3");
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

  private static int find_min_cut_to_partition_into_palindromes_1(String str) {
    // System.out.println("str = " + str);
    if (is_palindrome(str)) {
      return 0;
    }

    int min_num_cut = str.length() - 1;
    for (int i = 1; i < str.length(); i++) {
      min_num_cut = Math.min(min_num_cut, 1 + 
        find_min_cut_to_partition_into_palindromes_1(str.substring(0, i)) + 
        find_min_cut_to_partition_into_palindromes_1(str.substring(i)));
    }
    return min_num_cut;
  }

  private static int find_min_cut_to_partition_into_palindromes_2(String str) {
    if (is_palindrome(str)) {
      return 0;
    }

    boolean[][] palindrome_map = new boolean[str.length()][str.length()];
    int[][] min_num_cut = new int[str.length()][str.length()];

    for (int len = 1; len <= str.length(); len++) {
      for (int start = 0; start <= str.length() - len; start++) {
        int end = start + len - 1;
        if (str.charAt(start) == str.charAt(end) && (len <= 2 || palindrome_map[start+1][end-1])) {
          palindrome_map[start][end] = true;
        } else {
          min_num_cut[start][end] = len - 1;
          for (int j = start; j < end; j++) {
            min_num_cut[start][end] = Math.min(min_num_cut[start][end], 1 + min_num_cut[start][j] + min_num_cut[j+1][end]);
          }
        }
      }
    }
    return min_num_cut[0][str.length() - 1];
  }

  private static int find_min_cut_to_partition_into_palindromes_3(String str) {
    if (is_palindrome(str)) {
      return 0;
    }
    boolean[][] palindrome_map = new boolean[str.length()][str.length()];
    int[] min_cut = new int[str.length()];

    for (int end = 0; end < str.length(); end++) {
      min_cut[end] = end;
      for (int start = 0; start <= end; start++) {
        if (str.charAt(start) == str.charAt(end) && (end - start + 1 <= 2 || palindrome_map[start+1][end-1])) {
          palindrome_map[start][end] = true;
          if (start == 0) {
            min_cut[end] = 0;
          } else {
            min_cut[end] = Math.min(min_cut[end], min_cut[start - 1] + 1);
          }
        }
      }
    }

    return min_cut[str.length() - 1];
  }

  private static int find_min_cut_to_partition_into_palindromes_4(String str) {
    if (is_palindrome(str)) {
      return 0;
    }

    boolean[][] palindrome_map = new boolean[str.length()][str.length()];
    int[] min_cut = new int[str.length()];
    for (int end = str.length() - 1; end >= 0; end--) {
      min_cut[end] = str.length() - 1 - end;
      for (int start = end; start < str.length() - 1; start++) {
        if (str.charAt(start) == str.charAt(end) && (end - start + 1 <= 2 || palindrome_map[start+1][end-1])) {
          palindrome_map[start][end] = true;
          if (start == end) {
            min_cut[end] = 0;
          } else {
            min_cut[end] = Math.min(min_cut[end], min_cut[start + 1] + 1);
          }
        }
      }
    }
    return min_cut[0];
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