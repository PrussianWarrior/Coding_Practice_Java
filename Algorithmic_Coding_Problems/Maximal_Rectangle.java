import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing 
  only 1's and return its area.

  For example, given the following matrix:

  1 0 1 0 0
  1 0 1 1 1
  1 1 1 1 1
  1 0 0 1 0
  Return 6.

*/

public class Maximal_Rectangle {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<List<Integer>> matrix = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            matrix = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            matrix = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_matrix(matrix);

          long start = System.currentTimeMillis();
          int max_rectangular_area_in_matrix_1 = find_max_rectangular_area_in_matrix_1(matrix);
          long end = System.currentTimeMillis();
          long execution_time_soln_1 = end - start;

          System.out.println("MAX RECTANGULAR AREA IN MATRIX:");
          System.out.println("Solution 1: " + max_rectangular_area_in_matrix_1 + ", execution time = " 
              + execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_max_rectangular_area_in_matrix_1(List<List<Integer>> matrix) {
    int num_rows = matrix.size();
    int num_cols = matrix.get(0).size();

    int[][] cumulative_ones = new int[num_rows][num_cols];
    for (int r = 0; r < num_rows; r++) {
      for (int c = 0; c < num_cols; c++) {
        if (matrix.get(r).get(c) == 0) {
          cumulative_ones[r][c] = 0;
        } else {
          cumulative_ones[r][c] = r == 0 ? 1 : cumulative_ones[r - 1][c] + 1;
        }
      }
    }

    int max_area = 0;
    for (int r = 0; r < num_rows; r++) {
      max_area = Math.max(max_area, find_max_area_in_histogram_1(cumulative_ones[r]));
    }

    return max_area;
  }

  private static int find_max_area_in_histogram_1(int[] arr) {
    int max_area = 0;
    LinkedList<Integer> stack = new LinkedList<>();
    int i = 0;
    while (i < arr.length) {
      if (stack.isEmpty() || arr[i] >= arr[stack.getLast()]) {
        stack.addLast(i++);
      } else {
        int top_index = stack.removeLast();
        int base = stack.isEmpty() ? i : i - stack.getLast() - 1;
        max_area = Math.max(max_area, arr[top_index] * base);
      }
    }

    while (!stack.isEmpty()) {
      int top_index = stack.removeLast();
      int base = stack.isEmpty() ? i : i - stack.getLast() - 1;
      max_area = Math.max(max_area, arr[top_index] * base);
    }
    return max_area;
  }

  private static List<List<Integer>> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] integer_strings = line.split(" ");
    int num_rows = Integer.parseInt(integer_strings[0].trim());
    int num_cols = Integer.parseInt(integer_strings[1].trim());
    int num_zeros = Integer.parseInt(integer_strings[2].trim());

    List<List<Integer>> matrix = new ArrayList<>();
    for (int r = 1; r <= num_rows; r++) {
      List<Integer> row = new ArrayList<>();
      for (int c = 1; c <= num_cols; c++) {
        row.add(1);
      }
      matrix.add(row);
    }

    Set<Integer> already_used_cells = new HashSet<>();
    for (int i = 1; i <= num_zeros; i++) {
      int random_cell = (int)(Math.random() * (num_rows * num_cols));
      while (already_used_cells.contains(random_cell)) {
        random_cell = (int)(Math.random() * (num_rows * num_cols));
      }
      int r = random_cell / num_cols;
      int c = random_cell % num_cols;
      matrix.get(r).set(c, 0);
    }
    return matrix;
  }

  private static List<List<Integer>> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    
    int index_of_opening_bracket = line.indexOf("{");
    int index_of_closing_bracket = line.indexOf("}");
    line = line.substring(index_of_opening_bracket + 1, index_of_closing_bracket);

    List<List<Integer>> matrix = new ArrayList<>();

    String[] rows = line.split(",");
    for (String row : rows) {
      int index_of_opening_square_bracket = row.indexOf("[");
      int index_of_closing_square_bracket = row.indexOf("]");
      row = row.substring(index_of_opening_square_bracket + 1, index_of_closing_square_bracket).trim();

      List<Integer> list = new ArrayList<>();
      for (String data : row.split(" ")) {
        list.add(Integer.parseInt(data.trim()));
      }
      
      matrix.add(list);
    } 
    return matrix;
  }

  private static void print_matrix(List<List<Integer>> matrix) {
    for (List<Integer> row : matrix) {
      for (int data : row) {
        System.out.printf("%5d", data);
      }
      System.out.println();
    }
    System.out.println();
  }
}