import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description:
  Given a string containing just the characters '(' and ')', find
  the length of the longest valid (well-formed) parentheses substring.

  For "(()", the longest valid parentheses substring is "()", which has length = 2.

  Another example is ")()())", where the longest valid parentheses substring is "()()",
  which has length = 4.
*/

public class Longest_Valid_Parentheses {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String input = null;
          if (line.indexOf("Customized:") >= 0) {
            input = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " +
              "Customized: or Randomized:");
          }

          System.out.println("Input = " + input + ", length = " + input.length());

          String longest_substr_parentheses_1 = "";
          boolean is_soln_1_valid_parentheses = false;
          int[] longest_valid_parentheses_1 = find_longest_valid_parentheses_1(input);
          if (longest_valid_parentheses_1[0] >= 0) {
            longest_substr_parentheses_1 = input.substring(longest_valid_parentheses_1[0],
              longest_valid_parentheses_1[1] + 1);
            is_soln_1_valid_parentheses = is_valid_parentheses(longest_substr_parentheses_1);
          }

          System.out.println("Longest valid parentheses:");
          System.out.println("SOLUTION 1: " + (longest_valid_parentheses_1[1] - longest_valid_parentheses_1[0] + 1) +
            ". Start = " + longest_valid_parentheses_1[0] + ", end = " + longest_valid_parentheses_1[1] +
            ", string = " + longest_substr_parentheses_1 +
            ", valid = " + is_soln_1_valid_parentheses);

          System.out.println();

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int[] find_longest_valid_parentheses_1(String str) {
    LinkedList<Integer> position_stack = new LinkedList<>();
    LinkedList<int[]> interval_stack = new LinkedList<>();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        position_stack.add(i);
      } else if (str.charAt(i) == ')') {
        if (position_stack.isEmpty()) {
          continue;
        }
        int[] new_interval = new int[]{position_stack.removeLast(), i};
        interval_stack.add(new_interval);
        while (interval_stack.size() >= 2) {
          int[] interval_2 = interval_stack.removeLast();
          int[] interval_1 = interval_stack.removeLast();
          int[] merged_interval = merge_interval(interval_1, interval_2);

          if (merged_interval == null) {
            interval_stack.add(interval_1);
            interval_stack.add(interval_2);
            break;
          }
          interval_stack.add(merged_interval);
        }
      }
    }

    int[] longest_interval = new int[]{-1, -2};
    for (int[] interval : interval_stack) {
      if (longest_interval[1] - longest_interval[0] < interval[1] - interval[0]) {
        longest_interval = interval;
      }
    }
    return longest_interval;
  }

  private static int[] merge_interval(int[] interval_1, int[] interval_2) {
    int[] merged_interval = null;
    if ((interval_1[0] + 1 == interval_2[0]) && (interval_1[1] - 1 == interval_2[1])) {
      merged_interval = new int[]{interval_1[0], interval_1[1]};
    } else if (interval_1[1] + 1 == interval_2[0]) {
      merged_interval = new int[]{interval_1[0], interval_2[1]};
    }
    return merged_interval;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }

  private static boolean is_valid_parentheses(String str) {
    return is_valid_parentheses(str, 0, str.length() - 1);
  }

  private static boolean is_valid_parentheses(String str, int start, int end) {
    LinkedList<Character> stack = new LinkedList<>();
    while (start <= end) {
      if (str.charAt(start) == '(') {
        stack.add('(');
      } else {
        if (stack.isEmpty()) {
          return false;
        }
        stack.removeLast();
      }
      start++;
    }

    return stack.isEmpty();
  }

  private static void print_list_with_index(String str) {
    int longest_index = num_of_digits(str.length());
    String index_width_format = "%" + (longest_index + 1) + "d";

    for (int i = 0; i < str.length(); i++) {
      System.out.printf(index_width_format + " - %c\n", i, str.charAt(i));
    }
    System.out.println();
  }

  private static void print_list_without_index(String str) {
    for (int i = 0; i < str.length(); i++) {
      System.out.print(str.charAt(i) + " ");
    }
    System.out.println();
  }

  private static int num_of_digits(int N) {
    return (int)Math.floor(Math.log10(N)) + 1;
  }
}