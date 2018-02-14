import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_42_Climbing_Stairs {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          int num_stairs = Integer.parseInt(line.trim());

          int number_of_ways_to_reach_top_soln_1 = count_number_of_ways_to_reach_top_1(num_stairs);
          int number_of_ways_to_reach_top_soln_2 = count_number_of_ways_to_reach_top_2(num_stairs);
          int number_of_ways_to_reach_top_soln_3 = count_number_of_ways_to_reach_top_3(num_stairs);
          int number_of_ways_to_reach_top_soln_4 = count_number_of_ways_to_reach_top_4(num_stairs);
          
          System.out.println("NUMBER OF WAYS TO REACH THE TOP");
          System.out.println("Solution 1 = " + number_of_ways_to_reach_top_soln_1);
          System.out.println("Solution 2 = " + number_of_ways_to_reach_top_soln_2);
          System.out.println("Solution 3 = " + number_of_ways_to_reach_top_soln_3);
          System.out.println("Solution 4 = " + number_of_ways_to_reach_top_soln_4);

          if (number_of_ways_to_reach_top_soln_1 != number_of_ways_to_reach_top_soln_2 ||
              number_of_ways_to_reach_top_soln_1 != number_of_ways_to_reach_top_soln_3 ||
              number_of_ways_to_reach_top_soln_1 != number_of_ways_to_reach_top_soln_4) {
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

  private static int count_number_of_ways_to_reach_top_1(int num_stairs) {
    return num_stairs <= 2 ? num_stairs : count_number_of_ways_to_reach_top_1(num_stairs - 1) + 
           count_number_of_ways_to_reach_top_1(num_stairs - 2);
  }

  private static int count_number_of_ways_to_reach_top_2(int num_stairs) {
    if (num_stairs <= 2) {
      return num_stairs;
    }
    int[] cache = new int[num_stairs + 1];
    cache[1] = 1;
    cache[2] = 2;
    return count_number_of_ways_to_reach_top_2(num_stairs, cache);
  }

  private static int count_number_of_ways_to_reach_top_2(int num_stairs, int[] cache) {
    if (cache[num_stairs] > 0) {
      return cache[num_stairs];
    }
    int result = count_number_of_ways_to_reach_top_2(num_stairs - 1, cache) + 
                 count_number_of_ways_to_reach_top_2(num_stairs - 2, cache);
    cache[num_stairs] = result;
    return result;
  }

  private static int count_number_of_ways_to_reach_top_3(int num_stairs) {
    if (num_stairs <= 2) {
      return num_stairs;
    }
    int[] soln_sub_problem = new int[num_stairs + 1];
    soln_sub_problem[1] = 1;
    soln_sub_problem[2] = 2;
    for (int i = 3; i <= num_stairs; i++) {
      soln_sub_problem[i] = soln_sub_problem[i - 1] + soln_sub_problem[i - 2];
    }
    return soln_sub_problem[num_stairs];
  }

  private static int count_number_of_ways_to_reach_top_4(int num_stairs) {
    if (num_stairs <= 2) {
      return num_stairs;
    }
    int sub_problems_n_2 = 1;
    int sub_problems_n_1 = 2;
    int sub_problems_n = 0;
    for (int i = 3; i <= num_stairs; i++) {
      sub_problems_n = sub_problems_n_1 + sub_problems_n_2;
      sub_problems_n_2 = sub_problems_n_1;
      sub_problems_n_1 = sub_problems_n;
    }
    return sub_problems_n;
  }
}




