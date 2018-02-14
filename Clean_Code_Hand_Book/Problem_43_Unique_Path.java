import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_43_Unique_Path {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          int[] dimensions = process_input(line.trim());

          long number_of_unique_path_soln_1 = count_unique_paths_1(dimensions[0], dimensions[1]);
          long number_of_unique_path_soln_2 = count_unique_paths_2(dimensions[0], dimensions[1]);
          long number_of_unique_path_soln_3 = count_unique_paths_3(dimensions[0], dimensions[1]);
          long number_of_unique_path_soln_4 = count_unique_paths_4(dimensions[0], dimensions[1]);
          
          System.out.println("NUMBER OF UNIQUE PATHS");
          System.out.println("Solution 1 = " + number_of_unique_path_soln_1);
          System.out.println("Solution 2 = " + number_of_unique_path_soln_2);
          System.out.println("Solution 3 = " + number_of_unique_path_soln_3);
          System.out.println("Solution 4 = " + number_of_unique_path_soln_4);
          
          if (number_of_unique_path_soln_1 != number_of_unique_path_soln_2 || 
              number_of_unique_path_soln_1 != number_of_unique_path_soln_3 || 
              number_of_unique_path_soln_1 != number_of_unique_path_soln_4) {
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

  private static long count_unique_paths_1(int m, int n) {
    if (m < 1 || n < 1) {
      return 0;
    }
    if (m == 1 && n == 1) {
      return 1;
    }
    return count_unique_paths_1(m - 1, n) + count_unique_paths_1(m, n - 1);
  }

  private static long count_unique_paths_2(int m, int n) {
    long[][] cache = new long[m + 1][n + 1];
    return count_unique_paths_2(m, n, cache);
  }

  private static long count_unique_paths_2(int m, int n, long[][] cache) {
    // System.out.println("m = " + m + ", n = " + n);
    if (m < 1 || n < 1) {
      return 0;
    }
    if (m == 1 && n == 1) {
      return 1;
    }
    if (cache[m][n] > 0) {
      // System.out.println("Returning cached result for m = " + m + ", n = " + n);
      return cache[m][n];
    }
    long result = count_unique_paths_2(m - 1, n, cache) + count_unique_paths_2(m, n - 1, cache);
    cache[m][n] = result;
    return result;
  }

  private static long count_unique_paths_3(int m, int n) {
    return compute_combinations_1(m + n - 2, m - 1);
  }

  private static long compute_combinations_1(int n, int k) {
    if (n < k) {
      throw new IllegalArgumentException("ERROR. k must not be smaller than n");
    }
    if (k == 0 || k == n) {
      return 1;
    }
    if (k == 1 || k == n - 1) {
      return n;
    }
    long[][] smaller_combination = new long[k + 1][n + 1];
    for (int i = 0; i <= n; i++) {
      smaller_combination[0][i] = 1;
      smaller_combination[1][i] = i;
    }

    for (int i_k = 2; i_k <= k; i_k++) {
      for (int i_n = 2; i_n <= n; i_n++) {
        smaller_combination[i_k][i_n] = smaller_combination[i_k][i_n - 1] + smaller_combination[i_k - 1][i_n - 1];
      }
    }
    print(smaller_combination);

    return smaller_combination[k][n];
  }

  private static long count_unique_paths_4(int m, int n) {
    long[][] soln_sub_problem = new long[m][n];

    for (int r = 0; r < m; r++) {
      for (int c = 0; c < n; c++) {
        if (r == 0 || c == 0) {
          soln_sub_problem[r][c] = 1;
        } else {
          soln_sub_problem[r][c] = soln_sub_problem[r - 1][c] + soln_sub_problem[r][c - 1];
        }
      }
    }
    return soln_sub_problem[m - 1][n - 1];
  }

  private static int[] process_input(String line) {
    String[] tokens = line.trim().split(" ");
    return new int[]{Integer.parseInt(tokens[0].trim()), Integer.parseInt(tokens[1].trim())};
  }

  private static void print(long[][] matrix) {
    System.out.printf("%5s", " ");
    for (int c = 0; c < matrix[0].length; c++) {
      System.out.printf("%5d", c);
    }
    System.out.println();
    for (int r = 0; r < matrix.length; r++) {
      System.out.printf("%5d", r);
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.printf("%5d", matrix[r][c]);
      }
      System.out.println();
    }
    System.out.println();
  }
}




