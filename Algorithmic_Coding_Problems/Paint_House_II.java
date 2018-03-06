import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem Description:
  There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. 
  The cost of painting each house with a certain color is different. You have to paint all the houses such 
  that no two adjacent houses have the same color.
 
  The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, 
  costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with 
  color green, and so on... Find the minimum cost to paint all houses.

*/

public class Paint_House_II {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          int[][] costs = null;
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
            costs = get_customized_input(list, num_rows, num_cols);
          } else if (line.indexOf("Randomized:") >= 0) {
            costs = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_matrix(costs);
        
          long start = System.currentTimeMillis();
          int min_costs_1 = find_minimum_cost_1(costs);
          long end = System.currentTimeMillis();
          long execution_time_1 = end - start;

          System.out.println("Min cost of painting " + costs.length + " houses:");
          System.out.println("SOLUTION 1: " + min_costs_1 + ", execution time = " + execution_time_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_minimum_cost_1(int[][] costs) {
    int num_houses = costs.length;
    int num_colors = costs[0].length;
    
    int min_prec_house = 0;
    int second_min_prec_house = 0;
    int min_index_prec_house = -1;

    for (int r = 0; r < num_houses; r++) {
      int min_index = 0;
      int min = Integer.MAX_VALUE;
      int second_min = Integer.MAX_VALUE;

      for (int c = 0; c < num_colors; c++) {
        if (c == min_index_prec_house) {
          costs[r][c] += second_min_prec_house;
        } else {
          costs[r][c] += min_prec_house;
        }

        if (min > costs[r][c]) {
          second_min = min;
          min = costs[r][c];
          min_index = c;
        } else if (second_min > costs[r][c]) {
          second_min = costs[r][c];
        }
      }

      min_index_prec_house = min_index;
      min_prec_house = min;
      second_min_prec_house = second_min;
    }

    int min_cost = Integer.MAX_VALUE;
    for (int c = 0; c < num_colors; c++) {
      min_cost = Math.min(min_cost, costs[num_houses - 1][c]);
    }
    return min_cost;
  }

  private static int[][] get_customized_input(List<String> list, int num_rows, int num_cols) {
    int[][] matrix = new int[num_rows][num_cols];
    for (int r = 0; r < num_rows; r++) {
      String temp = remove_extra_spaces_1(list.get(r));
      String[] integer_strings = temp.split(" ");
      if (integer_strings.length != num_cols) {
        throw new IllegalArgumentException("There is a mismatch between the number of columns in the " +
          "string array and the input number of columns");
      }
      for (int c = 0; c < num_cols; c++) {
        matrix[r][c] = Integer.parseInt(integer_strings[c].trim());
      }
    }
    return matrix;
  }

  private static int[][] get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] integer_strings = line.split(" ");

    int num_houses = Integer.parseInt(integer_strings[0]);
    int num_colors = Integer.parseInt(integer_strings[0]);
    int min = Integer.parseInt(integer_strings[1]);
    int max = Integer.parseInt(integer_strings[2]);

    int[][] costs = new int[num_houses][num_colors];
    for (int r = 0; r < num_houses; r++) {
      for (int c = 0; c < num_colors; c++) {
        costs[r][c] = (int)(Math.random() * (max - min  + 1)) + min;
      }
    }

    return costs;
  }

  private static String remove_extra_spaces_1(String str) {
    StringBuilder new_str = new StringBuilder();
    str = str.trim();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || (Character.isSpace(str.charAt(i + 1)))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i < str.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }

  private static void print_matrix(int[][] matrix) {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.printf("%5d", matrix[r][c]);
      }
      System.out.println();
    }
    System.out.println("\n");
  }
}