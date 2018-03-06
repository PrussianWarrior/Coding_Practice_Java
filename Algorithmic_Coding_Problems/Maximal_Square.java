import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem Description:
  Given an array of positive numbers, find the maximum sum of a subsequence with the constraint 
  that no 2 numbers in the sequence should be adjacent in the array. So 3 2 7 10 should return 13 
  (sum of 3 and 10) or 3 2 5 10 7 should return 15 (sum of 3, 5 and 7).Answer the question in most 
  efficient way.
*/

public class Maximal_Square {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          int[][] matrix = null;
          if (line.indexOf("Customized:") >= 0) {
            int index_of_colon = line.indexOf(":");
            line = line.substring(index_of_colon + 1).trim();
            String[] integer_strings = line.split(" ");
            int num_rows = Integer.parseInt(integer_strings[0].trim());
            int num_cols = Integer.parseInt(integer_strings[1].trim());

            List<String> list = new ArrayList<>();
            line = br.readLine();
            while (!line.startsWith("*******")) {
              list.add(line);
              line = br.readLine();
            }
            matrix = get_customized_input(list, num_rows, num_cols);
          } else if (line.indexOf("Randomized:") >= 0) {
            matrix = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_matrix(matrix);
        
          long start = System.currentTimeMillis();
          int area_largest_square_1 = find_area_of_largest_square_1(matrix);
          long end = System.currentTimeMillis();
          long execution_time_1 = end - start;

          System.out.println("Area of max square:");
          System.out.println("SOLUTION 1: " + area_largest_square_1 + ", execution time = " + 
              execution_time_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_area_of_largest_square_1(int[][] matrix) {
    int num_rows = matrix.length;
    int num_cols = matrix[0].length;
    int[][] max_square_at_position = new int[num_rows][num_cols];
    int max_side = 0;

    for (int r = 0; r < num_rows; r++) {
      for (int c = 0; c < num_cols; c++) {
        if (r == 0 || c == 0) {
          max_square_at_position[r][c] = matrix[r][c];  
        } else {
          max_square_at_position[r][c] = matrix[r][c] == 0 ? 0 : 
            Math.min(max_square_at_position[r - 1][c], 
              Math.min(max_square_at_position[r][c - 1], max_square_at_position[r - 1][c - 1])) + 1;
        }
        max_side = Math.max(max_side, max_square_at_position[r][c]);
      }
    }

    // System.out.println("max_square_at_position:");
    // print_matrix(max_square_at_position);
    return max_side * max_side;
  }

  private static int[][] get_customized_input(List<String> list, int num_rows, int num_cols) {
    int[][] matrix = new int[num_rows][num_cols];
    for (int r = 0; r < num_rows; r++) {
      String[] temp = list.get(r).trim().split(" ");
      if (temp.length != num_cols) {
        throw new IllegalArgumentException("There is a mismatch between the number of columns in the " +
          "string array and the input number of columns");
      }
      for (int c = 0; c < num_cols; c++) {
        matrix[r][c] = Integer.parseInt(temp[c].trim());
      }
    }
    return matrix;
  }

  private static int[][] get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] row_col_num_zeros = line.split(" ");
    int num_rows  = Integer.parseInt(row_col_num_zeros[0].trim());
    int num_cols  = Integer.parseInt(row_col_num_zeros[1].trim());
    int num_zeros = Integer.parseInt(row_col_num_zeros[2].trim());

    int[][] matrix = new int[num_rows][num_cols];
    for (int r = 0; r < num_rows; r++) {
      Arrays.fill(matrix[r], 1);
    }

    Set<Integer> taken_cells = new HashSet<>();
    for (int i = 1; i <= num_zeros; i++) {
      int random_cell = (int)(Math.random() * (num_rows * num_cols));
      while (taken_cells.contains(random_cell)) {
        random_cell = (int)(Math.random() * (num_rows * num_cols));        
      }
      int r = random_cell / num_cols;
      int c = random_cell % num_cols;
      matrix[r][c] = 0;
    }

    return matrix;
  }

  private static void print_matrix(int[][] matrix) {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.printf("%5d", matrix[r][c]);
      }
      System.out.println();
    }
    System.out.println();
  }
}