import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an encoded string, return it's decoded string.

  The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets 
  is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

  You may assume that the input string is always valid; No extra white spaces, square brackets 
  are well-formed, etc.

  Furthermore, you may assume that the original data does not contain any digits and that digits 
  are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

  Examples:

  s = "3[a]2[bc]", return "aaabcbc".
  s = "3[a2[c]]", return "accaccacc".
  s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
*/

public class Decode_String {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String str = "";
          if (line.indexOf("Customized:") >= 0) {
            str = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          String decoded_str_1 = decode_string_1(str);
          long stop = System.currentTimeMillis();
          long decode_str_execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          String decoded_str_2 = decode_string_2(str);
          stop = System.currentTimeMillis();
          long decode_str_execution_time_soln_2 = stop - start;

          System.out.println("Decoded string:");
          System.out.println("Solution 1 = " + decoded_str_1 + ", execution time = " 
            + decode_str_execution_time_soln_1);
          System.out.println("Solution 2 = " + decoded_str_2 + ", execution time = " 
            + decode_str_execution_time_soln_2);

          if (!decoded_str_1.equals(decoded_str_2)) {
            System.out.println("decode_string_1 != decode_string_2");
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

  private static String decode_string_1(String str) {
    return decode_string_1_helper(str, new int[]{0});
  }

  private static String decode_string_1_helper(String str, int[] index) {
    StringBuilder result = new StringBuilder();
    while (index[0] < str.length() && str.charAt(index[0]) != ']') {
      if (!Character.isDigit(str.charAt(index[0]))) {
        result.append(str.charAt(index[0]++));
      } else {
        int count = 0;
        while (index[0] < str.length() && Character.isDigit(str.charAt(index[0]))) {
          count = 10 * count + Character.getNumericValue(str.charAt(index[0]++));
        }

        index[0]++;
        String temp = decode_string_1_helper(str, index);
        index[0]++;

        while (count-- > 0) {
           result.append(temp);
        }
      }
    }
    return result.toString();
  }

  private static String decode_string_2(String str) {
    LinkedList<String> str_stack = new LinkedList<>();
    LinkedList<Integer> count_stack = new LinkedList<>();

    StringBuilder decoded_str = new StringBuilder();
    int i = 0;

    while (i < str.length()) {
      if (Character.isDigit(str.charAt(i))) {
        int count = 0;
        while (i < str.length() && Character.isDigit(str.charAt(i))) {
          count = 10 * count + Character.getNumericValue(str.charAt(i++));
        }
        count_stack.addFirst(count);
      } else if (str.charAt(i) == '[') {
        str_stack.addFirst(decoded_str.toString());
        decoded_str = new StringBuilder();
        i++;
      } else if (str.charAt(i) == ']') {
        StringBuilder temp = new StringBuilder(str_stack.removeFirst());
        int count = count_stack.removeFirst();
        while (count-- > 0) {
          temp.append(decoded_str.toString());
        }
        decoded_str = temp;
        i++;
      } else {
        decoded_str.append(str.charAt(i++));
      }
    }

    return decoded_str.toString();
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }
}