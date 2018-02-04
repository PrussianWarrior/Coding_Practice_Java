import java.lang.*;
import java.util.*;

public class Problem_9_Valid_Number {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    System.out.println("input = " + input);
    boolean soln_1 = is_numeric_1(input);

    System.out.println("Solution 1: " + soln_1);

  }

  /*
    This functions checks if the input string represents a valid decimal number
  */

  public static boolean is_numeric_1(String input) {
    int i = 0;
    while (i < input.length() && Character.isWhitespace(input.charAt(i))) {
      ++i;
    }

    if (i < input.length() && (input.charAt(i) == '+' || input.charAt(i) == '-')) {
      ++i;
    }
    boolean is_numeric = false;
    while (i < input.length() && Character.isDigit(input.charAt(i))) {
      ++i;
      is_numeric = true;
    }

    if (i < input.length() && input.charAt(i) == '.') {
      ++i;
    }

    while (i < input.length() && Character.isDigit(input.charAt(i))) {
      ++i;
      is_numeric = true;
    }

    while (i < input.length() && Character.isWhitespace(input.charAt(i))) {
      ++i;
    }
    return is_numeric && i == input.length();
  }
}