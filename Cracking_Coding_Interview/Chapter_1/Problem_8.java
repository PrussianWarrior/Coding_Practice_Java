import java.util.*;

public class Problem_8 {
  public static void main(String[] args) {
    int num_row = Integer.parseInt(args[0]);
    int num_col = Integer.parseInt(args[1]);
    int num_of_zero = Integer.parseInt(args[2]);

    int[][] matrix = generate_matrix(num_row, num_col, num_of_zero);
    int[][] copy_matrix = copy_matrix(matrix);

    System.out.println("Before zeroing");
    print_matrix(matrix);
    zeroing_1(matrix);
    zeroing_2(copy_matrix);
    System.out.println("After zeroing");
    System.out.println("Method 1");
    print_matrix(matrix);
    System.out.println();
    System.out.println("Method 2");
    print_matrix(copy_matrix);

    System.out.println("Method 1 yields the same result as method 2 = " + 
      are_matrices_identical(matrix, copy_matrix));
  }

  private static void zeroing_1(int[][] matrix) {
    int num_row = matrix.length;
    int num_col = matrix[0].length;

    boolean[] zero_in_row = new boolean[num_row];
    boolean[] zero_in_col = new boolean[num_col];

    for (int r = 0; r < num_row; r++) {
      for (int c = 0; c < num_col; c++) {
        if (matrix[r][c] == 0) {
          zero_in_row[r] = true;
          zero_in_col[c] = true;
        }
      }
    }

    for (int r = 0; r < num_row; r++) {
      if (zero_in_row[r]) {
        fill_row_with_zeros(matrix, r);
      }
    }

    for (int c = 0; c < num_col; c++) {
      if (zero_in_col[c]) {
        fill_col_with_zeros(matrix, c);
      }
    }
  }

  private static void zeroing_2(int[][] matrix) {
    int num_row = matrix.length;
    int num_col = matrix[0].length;
    int row_bit = 0;
    int col_bit = 0;

    for (int r = 0; r < num_row; r++) {
      for (int c = 0; c < num_col; c++) {
        if (matrix[r][c] == 0) {
          row_bit |= (1 << r);
          col_bit |= (1 << c);
        }
      }
    }

    for (int r = 0; r < num_row; r++) {
      if ((row_bit & (1 << r)) != 0) {
        fill_row_with_zeros(matrix, r);
      }
    }

    for (int c = 0; c < num_col; c++) {
      if ((col_bit & (1 << c)) != 0) {
        fill_col_with_zeros(matrix, c);
      }
    }
  }

  private static int[][] generate_matrix(int num_row, int num_col, int num_of_zero) {
    int[][] matrix = new int[num_row][num_col];
    for (int r = 0; r < num_row; r++) {
      Arrays.fill(matrix[r], 1);
    }
    boolean[] already_used_slot = new boolean[num_col * num_row];
    for (int i = 1; i <= num_of_zero; i++) {
      int slot = (int)(Math.random() * (num_col * num_row));
      while (already_used_slot[slot]) {
        slot = (int)(Math.random() * (num_col * num_row));
      }
      matrix[slot/num_col][slot%num_col] = 0;
      already_used_slot[slot] = true;
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
  }

  private static void fill_row_with_zeros(int[][] matrix, int r) {
    for (int c = 0; c < matrix[0].length; c++) {
      matrix[r][c] = 0;
    }
  }

  private static void fill_col_with_zeros(int[][] matrix, int c) {
    for (int r = 0; r < matrix.length; r++) {
      matrix[r][c] = 0;
    }
  }

  private static int[][] copy_matrix(int[][] matrix) {
    int num_row = matrix.length;
    int num_col = matrix[0].length;

    int[][] copy_matrix = new int[num_row][num_col];
    for (int r = 0; r < num_row; r++) {
      for (int c = 0; c < num_col; c++) {
        copy_matrix[r][c] = matrix[r][c];
      }
    }
    return copy_matrix;
  }

  private static boolean are_matrices_identical(int[][] matrix_1, int[][] matrix_2) {
    if (matrix_1.length != matrix_2.length || matrix_1[0].length != matrix_2[0].length) {
      return false;
    }
    for (int r = 0; r < matrix_1.length; r++) {
      for (int c = 0; c < matrix_1[0].length; c++) {
        if (matrix_1[r][c] != matrix_2[r][c]) {
          return false;
        }
      }
    }
    return true;
  }
}