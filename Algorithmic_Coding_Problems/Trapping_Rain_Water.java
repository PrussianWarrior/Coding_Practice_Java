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

public class Trapping_Rain_Water {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> heights = null;
          if (line.indexOf("Customized:") >= 0) {
            heights = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("Heights:");
          print_list_without_index(heights);

          long start = System.currentTimeMillis();
          int rain_water_trapped_soln_1 = compute_rain_water_trapped_1(heights);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int rain_water_trapped_soln_2 = compute_rain_water_trapped_2(heights);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int rain_water_trapped_soln_3 = compute_rain_water_trapped_3(heights);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          System.out.println("Rain water trapped:");
          System.out.println("Solution 1: " + rain_water_trapped_soln_1 + ", execution time = " 
            + execution_time_soln_1);
          System.out.println("Solution 2: " + rain_water_trapped_soln_2 + ", execution time = " 
            + execution_time_soln_2);
          System.out.println("Solution 3: " + rain_water_trapped_soln_3 + ", execution time = " 
            + execution_time_soln_3);

          if (rain_water_trapped_soln_1 != rain_water_trapped_soln_2 ||
              rain_water_trapped_soln_1 != rain_water_trapped_soln_3) {
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

  private static int compute_rain_water_trapped_1(List<Integer> heights) {
    if (heights.size() <= 2) {
      return 0;
    }

    int rain_water_trapped = 0;
    int len = heights.size();

    for (int i = 1; i < len - 1; i++) {
      int max_height_from_left = 0;
      int max_height_from_right = 0;
      for (int j = i; j >= 0; j--) {
        max_height_from_left = Math.max(max_height_from_left, heights.get(j));
      }

      for (int j = i; j < len; j++) {
        max_height_from_right = Math.max(max_height_from_right, heights.get(j));
      }

      rain_water_trapped += Math.min(max_height_from_left, max_height_from_right) - heights.get(i);      
    }

    return rain_water_trapped;
  }

  private static int compute_rain_water_trapped_2(List<Integer> heights) {
    if (heights.size() <= 2) {
      return 0;
    }

    int len = heights.size();
    int[] max_height_from_left = new int[len];
    int[] max_height_from_right = new int[len];
    max_height_from_left[0] = heights.get(0);
    max_height_from_right[len - 1] = heights.get(len - 1);

    for (int i = 1; i < len - 1; i++) {
      max_height_from_left[i] = Math.max(max_height_from_left[i - 1], heights.get(i));
      max_height_from_right[len - i - 1] = Math.max(max_height_from_right[len - i], heights.get(len - i - 1));
    }

    int rain_water_trapped = 0;
    for (int i = 1; i < len - 1; i++) {
      rain_water_trapped += Math.min(max_height_from_left[i], max_height_from_right[i]) - heights.get(i);
    }

    return rain_water_trapped;
  }

  private static int compute_rain_water_trapped_3(List<Integer> heights) {
    if (heights.size() <= 2) {
      return 0;
    }

    int len = heights.size();
    int index_of_max_height = 0;
    int max_height = 0;

    for (int i = 0; i < len; i++) {
      if (max_height < heights.get(i)) {
        max_height = heights.get(i);
        index_of_max_height = i;
      }
    }

    int rain_water_trapped = 0;
    max_height = heights.get(0);
    for (int i = 1; i < index_of_max_height; i++) {
      if (max_height <= heights.get(i)) {
        max_height = heights.get(i);
      } else {
        rain_water_trapped += max_height - heights.get(i);
      }
    }

    max_height = heights.get(len - 1);
    for (int i = len - 2; i > index_of_max_height; i--) {
      if (max_height < heights.get(i)) {
        max_height = heights.get(i);
      } else {
        rain_water_trapped += max_height - heights.get(i);
      }
    }

    return rain_water_trapped;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    List<Integer> list = new ArrayList<>();

    for (String int_str : line.split(" ")) {
      list.add(Integer.parseInt(int_str.trim()));
    }
    return list;
  }

  private static void print_list_with_index(List<Integer> list) {
    int longest_index = num_of_digits(list.size());
    int largest_length = largest_length(list);
    String index_width_format = "%" + longest_index + "d";
    String num_width_format = "%" + largest_length + "d";

    for (int i = 0; i < list.size(); i++) {
      System.out.printf(index_width_format + " - " + num_width_format + "\n", i, list.get(i));
    }
    System.out.println();
  }

  private static void print_list_without_index(List<Integer> list) {
    int largest_length = largest_length(list);
    String num_width_format = "%" + (largest_length + 1) + "d";
    for (int n : list) {
      System.out.printf(num_width_format, n);
    }
    System.out.println();
  }

  private static int num_of_digits(int N) {
    return (int)Math.floor(Math.log10(N)) + 1;
  }

  private static int largest_length(List<Integer> list) {
    int max_len = 0;
    for (int n : list) {
      max_len = Math.max(max_len, num_of_digits(n));
    }
    return max_len;
  }
}