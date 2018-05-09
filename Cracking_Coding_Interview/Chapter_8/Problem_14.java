import java.util.*;

public class Problem_14 {
  public static void main(String[] args) {
    String exp = args[0];
    boolean value = Boolean.valueOf(args[1]);
    
    int num_of_ways_of_parenthesizing_1 = find_num_of_ways_parenthesizing_1(exp, value);
    int num_of_ways_of_parenthesizing_2 = find_num_of_ways_parenthesizing_2(exp, value);

    System.out.println("Number of ways of parenthesizing '" + exp + "' such that it evaluates to " + value);
    System.out.println("Method 1: " + num_of_ways_of_parenthesizing_1);
    System.out.println("Method 2: " + num_of_ways_of_parenthesizing_2);

    if (num_of_ways_of_parenthesizing_1 != num_of_ways_of_parenthesizing_2) {
      System.out.println("find_num_of_ways_parenthesizing_1 does not yield the same result " + 
        "as find_num_of_ways_parenthesizing_2");
    }
  }

  private static int find_num_of_ways_parenthesizing_1(String exp, boolean value) {
    if (exp.isEmpty()) {
      return 0;
    }
    if (exp.length() == 1) {
      if (value) {
        return exp.equals("1") ? 1 : 0;
      } else {
        return exp.equals("0") ? 1 : 0;
      }
    }

    int total = 0;
    if (value) {
      for (int i = 1; i < exp.length(); i += 2) {
        char op = exp.charAt(i);
        switch (op) {
          case '&':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) * 
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            break;
          case '|':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);   
            break;
          case '^':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            break;
        }
      }
    } else {
      for (int i = 1; i < exp.length(); i += 2) {
        char op = exp.charAt(i);
        switch (op) {
          case '&':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);
            break;
          case '|':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);
            break;
          case '^':
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), false) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), false);
            total += find_num_of_ways_parenthesizing_1(exp.substring(0, i), true) *
                     find_num_of_ways_parenthesizing_1(exp.substring(i + 1), true);
            break;
        }
      }
    }

    return total;
  }

  private static int find_num_of_ways_parenthesizing_2(String exp, boolean value) {
    return find_num_of_ways_parenthesizing_2(exp, value, new HashMap<>());
  }

  private static int find_num_of_ways_parenthesizing_2(String exp, boolean value, Map<String, Integer> memoized) {
    if (exp.isEmpty()) {
      return 0;
    }
    if (exp.length() == 1) {
      if (value) {
        return exp.equals("1") ? 1 : 0;
      } else {
        return exp.equals("0") ? 1 : 0;
      }
    }

    String key = exp + "-" + Boolean.toString(value);
    if (memoized.containsKey(key)) {
      // System.out.println("Returning cached result for '" + key + "'");
      return memoized.get(key);
    }

    int total = 0;
    if (value) {
      for (int i = 1; i < exp.length(); i += 2) {
        char op = exp.charAt(i);
        switch (op) {
          case '&':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            break;
          case '|':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            break;
          case '^':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            break;
        }
      }
    } else {
      for (int i = 1; i < exp.length(); i += 2) {
        char op = exp.charAt(i);
        switch (op) {
          case '&':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            break;
          case '|':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            break;
          case '^':
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), true, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), true, memoized);
            total += find_num_of_ways_parenthesizing_2(exp.substring(0, i), false, memoized) *
                     find_num_of_ways_parenthesizing_2(exp.substring(i + 1), false, memoized);
            break;
        }
      }
    }

    memoized.put(key, total);
    return total;
  }
}