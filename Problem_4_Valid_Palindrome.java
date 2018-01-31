import java.lang.*;
import java.util.*;

public class Problem_4_Valid_Palindrome {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    boolean keep_running = true;
    do {
      String str = in.next();
      boolean soln_1 = solution_1(str);
      System.out.println("String '" + str + "'" + (soln_1 ? " is a palindrome" : " is not a palindrome"));
      System.out.println("Type Y/y to continue. Type anything else to quit");
      char choice = in.next().charAt(0);
      keep_running = choice == 'Y' || choice == 'y';
    } while (keep_running);

  }

  public static boolean solution_1(String A) {
    for (int left_ptr = 0, right_ptr = A.length()-1; left_ptr < right_ptr;) {
      while (left_ptr < right_ptr && !Character.isLetterOrDigit(A.charAt(left_ptr))) left_ptr++;
      while (left_ptr < right_ptr && !Character.isLetterOrDigit(A.charAt(right_ptr))) right_ptr--;
      if (Character.toLowerCase(A.charAt(left_ptr)) != 
          Character.toLowerCase(A.charAt(right_ptr))) {
        return false;
      }
      left_ptr++;
      right_ptr--;
    }
    return true;
  }

  private static int[] rand_arr_generator(int L, int min, int max) {
    // Random rand_generator = new Random();
    int[] rand_arr = new int[L];
    for (int i = 0; i < L; i++) {
      rand_arr[i] = (int)(Math.random() * (max - min + 1)) + min;
    }
    return rand_arr;
  }

  private static void print(int[] A) {
    for (int i = 0; i < A.length; i++) {
      System.out.printf("%5d", A[i]);
      if ((i + 1) % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
  }

  private static boolean isSorted(int[] A) {
    assert A != null;
    for (int i = 1; i < A.length; i++) {
      if (A[i] < A[i-1]) {
        return false;
      }
    }
    return true;
  }
}