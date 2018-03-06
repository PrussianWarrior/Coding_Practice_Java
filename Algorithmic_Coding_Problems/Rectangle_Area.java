import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given n non-negative integers representing an elevation map where the width of each bar is 1, 
  compute how much water it is able to trap after raining.

  For example, 
  Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
*/

public class Rectangle_Area {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<int[]> coordinates = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            coordinates = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_rectangle_coordinates(coordinates);

          long start = System.currentTimeMillis();
          int rect_area_1 = compute_rectangle_area_1(coordinates);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Area of rectangle:");
          System.out.println("Solution 1 = " + rect_area_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int compute_rectangle_area_1(List<int[]> coordinates) {
    int[] rect_1_base_left = coordinates.get(0);
    int[] rect_1_top_right = coordinates.get(1);
    int[] rect_2_base_left = coordinates.get(2);
    int[] rect_2_top_right = coordinates.get(3);

    boolean is_rect_1_left_rect_2  = rect_1_top_right[0] <= rect_2_base_left[0];
    boolean is_rect_1_right_rect_2 = rect_1_base_left[0] >= rect_2_top_right[0];
    boolean is_rect_1_above_rect_2 = rect_1_base_left[1] >= rect_2_top_right[1];
    boolean is_rect_1_below_rect_2 = rect_1_top_right[1] <= rect_2_base_left[1];

    int overlap_area = 0;
    if (!is_rect_1_left_rect_2 && !is_rect_1_right_rect_2 &&
        !is_rect_1_above_rect_2 && !is_rect_1_below_rect_2) {
      int left  = Math.max(rect_1_base_left[0], rect_2_base_left[0]);
      int right = Math.min(rect_1_top_right[0], rect_2_top_right[0]);
      int top   = Math.min(rect_1_top_right[1], rect_2_top_right[1]);
      int base  = Math.max(rect_1_base_left[1], rect_2_base_left[1]);
      overlap_area = (right - left) * (top - base);
    }
    System.out.println("overlap_area = " + overlap_area);

    return (rect_1_top_right[0] - rect_1_base_left[0]) * (rect_1_top_right[1] - rect_1_base_left[1]) +
           (rect_2_top_right[0] - rect_2_base_left[0]) * (rect_2_top_right[1] - rect_2_base_left[1]) - 
           overlap_area;
  }

  private static List<int[]> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] pairs_of_int = line.split(",");
    List<int[]> coordinates = new ArrayList<>();
    for (String pair : pairs_of_int) {
      int index_of_opening_square_bracket = pair.indexOf("[");
      int index_of_closing_square_bracket = pair.indexOf("]");
      String[] x_and_y = pair.substring(index_of_opening_square_bracket + 1, 
        index_of_closing_square_bracket).trim().split(" ");

      int x = Integer.parseInt(x_and_y[0].trim());
      int y = Integer.parseInt(x_and_y[1].trim());
      coordinates.add(new int[]{x, y});
    }

    return coordinates;
  }

  private static void print_rectangle_coordinates(List<int[]> coordinates) {
    System.out.println("Triangle 1:");
    System.out.printf("Base left: x = %5d, y = %5d\n", coordinates.get(0)[0], coordinates.get(0)[1]);
    System.out.printf("Top right: x = %5d, y = %5d\n", coordinates.get(1)[0], coordinates.get(1)[1]);
    
    System.out.println("Triangle 2:");
    System.out.printf("Base left: x = %5d, y = %5d\n", coordinates.get(2)[0], coordinates.get(2)[1]);
    System.out.printf("Top right: x = %5d, y = %5d\n", coordinates.get(3)[0], coordinates.get(3)[1]);
  }
}