import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_41_Valid_Parentheses {
  private static final Map<Character, Character> VALID_PARENTHSES_PAIR = new HashMap<Character, Character>() {{
    put('(', ')');
    put('[', ']');
    put('{', '}');
  }};

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          line = line.trim();

          boolean valid_parentheses_soln_1 = valid_parentheses_1(line);
          
          System.out.println("VALID PARENTHESES");
          System.out.println("Solution 1 = " + valid_parentheses_soln_1);
          
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static boolean valid_parentheses_1(String line) {
    LinkedList<Character> stack = new LinkedList<>();
    for (char c : line.toCharArray()) {
      if (VALID_PARENTHSES_PAIR.containsKey(c)) {
        stack.addFirst(c);
      } else if (stack.isEmpty() || VALID_PARENTHSES_PAIR.get(stack.removeFirst()) != c) {
        return false;
      }
    }
    return stack.isEmpty();
  }
}