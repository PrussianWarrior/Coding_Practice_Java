import java.util.*;

public class Problem_9 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    List<String> all_parentheses = generate_valid_parentheses_1(N);

    System.out.println("All valid parentheses with N = " + N);
    System.out.println("Method 1:");
    for (int i = 0; i < all_parentheses.size(); i++) {
      boolean valid = is_valid_parentheses(all_parentheses.get(i));;
      System.out.printf("%5d: %s\n", i + 1, all_parentheses.get(i) + ", valid = " + valid);
      if (!valid) {
        System.out.println("The parentheses expression is invalid.");
        break;
      }
    }
    System.out.println();
  }

  private static List<String> generate_valid_parentheses_1(int N) {
    char[] temp = new char[2*N];
    List<String> all_parentheses = new ArrayList<>();
    generate_valid_parentheses_1(N, N, temp, 0, all_parentheses);
    return all_parentheses;
  }

  private static void generate_valid_parentheses_1(int num_of_opening_parentheses, int num_of_closing_parentheses, 
      char[] temp, int index, List<String> all_parentheses) {
    if (num_of_opening_parentheses == 0 && num_of_closing_parentheses == 0) {
      all_parentheses.add(new String(temp));
      return;
    }
    if (num_of_opening_parentheses < 0 || (num_of_opening_parentheses > num_of_closing_parentheses)) {
      return;
    }

    if (num_of_opening_parentheses > 0) {
      temp[index] = '(';
      generate_valid_parentheses_1(num_of_opening_parentheses - 1, num_of_closing_parentheses, temp, 
        index + 1, all_parentheses);
    }

    if (num_of_closing_parentheses > 0) {
      temp[index] = ')';
      generate_valid_parentheses_1(num_of_opening_parentheses, num_of_closing_parentheses - 1, temp,
        index + 1, all_parentheses);
    }
  }

  private static boolean is_valid_parentheses(String str) {
    LinkedList<Character> stack = new LinkedList<>();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '(') {
        stack.add('(');
      } else if (str.charAt(i) == ')') {
        if (stack.isEmpty()) {
          return false;
        }
        stack.removeLast();
      } else {
        return false;
      }
    }
    return stack.isEmpty();
  }
}