import java.lang.*;
import java.util.*;

public class Problem_6_reverse_string {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    String soln_1 = reverse_1(input);
    String soln_2 = reverse_2(input);

    System.out.println(input + " : " + input.length());
    System.out.println("Solution 1: " + soln_1 + " : " + soln_1.length());
    System.out.println("Solution 2: " + soln_2 + " : " + soln_2.length());
  }

  /*
    This function preserves all the white spaces between words in the string
  */
  public static String reverse_1(String input) {
    StringBuilder reverse = new StringBuilder();
    int right_bnd = input.length();
    for (int i = input.length() - 1; i >= 0; --i) {
      if (Character.isSpace(input.charAt(i))) {
        reverse.append(' ');
        right_bnd = i;
      } else if (i == 0 || Character.isSpace(input.charAt(i - 1))) {
        reverse.append(input.substring(i, right_bnd));
      }
    }
    return reverse.toString();
  }

  /*
    This function eliminates all but 1 space between words in the input string
  */

  public static String reverse_2(String input) {
    StringBuilder reverse = new StringBuilder();
    int right_bnd = input.length();
    for (int i = input.length() - 1; i >= 0; --i) {
      if (Character.isSpace(input.charAt(i))) {
        right_bnd = i;
      } else if (i == 0 || Character.isSpace(input.charAt(i - 1))) {
        if (reverse.length() > 0) {
          reverse.append(' ');
        }
        reverse.append(input.substring(i, right_bnd));
      }
    }
    return reverse.toString();
  }
}