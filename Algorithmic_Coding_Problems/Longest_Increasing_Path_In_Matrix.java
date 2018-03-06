import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem Description:
  Given an integer matrix, find the length of the longest increasing path.

  From each cell, you can either move to four directions: left, right, up or down. 
  You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).
  Example 1:
  nums = [
    [9,9,4],
    [6,6,8],
    [2,1,1]
  ]


  Return 4
  The longest increasing path is [1, 2, 6, 9].
  Example 2:
  nums = [
    [3,4,5],
    [3,2,6],
    [2,2,1]
  ]
  Return 4
  The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

*/

public class Longest_Increasing_Path_In_Matrix {
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
            while (!line.startsWith("**")) {
              list.add(line);
              line = br.readLine();
            }
            matrix = get_customized_input(list, num_rows, num_cols);
          } else if (line.indexOf("Randomized:") >= 0) {
            
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_matrix(matrix);
        
          long start = System.currentTimeMillis();
          int length_longest_increasing_path_1 = find_longest_increasing_path_matrix_1(matrix);
          long end = System.currentTimeMillis();
          long execution_time_1 = end - start;

          start = System.currentTimeMillis();
          int length_longest_increasing_path_2 = find_longest_increasing_path_matrix_2(matrix);
          end = System.currentTimeMillis();
          long execution_time_2 = end - start;

          start = System.currentTimeMillis();
          int length_longest_increasing_path_3 = find_longest_increasing_path_matrix_3(matrix);
          end = System.currentTimeMillis();
          long execution_time_3 = end - start;

          start = System.currentTimeMillis();
          int length_longest_increasing_path_4 = find_longest_increasing_path_matrix_4(matrix);
          end = System.currentTimeMillis();
          long execution_time_4 = end - start;

          System.out.println("Length of longest increasing path in matrix:");
          System.out.println("SOLUTION 1 = " + length_longest_increasing_path_1 + ", execution time = " + 
              execution_time_1);
          System.out.println("SOLUTION 2 = " + length_longest_increasing_path_2 + ", execution time = " + 
              execution_time_2);
          System.out.println("SOLUTION 3 = " + length_longest_increasing_path_3 + ", execution time = " + 
              execution_time_3);
          System.out.println("SOLUTION 4 = " + length_longest_increasing_path_4 + ", execution time = " + 
              execution_time_4);

          if (length_longest_increasing_path_1 != length_longest_increasing_path_2 ||
              length_longest_increasing_path_1 != length_longest_increasing_path_3) {
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

  private static int find_longest_increasing_path_matrix_1(int[][] matrix) {
    int max_length = 0;
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        max_length = Math.max(max_length, 
          find_longest_increasing_path_matrix_1(matrix, r, c));
      }
    }
    return max_length;
  }

  private static int find_longest_increasing_path_matrix_1(int[][] matrix, int start_x, int start_y) {
    int[] x_move = {-1, 1,  0, 0};
    int[] y_move = { 0, 0, -1, 1};
    int max_length = 1;

    for (int i = 0; i < x_move.length; i++) {
      int new_x = start_x + x_move[i];
      int new_y = start_y + y_move[i];
      if (new_x >= 0 && new_x < matrix.length &&
          new_y >= 0 && new_y < matrix[0].length &&
          matrix[start_x][start_y] < matrix[new_x][new_y]) {
        max_length = Math.max(max_length, find_longest_increasing_path_matrix_1(matrix, new_x, new_y) + 1);
      }
    }
    return max_length;
  }

  private static int find_longest_increasing_path_matrix_2(int[][] matrix) {
    int max_length = 0;
    int[][] cache = new int[matrix.length][matrix[0].length];
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        max_length = Math.max(max_length, find_longest_increasing_path_matrix_2(matrix, r, c, cache));
      }
    }
    return max_length;
  }

  private static int find_longest_increasing_path_matrix_2(int[][] matrix, int start_x, int start_y, 
      int[][] cache) {
    if (cache[start_x][start_y] > 0) {
      // System.out.println("Returning cached result for start_x = " + start_x + ", start_y = " + start_y);
      return cache[start_x][start_y];
    }

    int max_length = 1;
    int[] x_move = {-1, 1, 0, 0};
    int[] y_move = { 0, 0, -1, 1};

    for (int i = 0; i < x_move.length; i++) {
      int new_x = start_x + x_move[i];
      int new_y = start_y + y_move[i];

      if (new_x >= 0 && new_x < matrix.length &&
          new_y >= 0 && new_y < matrix[0].length && 
          matrix[new_x][new_y] > matrix[start_x][start_y]) {
        max_length = Math.max(max_length, find_longest_increasing_path_matrix_2(matrix, new_x, new_y, cache) + 1);
      }
    }

    cache[start_x][start_y] = max_length;
    return max_length;
  }

  private static int find_longest_increasing_path_matrix_3(int[][] matrix) {
    int[] max_length = {0};
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        find_longest_increasing_path_matrix_3(matrix, r, c, max_length, 1);
      }
    }
    return max_length[0];
  }

  private static void find_longest_increasing_path_matrix_3(int[][] matrix, int start_x, int start_y, 
      int[] max_length, int length) {
    max_length[0] = Math.max(max_length[0], length);

    int[] x_move = {-1, 1,  0, 0};
    int[] y_move = { 0, 0, -1, 1};

    for (int i = 0; i < x_move.length; i++) {
      int new_x = start_x + x_move[i];
      int new_y = start_y + y_move[i];

      if (new_x >= 0 && new_x < matrix.length && 
          new_y >= 0 && new_y < matrix[0].length &&
          matrix[new_x][new_y] > matrix[start_x][start_y]) {
        find_longest_increasing_path_matrix_3(matrix, new_x, new_y, max_length, length + 1);
      }
    }
  }

  private static int find_longest_increasing_path_matrix_4(int[][] matrix) {
    int[][] cache = new int[matrix.length][matrix[0].length];
    int max_length = 0;
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        max_length = Math.max(max_length, find_longest_increasing_path_matrix_4(matrix, r, c, cache));
      }
    }
    return max_length;
  }

  private static int find_longest_increasing_path_matrix_4(int[][] matrix, int start_x, int start_y, int[][] cache) {
    if (cache[start_x][start_y] > 0) {
      return cache[start_x][start_y];
    }

    int[] x_move = {-1, 1,  0, 0};
    int[] y_move = { 0, 0, -1, 1};

    for (int i = 0; i < x_move.length; i++) {
      int new_x = start_x + x_move[i];
      int new_y = start_y + y_move[i];

      if (new_x >= 0 && new_x < matrix.length && 
          new_y >= 0 && new_y < matrix[0].length &&
          matrix[new_x][new_y] > matrix[start_x][start_y]) {
        cache[start_x][start_y] = Math.max(cache[start_x][start_y], 
          find_longest_increasing_path_matrix_4(matrix, new_x, new_y, cache));
      } 
    }
    return ++cache[start_x][start_y];
  }

  private static int[][] get_customized_input(List<String> list, int num_rows, int num_cols) {
    int[][] matrix = new int[num_rows][num_cols];
    for (int r = 0; r < num_rows; r++) {
      String row = remove_extra_spaces_in_string_1(list.get(r));
      String[] temp = row.split(" ");
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

  private static void print_matrix(int[][] matrix) {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.printf("%5d", matrix[r][c]);
      }
      System.out.println();
    }
    System.out.println();
  }

  private static String remove_extra_spaces_in_string_1(String str) {
    str = str.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || Character.isSpace(str.charAt(i + 1))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i != str.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }
}