import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_40_Evaluate_Reverse_Polish_Notation {
  private interface Operator {
    public int eval(int op_1, int op_2);
  }

  private static final Map<String, Operator> OPERATORS = new HashMap<String, Operator>() {{
    put("+", new Operator() {
      @Override
      public int eval(int op_1, int op_2) {
        return op_1 + op_2;
      }
    });

    put("-", new Operator() {
      @Override
      public int eval(int op_1, int op_2) {
        return op_1 - op_2;
      }
    });

    put("*", new Operator() {
      @Override
      public int eval(int op_1, int op_2) {
        return op_1 * op_2;
      }
    });

    put("/", new Operator() {
      @Override
      public int eval(int op_1, int op_2) {
        if (op_2 == 0) {
          throw new IllegalArgumentException("ERROR. The divisor cannot be zero.");
        }
        return op_1 / op_2;
      }
    });    
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
          String[] input = process_input(line);
          
          int rpn_evaluation_soln_1 = evaluate_RPN_1(input);
          
          System.out.println("RPN EVALUATION");
          System.out.println("Solution 1 = " + rpn_evaluation_soln_1);
          
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int evaluate_RPN_1(String[] tokens) {
    LinkedList<Integer> operands = new LinkedList<>();
    for (String token : tokens) {
      if (OPERATORS.containsKey(token)) {
        int op_2 = operands.removeFirst();
        int op_1 = operands.removeFirst();
        operands.addFirst(OPERATORS.get(token).eval(op_1, op_2));
      } else {
        operands.addFirst(Integer.parseInt(token));
      }
    }

    return operands.removeFirst();
  }

  private static String[] process_input(String line) {
    String[] words = line.trim().split(" ");
    for (int i = 0; i < words.length; i++) {
      words[i] = words[i].trim();
    }
    return words;
  }
}