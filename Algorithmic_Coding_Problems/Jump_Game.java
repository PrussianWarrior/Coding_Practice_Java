import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an array of non-negative integers, you are initially positioned at the first index of the array.

  Each element in the array represents your maximum jump length at that position.

  Determine if you are able to reach the last index.

  For example:
  A = [2,3,1,1,4], return true.

  A = [3,2,1,0,4], return false.
*/

public class Jump_Game {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> jumps = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            jumps = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(jumps);
          
          long start = System.currentTimeMillis();
          boolean is_reachable_1 = is_last_reachable_1(jumps);
          long stop = System.currentTimeMillis();
          long is_reachable_execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int min_num_jumps_1 = find_min_num_jumps_1(jumps);
          stop = System.currentTimeMillis();
          long min_num_jumps_execution_time_soln_1 = stop - start;

          System.out.println("Is reachable and min number of jumps:");
          System.out.println("Method 1: reachable = " + is_reachable_1 
            + ", execution time = " + is_reachable_execution_time_soln_1 
            + ", min number of jumps = " + min_num_jumps_1
            + ", execution time = " + min_num_jumps_execution_time_soln_1);
        
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static boolean is_last_reachable_1(List<Integer> jumps) {
    return is_reachable_from_start_to_end_1(jumps, 0, jumps.size() - 1);
  }

  private static boolean is_reachable_from_start_to_end_1(List<Integer> jumps, int start, int end) {
    int max_reach = 0;
    for (int i = start; i <= end; i++) {
      if (jumps.get(i) <= 0) {
        return false;
      }

      max_reach = Math.max(max_reach, jumps.get(i) + i);
      if (max_reach >= end) {
        return true;
      }
    }

    return false;
  }

  private static int find_min_num_jumps_1(List<Integer> jumps) {
    if (jumps.size() <= 1) {
      return 0;
    }

    int min_num_jump = 0;
    int current_max_reach = 0;
    int last_max_reach = 0;

    for (int i = 0; i <= Math.min(current_max_reach, jumps.size() - 1); i++) {
      if (i > last_max_reach) {
        min_num_jump++;
        last_max_reach = current_max_reach;
      }

      current_max_reach = Math.max(current_max_reach, jumps.get(i) + i);
    }

    return current_max_reach < jumps.size() - 1 ? 0 : min_num_jump;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));

    String[] jumps_str = line.split(" ");
    List<Integer> jump_arr = new ArrayList<>();
    for (String jump : jumps_str) {
      jump_arr.add(Integer.parseInt(jump));
    }
    return jump_arr;
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

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d : %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}