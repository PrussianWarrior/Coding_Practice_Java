import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an unsorted array of integers, find the length of longest increasing subsequence.

  For example,
  Given [10, 9, 2, 5, 3, 7, 101, 18],
   
  The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that 
  there may be more than one LIS combination, it is only necessary for you to return the length.
*/

public class Longest_Increasing_Subsequence {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> seq = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            seq = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            seq = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(seq);

          long start = System.currentTimeMillis();
          List<Integer> longest_increasing_subseq_1 = find_longest_increasing_subseq_1(seq);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          List<Integer> longest_increasing_subseq_2 = find_longest_increasing_subseq_2(seq);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int[] longest_increasing_subseq_3 = find_longest_increasing_subseq_3(seq);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          start = System.currentTimeMillis();
          int[] longest_increasing_subseq_4 = find_longest_increasing_subseq_3(seq);
          stop = System.currentTimeMillis();
          long execution_time_soln_4 = stop - start;

          System.out.println("Longest increasing subsequence:");
          System.out.println("SOLUTION 1: executime time = " + execution_time_soln_1);
          print_list(longest_increasing_subseq_1);
          System.out.println();

          System.out.println("SOLUTION 2: executime time = " + execution_time_soln_2);
          print_list(longest_increasing_subseq_2);
          System.out.println();

          System.out.println("SOLUTION 3: executime time = " + execution_time_soln_3);
          print_arr(longest_increasing_subseq_3);
          System.out.println();

          System.out.println("SOLUTION 4: executime time = " + execution_time_soln_4);
          print_arr(longest_increasing_subseq_4);
          System.out.println();

          if (longest_increasing_subseq_1.size() != longest_increasing_subseq_2.size() ||
              longest_increasing_subseq_1.size() != longest_increasing_subseq_3.length ||
              longest_increasing_subseq_1.size() != longest_increasing_subseq_4.length) {
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

  private static List<Integer> find_longest_increasing_subseq_1(List<Integer> sequence) {
    List<List<Integer>> longest_increasing_subseq_at_index = new ArrayList<>();

    for (int end_index = 0; end_index < sequence.size(); end_index++) {
      List<Integer> longest_increasing_subseq_prec = new ArrayList<>();
      for (int i = 0; i < end_index; i++) {
        List<Integer> subseq = longest_increasing_subseq_at_index.get(i);
        if (subseq.get(subseq.size() - 1) < sequence.get(end_index) &&
            subseq.size() > longest_increasing_subseq_prec.size()) {
          longest_increasing_subseq_prec = subseq;
        }
      }

      List<Integer> longest_subseq = new ArrayList<>(longest_increasing_subseq_prec);
      longest_subseq.add(sequence.get(end_index));
      longest_increasing_subseq_at_index.add(longest_subseq);
    }

    List<Integer> longest_increasing_subseq = new ArrayList<>();
    for (List<Integer> subseq : longest_increasing_subseq_at_index) {
      if (subseq.size() > longest_increasing_subseq.size()) {
        longest_increasing_subseq = subseq;
      }
    }
    return longest_increasing_subseq;
  }

  private static List<Integer> find_longest_increasing_subseq_2(List<Integer> sequence) {
    List<List<Integer>> all_subseq = new ArrayList<>();
    find_longest_increasing_subseq_2_helper(sequence, 0, all_subseq);

    List<Integer> longest_increasing_subseq = new ArrayList<>();
    for (List<Integer> subseq : all_subseq) {
      if (longest_increasing_subseq.size() < subseq.size()) {
        longest_increasing_subseq = subseq;
      }
    }
    return longest_increasing_subseq;
  }

  private static void find_longest_increasing_subseq_2_helper(List<Integer> sequence, int end_index,
      List<List<Integer>> all_subseq) {
    if (end_index >= sequence.size()) {
      return;
    }

    List<Integer> longest_increasing_subseq_prec = new ArrayList<>();
    for (int i = 0; i < end_index; i++) {
      List<Integer> subseq = all_subseq.get(i);
      if (subseq.get(subseq.size() - 1) < sequence.get(end_index) &&
          subseq.size() > longest_increasing_subseq_prec.size()) {
        longest_increasing_subseq_prec = subseq;
      }
    }

    List<Integer> new_seq = new ArrayList<>(longest_increasing_subseq_prec);
    new_seq.add(sequence.get(end_index));
    all_subseq.add(new_seq);
    find_longest_increasing_subseq_2_helper(sequence, end_index + 1, all_subseq);
  }

  private static int[] find_longest_increasing_subseq_3(List<Integer> sequence) {
    int[] M = new int[sequence.size() + 1];
    int[] P = new int[sequence.size()];

    int max_len = 0;
    for (int i = 0; i < sequence.size(); i++) {
      int start = 1;
      int end = max_len;
      while (start <= end) {
        int mid = start + (end - start)/2;
        if (sequence.get(M[mid]) < sequence.get(i)) {
          start = mid + 1;
        } else {
          end = mid - 1;
        }
      }

      int new_len = start;
      P[i] = M[new_len - 1];
      M[new_len] = i;
      max_len = Math.max(new_len, max_len);
    }

    int[] longest_increasing_subseq = new int[max_len];
    int i = M[max_len];
    for (int j = max_len - 1; j >= 0; j--) {
      longest_increasing_subseq[j] = sequence.get(i);
      i = P[i];
    }

    return longest_increasing_subseq;
  }

  private static int[] find_longest_increasing_subseq_4(List<Integer> sequence) {
    List<Integer> sorted_seq = new ArrayList<>(sequence);
    Collections.sort(sorted_seq);

    int[][] longest_common_subseq = new int[sequence.size() + 1][sequence.size() + 1];

    for (int i_1 = 1; i_1 <= sequence.size(); i_1++) {
      for (int i_2 = 1; i_2 <= sequence.size(); i_2++) {
        longest_common_subseq[i_1][i_2] = sequence.get(i_1 - 1) == sorted_seq.get(i_2 - 1) ?
          longest_common_subseq[i_1 - 1][i_2 - 1] + 1 :
          Math.max(longest_common_subseq[i_1 - 1][i_2], longest_common_subseq[i_1][i_2 - 1]);
      }
    }

    int max_len = longest_common_subseq[sequence.size()][sequence.size()];
    int[] longest_increasing_subseq = new int[max_len];
    int i = max_len - 1;
    int i_1 = sequence.size();
    int i_2 = sequence.size();

    while (i >= 0 && i_1 > 0 && i_2 > 0) {
      if (sequence.get(i_1 - 1) == sorted_seq.get(i_2 - 1)) {
        longest_increasing_subseq[i--] = sequence.get(i_1 - 1);
        i_1--;
        i_2--;
      } else {
        if (longest_common_subseq[i_1][i_2] == longest_common_subseq[i_1 - 1][i_2]) {
          i_1--;
        } else if (longest_common_subseq[i_1][i_2] == longest_common_subseq[i_1][i_2 - 1]) {
          i_2--;
        }
      }
    }
    return longest_increasing_subseq;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] integers_str = line.split(" ");

    List<Integer> seq = new ArrayList<>();
    for (String int_str : integers_str) {
      seq.add(Integer.parseInt(int_str));
    }
    return seq;
  }

  private static List<Integer> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));

    String[] min_max_len = line.split(" ");
    int min = Integer.parseInt(min_max_len[0]);
    int max = Integer.parseInt(min_max_len[1]);
    int len = Integer.parseInt(min_max_len[2]);

    List<Integer> seq = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      int random_data = (int)(Math.random() * (max - min + 1)) + min;
      seq.add(random_data);
    }
    return seq;
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
      System.out.printf("%5d : %d\n", i, list.get(i));
    }
    System.out.println();
  }

  private static void print_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%5d : %d\n", i, arr[i]);
    }
    System.out.println(); 
  }
}