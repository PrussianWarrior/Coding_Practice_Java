import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_45_Maximum_Sum_Subarray {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          if (line.isEmpty()) {
            line = br.readLine();
            continue;
          }
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          
          List<Integer> len_min_max = process_input(line);
          List<Integer> input = generate_random_int_list(len_min_max.get(0), len_min_max.get(1), len_min_max.get(2));
          print(input);

          int[] max_sum_subarr_soln_1 = find_max_sum_subarray_1(input);
          int[] max_sum_subarr_soln_2 = find_max_sum_subarray_2(input);
          int[] max_sum_subarr_soln_3 = find_max_sum_subarray_3(input);

          System.out.println("MAX SUM SUBARRAY:");
          System.out.println("SOLUTION 1: Max sum = " + max_sum_subarr_soln_1[0] 
              + ", start = " + max_sum_subarr_soln_1[1]
              + ", end = " + max_sum_subarr_soln_1[2]);

          System.out.println("SOLUTION 2: Max sum = " + max_sum_subarr_soln_2[0] 
              + ", start = " + max_sum_subarr_soln_2[1]
              + ", end = " + max_sum_subarr_soln_2[2]);

          System.out.println("SOLUTION 3: Max sum = " + max_sum_subarr_soln_3[0] 
              + ", start = " + max_sum_subarr_soln_3[1]
              + ", end = " + max_sum_subarr_soln_3[2]);

          if (max_sum_subarr_soln_1[0] != max_sum_subarr_soln_2[0] || 
              max_sum_subarr_soln_1[1] != max_sum_subarr_soln_2[1] ||
              max_sum_subarr_soln_1[2] != max_sum_subarr_soln_2[2] ||
              max_sum_subarr_soln_1[0] != max_sum_subarr_soln_3[0] || 
              max_sum_subarr_soln_1[1] != max_sum_subarr_soln_3[1] ||
              max_sum_subarr_soln_1[2] != max_sum_subarr_soln_3[2]) {
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

  private static int[] find_max_sum_subarray_1(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }

    int start = 0;
    int end = 0;
    int max_sum = Integer.MIN_VALUE;
    for (int i_1 = 0; i_1 < list.size(); i_1++) {
      int sum = list.get(i_1);
      for (int i_2 = i_1 + 1; i_2 < list.size(); i_2++) {
        sum += list.get(i_2);
        if (sum > max_sum) {
          start = i_1;
          end = i_2;
          max_sum = sum;
        }
      }
    }
    return new int[]{max_sum, start, end};
  }

  /*
    Correct but unnecessarily complicated
  */
  private static int[] find_max_sum_subarray_2(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }

    int running_sum = list.get(0);
    int running_sum_len = 1;

    int max_sum_local = list.get(0);
    int max_sum_local_len = 1;

    int max_sum_global = list.get(0);
    int max_sum_global_len = 1;

    int end = 0;

    for (int i = 1; i < list.size(); i++) {
      running_sum += list.get(i);
      running_sum_len++;

      max_sum_local += list.get(i);
      max_sum_local_len++;

      max_sum_local_len = list.get(i) >= max_sum_local ? 1 : max_sum_local_len;
      max_sum_local = list.get(i) >= max_sum_local ? list.get(i) : max_sum_local;

      max_sum_local_len = running_sum > max_sum_local ? running_sum_len : max_sum_local_len;
      max_sum_local = running_sum > max_sum_local ? running_sum : max_sum_local;

      if (max_sum_local > max_sum_global) {
        max_sum_global = max_sum_local;
        max_sum_global_len = max_sum_local_len;
        end = i;
      }
    }

    return new int[]{max_sum_global, end - max_sum_global_len + 1, end};
  }

  private static int[] find_max_sum_subarray_3(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }

    int max_sum_local = list.get(0);
    int max_sum_local_len = 1;

    int max_sum_global = list.get(0);
    int max_sum_global_len = 1;
    int end = 0;
    
    for (int i = 1; i < list.size(); i++) {
      max_sum_local += list.get(i);
      
      max_sum_local_len = list.get(i) > max_sum_local ? 1 : max_sum_local_len + 1;
      max_sum_local = list.get(i) > max_sum_local ? list.get(i) : max_sum_local;

      if (max_sum_local > max_sum_global) {
        max_sum_global = max_sum_local;
        max_sum_global_len = max_sum_local_len;
        end = i;
      }
    }
    
    return new int[]{max_sum_global, end - max_sum_global_len + 1, end};
  }

  private static List<Integer> process_input(String line) {
    String[] tokens = line.trim().split(" ");
    List<Integer> list = new ArrayList<>();
    for (String token : tokens) {
      list.add(Integer.parseInt(token.trim()));
    }
    return list;
  }

  private static List<Integer> generate_random_int_list(int len, int min, int max) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      list.add((int)(Math.random() * (max - min + 1)) + min);
    }
    return list;
  }

  private static void print(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d (%5d)", list.get(i), i);
      if ((i + 1) % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
  }
}