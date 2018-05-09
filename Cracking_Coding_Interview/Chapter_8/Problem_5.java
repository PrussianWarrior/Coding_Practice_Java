public class Problem_5 {
  public static void main(String[] args) {
    int n_1 = Integer.parseInt(args[0]);
    int n_2 = Integer.parseInt(args[1]);

    int multiply_1 = multiply_1(n_1, n_2);
    int multiply_2 = multiply_2(n_1, n_2);
    int multiply_3 = multiply_3(n_1, n_2);

    int multiply_4 = n_1 * n_2;

    System.out.println(n_1 + " multiplied by " + n_2 + " = ");
    System.out.println("Method 1 = " + multiply_1);
    System.out.println("Method 2 = " + multiply_2);
    System.out.println("Method 3 = " + multiply_3);

    if (multiply_1 != multiply_4) {
      System.out.println("Method 1 does not yield the correct result for the multiplication");
      return;
    }

    if (multiply_2 != multiply_4) {
      System.out.println("Method 2 does not yield the correct result for the multiplication");
      return;
    }

    if (multiply_3 != multiply_4) {
      System.out.println("Method 3 does not yield the correct result for the multiplication");
      return;
    }
  }

  private static int multiply_1(int n_1, int n_2) {
    int greater = Math.max(n_1, n_2);
    int smaller = Math.min(n_1, n_2);
    return multiply_1_helper(smaller, greater);
  }

  private static int multiply_1_helper(int smaller, int greater) {
    if (smaller == 0) {
      return 0;
    }
    if (smaller == 1) {
      return greater;
    }

    int half_smaller = smaller >> 1;
    int first_num = multiply_1_helper(half_smaller, greater);
    int second_num = smaller % 2 == 1 ? multiply_1_helper(smaller - half_smaller, greater) : first_num;
    return first_num + second_num;
  }

  private static int multiply_2(int n_1, int n_2) {
    int greater = Math.max(n_1, n_2);
    int smaller = Math.min(n_1, n_2);
    int[] memoized = new int[smaller+1];
    return multiply_2_helper(smaller, greater, memoized);
  }

  private static int multiply_2_helper(int smaller, int greater, int[] memoized) {
    if (smaller == 0) {
      return 0;
    }
    if (smaller == 1) {
      return greater;
    }
    if (memoized[smaller] > 0) {
      // System.out.println("Returning cached result for multiply_2_helper(" + 
      //     smaller + ", " + greater + ") = " + memoized[smaller]);
      return memoized[smaller];
    }

    int half_smaller = smaller >> 1;
    int first_num = multiply_2_helper(half_smaller, greater, memoized);
    int second_num = smaller % 2 == 1 ? multiply_2_helper(smaller - half_smaller, greater, memoized) : first_num;
    memoized[smaller] = first_num + second_num;
    return memoized[smaller];
  }

  private static int multiply_3(int n_1, int n_2) {
    int greater = Math.max(n_1, n_2);
    int smaller = Math.min(n_1, n_2);
    return multiply_3_helper(smaller, greater);
  }

  private static int multiply_3_helper(int smaller, int greater) {
    if (smaller == 0) {
      return 0;
    }
    if (smaller == 1) {
      return greater;
    }
    int half_smaller = smaller >> 1;
    int half = multiply_3_helper(half_smaller, greater);
    return half + half + (smaller % 2 == 1 ? greater : 0);
  }
}