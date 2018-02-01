import java.lang.*;
import java.util.*;

public class Problem_5_strstr {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String needle = in.nextLine();
    String haystack = in.nextLine();
    int soln_1 = solution_1(needle, haystack);
    System.out.println("Solution 1 = " + soln_1);
  }

  public static int solution_1(String needle, String haystack) {
    for (int i_haystack = 0; ; ++i_haystack) {
      for (int i_needle = 0; ; ++i_needle) {
        if (i_needle == needle.length()) {
          return i_haystack;
        }
        if (i_needle + i_haystack == haystack.length()) {
          return -1;
        }
        if (needle.charAt(i_needle) != haystack.charAt(i_needle + i_haystack)) {
          break;
        }
      }
    }
  }

  public static int solution_2(String needle, String haystack) {
    return 1;
  }
}