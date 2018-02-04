import java.lang.*;
import java.util.*;

public class Problem_8_strtoi {
  static final int MAX = Integer.MAX_VALUE/10;

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    int soln_1 = str2i_1(input);
    System.out.println("Solution 1: " + soln_1);

  }

  public static int str2i_1(String input) {
    int i = 0;
    while (i < input.length() && Character.isWhitespace(input.charAt(i))) {
      ++i;
    }
    int sign = 1;
    if (i < input.length() && (input.charAt(i) == '+' || input.charAt(i) == '-')) {
      sign = input.charAt(i) == '+' ? 1 : -1;
      ++i;
    }
    System.out.println("sign = " + sign);

    int result = 0;
    while (i < input.length() && Character.isDigit(input.charAt(i))) {
      int digit = Character.getNumericValue(input.charAt(i));
      if (result > MAX || (result == MAX && digit >= 8)) {
        break;
      }
      result = result * 10 + digit;
      ++i;
    }
    return sign*result;
  }
}