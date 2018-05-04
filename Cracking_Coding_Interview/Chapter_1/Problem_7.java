import java.util.*;

public class Problem_7 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int[][] matrix = generate_matrix(N);
    System.out.println("Before rotating");
    print_matrix(matrix);
    rotate_90_degree_1(matrix);
    System.out.println("After rotating");
    print_matrix(matrix);
  }

  private static void rotate_90_degree_1(int[][] matrix) {
    int num_row = matrix.length;
    int num_col = matrix[0].length;
    if (num_row != num_col) {
      throw new IllegalArgumentException("The number of rows != number of columns. The matrix must be of NxN dimension.");
    }

    int top_row = 0;
    int down_row = num_row - 1;
    int left_col = 0;
    int right_col = num_col - 1;
    while (top_row < down_row) {
      for (int i = 0; i < down_row - top_row; i++) {
        int temp = matrix[top_row][left_col + i];
        matrix[top_row][left_col + i] = matrix[down_row - i][left_col];
        matrix[down_row - i][left_col] = matrix[down_row][right_col - i];
        matrix[down_row][right_col - i] = matrix[top_row + i][right_col];
        matrix[top_row + i][right_col] = temp;
      }
      top_row++;
      down_row--;
      left_col++;
      right_col--;
    }
  }

  private static int[][] generate_matrix(int N) {
    int[][] matrix = new int[N][N];
    int num = 1;
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < N; c++) {
        matrix[r][c] = num++;
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
  }
}