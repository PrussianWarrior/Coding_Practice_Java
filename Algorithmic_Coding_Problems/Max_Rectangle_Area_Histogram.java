import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find 
  the area of largest rectangle in the histogram.


  Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].


  The largest rectangle is shown in the shaded area, which has area = 10 unit.

  For example,
  Given heights = [2,1,5,6,2,3],
  return 10.
*/

public class Max_Rectangle_Area_Histogram {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> histogram = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            histogram = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(histogram);

          long start = System.currentTimeMillis();
          int max_rectangular_area_1 = find_max_rectangular_area_histogram_1(histogram);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int max_rectangular_area_2 = find_max_rectangular_area_histogram_2(histogram);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("Max rectangular area in the histogram:");
          System.out.println("Solution 1: " + max_rectangular_area_1 + ", execution time = " 
              + execution_time_soln_1);
          System.out.println("Solution 2: " + max_rectangular_area_2 + ", execution time = " 
              + execution_time_soln_2);

          if (max_rectangular_area_1 != max_rectangular_area_2) {
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

  private static int find_max_rectangular_area_histogram_1(List<Integer> histogram) {
    int max_area = 0;
    for (int i = 0; i < histogram.size(); i++) {
      int min_height = histogram.get(i);
      max_area = Math.max(max_area, min_height);
      for (int j = i + 1; j < histogram.size(); j++) {
        min_height = Math.min(histogram.get(j), min_height);
        max_area = Math.max(max_area, min_height * (j - i + 1));
      }
    }
    return max_area;
  }

  private static int find_max_rectangular_area_histogram_2(List<Integer> histogram) {
    int max_area = 0;
    LinkedList<Integer> stack = new LinkedList<>();
    int i = 0;
    while (i < histogram.size()) {
      if (stack.isEmpty() || histogram.get(i) >= histogram.get(stack.getLast())) {
        stack.addLast(i++);
      } else {
        int top_index = stack.removeLast();
        int width = stack.isEmpty() ? i : i - stack.getLast() - 1;
        max_area = Math.max(max_area, histogram.get(top_index) * width);
      }
    }

    while (!stack.isEmpty()) {
      int top_index = stack.removeLast();
      int width = stack.isEmpty() ? histogram.size() : histogram.size() - stack.getLast() - 1;
      max_area = Math.max(max_area, histogram.get(top_index) * width);
    }

    return max_area;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] integer_strings = line.split(" ");
    List<Integer> list = new ArrayList<>();
    for (String int_str : integer_strings) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    return list;
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d - %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}