import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  A message containing letters from A-Z is being encoded to numbers using the following mapping:
  'A' -> 1
  'B' -> 2
  ...
  'Z' -> 26

  Given an encoded message containing digits, determine the total number of ways to decode it.
  For example,
  Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
  The number of ways decoding "12" is 2.
*/

public class Decode_Ways {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String digits = "";
          if (line.indexOf("Customized:") >= 0) {
            digits = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          int num_decode_1 = count_num_decode_1(digits);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int num_decode_2 = count_num_decode_2(digits);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int num_decode_3 = count_num_decode_3(digits);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          start = System.currentTimeMillis();
          int num_decode_4 = count_num_decode_3(digits);
          stop = System.currentTimeMillis();
          long execution_time_soln_4 = stop - start;

          System.out.println("Number of decodes:");
          System.out.println("Solution 1 = " + num_decode_1 + ", execution time = " 
            + execution_time_soln_1);
          System.out.println("Solution 2 = " + num_decode_2 + ", execution time = " 
            + execution_time_soln_2);
          System.out.println("Solution 3 = " + num_decode_3 + ", execution time = " 
            + execution_time_soln_3);
          System.out.println("Solution 4 = " + num_decode_3 + ", execution time = " 
            + execution_time_soln_4);
          
          if (num_decode_1 != num_decode_2 ||
              num_decode_1 != num_decode_3 ||
              num_decode_1 != num_decode_4) {
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

  private static int count_num_decode_1(String digits) {
    if (digits.isEmpty()) {
      return 0;
    }
    return count_num_decode_1_helper(digits, digits.length() - 1);
  }

  private static int count_num_decode_1_helper(String digits, int end_index) {
    if (end_index < 0) {
      return 1;
    }

    int num_decode = 0;
    int last_digit = Integer.parseInt(digits.substring(end_index, end_index + 1));
    if (last_digit > 0) {
      num_decode += count_num_decode_1_helper(digits, end_index - 1);
    }

    if (end_index >= 1) {
      int last_2_digits = Integer.parseInt(digits.substring(end_index - 1, end_index + 1));
      if (10 <= last_2_digits && last_2_digits <= 26) {
        num_decode += count_num_decode_1_helper(digits, end_index - 2);
      }
    }

    return num_decode;
  }

  private static int count_num_decode_2(String digits) {
    if (digits.isEmpty()) {
      return 0;
    }
    int[] cache = new int[digits.length()];
    Arrays.fill(cache, -1);
    return count_num_decode_2_helper(digits, digits.length() - 1, cache);
  }

  private static int count_num_decode_2_helper(String digits, int end_index, int[] cache) {
    if (end_index < 0) {
      return 1;
    }

    if (cache[end_index] >= 0) {
      // System.out.println("Returning from cache for end_index = " + end_index);
      return cache[end_index];
    }

    int num_decode = 0;
    int last_digit = Integer.parseInt(digits.substring(end_index, end_index + 1));
    if (last_digit > 0) {
      num_decode += count_num_decode_2_helper(digits, end_index - 1, cache);
    }

    if (end_index >= 1) {
      int last_2_digits = Integer.parseInt(digits.substring(end_index - 1, end_index + 1));
      if (10 <= last_2_digits && last_2_digits <= 26) {
        num_decode += count_num_decode_2_helper(digits, end_index - 2, cache);
      }  
    }
    
    cache[end_index] = num_decode;
    return num_decode;
  }

  private static int count_num_decode_3(String digits) {
    if (digits.isEmpty()) {
      return 0;
    }
    int[] num_decode_at_index = new int[digits.length() + 1];
    num_decode_at_index[0] = 1;

    for (int i = 1; i <= digits.length(); i++) {
      int last_digit = Integer.parseInt(digits.substring(i - 1, i));
      if (last_digit > 0) {
        num_decode_at_index[i] += num_decode_at_index[i - 1];
      }
      if (i >= 2) {
        int last_2_digits = Integer.parseInt(digits.substring(i - 2, i));
        if (10 <= last_2_digits && last_2_digits <= 26) {
          num_decode_at_index[i] += num_decode_at_index[i - 2];
        }
      }
    }
    return num_decode_at_index[digits.length()];
  }

  private static int count_num_decode_4(String digits) {
    if (digits.isEmpty()) {
      return 0;
    }

    int[] num_decode_at_index = new int[digits.length() + 1];
    num_decode_at_index[0] = 1;
    num_decode_at_index[1] = Integer.parseInt(digits.substring(0, 1)) > 0 ? 1 : 0;
    for (int i = 2; i <= digits.length(); i++) {
      int last_digit = Integer.parseInt(digits.substring(i - 1, i));
      int last_2_digits = Integer.parseInt(digits.substring(i - 2, i));
      if (last_digit > 0) {
        num_decode_at_index[i] += num_decode_at_index[i - 1];
      }
      if (10 <= last_2_digits && last_2_digits <= 26) {
        num_decode_at_index[i] += num_decode_at_index[i - 2];
      }
    }
    return num_decode_at_index[digits.length()];
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }
}