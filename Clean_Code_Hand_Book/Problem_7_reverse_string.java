import java.lang.*;
import java.util.*;

public class Problem_7_reverse_string {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    String soln_1 = reverse_1(input);
    // String soln_2 = reverse_2(input);

    System.out.println(input + " : " + input.length());
    System.out.println("Solution 1: " + soln_1 + " : " + soln_1.length());
    // System.out.println("Solution 2: " + soln_2 + " : " + soln_2.length());
  }

  public static String reverse_1(String input) {
    char[] temp = input.toCharArray();
    reverse(temp, 0, temp.length - 1);
    // int start = 0;
    int start = -1;
    for (int i = 0; i < temp.length; i++) {
      if (i + 1 == temp.length || Character.isSpace(temp[i + 1])) {
        // reverse(temp, start, i);
        // start = i + 2;
        reverse(temp, start + 1, i);
        start = i + 1;
      }
    }
    return new String(temp);
  }

  private static void reverse(char[] input, int start, int end) {
    for (int i = start, j = end; i < j; i++, j--) {
      char temp = input[i];
      input[i] = input[j];
      input[j] = temp;
    }
  }
}