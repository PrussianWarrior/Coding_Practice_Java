import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_44_Unique_Paths_II {
  private static class Matrix_Dimensions {
    int R;
    int C;
    String[] lines;

    public Matrix_Dimensions(int R, int C, String[] lines) {
      this.R = R;
      this.C = C;
      this.lines = lines;
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
          if (line.isEmpty()) {
            line = br.readLine();
            continue;
          }
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          
          String[] row_col = line.trim().split(" ");
          int R = Integer.parseInt(row_col[0].trim());
          int C = Integer.parseInt(row_col[1].trim());

          String[] lines = new String[R];
          for (int i = 0; i < R; i++) {
            lines[i] = br.readLine();
          }

          Matrix_Dimensions data = new Matrix_Dimensions(R, C, lines);
          int[][] matrix = process_input(data);

          long number_of_unique_path_soln_1 = count_unique_paths_1(matrix, R, C);
          long number_of_unique_path_soln_2 = count_unique_paths_2(matrix, R, C);
          long number_of_unique_path_soln_3 = count_unique_paths_3(matrix, R, C);
          
          System.out.println("NUMBER OF UNIQUE PATHS");
          System.out.println("Solution 1 = " + number_of_unique_path_soln_1);
          System.out.println("Solution 2 = " + number_of_unique_path_soln_2);
          System.out.println("Solution 3 = " + number_of_unique_path_soln_3);
          
          if (number_of_unique_path_soln_1 != number_of_unique_path_soln_2 || 
              number_of_unique_path_soln_1 != number_of_unique_path_soln_3) {
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

  private static long count_unique_paths_1(int[][] matrix, int r, int c) {
    if (r < 1 || c < 1 || matrix[r - 1][c - 1] == 1) {
      return 0;
    }
    if (r == 1 && c == 1) {
      return 1;
    }
    return count_unique_paths_1(matrix, r - 1, c) + count_unique_paths_1(matrix, r, c - 1);
  }

  private static long count_unique_paths_2(int[][] matrix, int r, int c) {
    long[][] cache = new long[r + 1][c + 1];
    return count_unique_paths_2(matrix, r, c, cache);
  }

  private static long count_unique_paths_2(int[][] matrix, int r, int c, long[][] cache) {
    // System.out.println("m = " + m + ", n = " + n);
    if (r < 1 || c < 1 || matrix[r - 1][c - 1] == 1) {
      return 0;
    }
    if (r == 1 && c == 1) {
      return 1;
    }
    if (cache[r][c] > 0) {
      // System.out.println("Returning cached result for m = " + m + ", n = " + n);
      return cache[r][c];
    }
    long result = count_unique_paths_2(matrix, r - 1, c, cache) + 
                  count_unique_paths_2(matrix, r, c - 1, cache);
    cache[r][c] = result;
    return result;
  }

  private static long count_unique_paths_3(int[][] matrix, int r, int c) {
    long[][] soln_sub_problem = new long[r][c];
    soln_sub_problem[0][0] = matrix[0][0] == 1 ? 0 : 1;
    for (int i = 1; i < r; i++) {
      soln_sub_problem[i][0] = matrix[i][0] == 1 ? 0 : soln_sub_problem[i - 1][0];
    }
    for (int i = 1; i < c; i++) {
      soln_sub_problem[0][i] = matrix[0][i] == 1 ? 0 : soln_sub_problem[0][i - 1];
    }

    for (int i = 1; i < r; i++) {
      for (int j = 1; j < c; j++) {
        soln_sub_problem[i][j] = matrix[i][j] == 1 ? 0 : 
            soln_sub_problem[i - 1][j] + soln_sub_problem[i][j - 1];
      }
    }
    return soln_sub_problem[r - 1][c - 1];
  }

  private static int[][] process_input(Matrix_Dimensions data) {
    int[][] matrix = new int[data.R][data.C];
    for (int i = 0; i < data.R; i++) {
      String[] digits = data.lines[i].trim().split(" ");
      for (int j = 0; j < data.C; j++) {
        matrix[i][j] = Integer.parseInt(digits[j].trim());
      }
    }
    return matrix;
  }
}




