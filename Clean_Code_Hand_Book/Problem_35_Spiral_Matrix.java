import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_35_Spiral_Matrix {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          int[][] matrix = process_input(line);

          System.out.println("Original matrix");
          print(matrix);
          System.out.println("Spiral matrix");
          print_spiral_matrix_1(matrix);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }   
  }

  private static void print_spiral_matrix_1(int[][] matrix) {
    int R = matrix.length;
    int C = matrix[0].length;

    int top = 0;
    int down = R-1;
    int left = 0;
    int right = C-1;

    int counter = 1;
    while (top < down && left < right) {
      for (int c = left; c < right; c++) {
        System.out.printf("%5d", matrix[top][c]);
        if (counter++ % 10 == 0) {
          System.out.println();
        }
      }

      for (int r = top; r < down; r++) {
        System.out.printf("%5d", matrix[r][right]);
        if (counter++ % 10 == 0) {
          System.out.println();
        } 
      }

      for (int c = right; c > left; c--) {
        System.out.printf("%5d", matrix[down][c]);
        if (counter++ % 10 == 0) {
          System.out.println();
        }
      }

      for (int r = down; r > top; r--) {
        System.out.printf("%5d", matrix[r][left]);
        if (counter++ % 10 == 0) {
          System.out.println();
        }
      }

      left++;
      right--;
      top++;
      down--;
    }

    if (top == down) {
      for (int c = left; c <= right; c++) {
        System.out.printf("%5d", matrix[top][c]);
        if (counter++ % 10 == 0) {
          System.out.println();
        } 
      }
    } else if (right == left) {
      for (int r = top; r <= down; r++) {
        System.out.printf("%5d", matrix[r][right]);
        if (counter++ % 10 == 0) {
          System.out.println();
        } 
      }
    }

    System.out.println();
  }

  private static int[][] process_input(String line) {
    String[] words = line.trim().split(" ");
    int row = Integer.parseInt(words[0].trim());
    int col = Integer.parseInt(words[1].trim());
    int[][] matrix = new int[row][col];
    int val = 1;
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        matrix[r][c] = val++;
      }
    }
    return matrix;
  }

  private static void print(int[][] matrix) {
    int counter = 1;
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.printf("%5d", matrix[r][c]);
      }
      System.out.println();
    }
    System.out.println();
  }
}