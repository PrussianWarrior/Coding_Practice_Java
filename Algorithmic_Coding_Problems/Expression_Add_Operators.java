import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a string that contains only digits 0-9 and a target value, return all possibilities to 
  add binary operators (not unary) +, -, or *between the digits so they evaluate to the target 
  value.
 
  Examples: 
  "123", 6 -> ["1+2+3", "1*2*3"] 
  "232", 8 -> ["2*3+2", "2+3*2"]
  "105", 5 -> ["1*0+5","10-5"]
  "00", 0 -> ["0+0", "0-0", "0*0"]
  "3456237490", 9191 -> []
*/

public class Expression_Add_Operators {
  private static class String_Num {
    String str;
    int num;

    public String_Num(String str, int num) {
      this.str = str;
      this.num = num;
    }
  }

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String_Num str_num = null;
          if (line.indexOf("Customized:") >= 0) {
            str_num = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<String> all_expressions_1 = find_all_expressions_1(str_num.str, str_num.num);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("ALL EXPRESSIONS EVALUATING TO " + str_num.num);
          System.out.println("Method 1:");
          print_all_expressions(all_expressions_1);
          System.out.println("Execution time = " + execution_time_soln_1);
          System.out.println();

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static List<String> find_all_expressions_1(String str, int num) {
    List<String> all_expressions = new ArrayList<>();
    find_all_expressions_1(str, num, "", 0, 0, all_expressions);
    return all_expressions;
  }

  private static void find_all_expressions_1(String str, int num, String expression, long curr_result, long prev_num, 
      List<String> all_expressions) {
    if (str.isEmpty()) {
      if (curr_result == num) {
        all_expressions.add(expression);
      }
      return;
    }

    for (int i = 1; i <= str.length(); i++) {
      String substr = str.substring(0, i);
      if (substr.length() > 1 && substr.charAt(0) == '0') {
        return;
      }
      long curr_num = Long.parseLong(substr);
      if (expression.isEmpty()) {
        find_all_expressions_1(str.substring(i), num, substr, curr_num, curr_num, all_expressions);
      } else {
        find_all_expressions_1(str.substring(i), num, expression + "*" + curr_num, (curr_result - prev_num) + prev_num * curr_num,
          prev_num * curr_num, all_expressions);
        find_all_expressions_1(str.substring(i), num, expression + "+" + curr_num, curr_result + curr_num, curr_num, 
          all_expressions);
        find_all_expressions_1(str.substring(i), num, expression + "-" + curr_num, curr_result - curr_num, -curr_num, 
          all_expressions);
      }
    }
  }

  private static String_Num get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces(line.substring(index_of_colon + 1));
    String[] temp = line.split(" ");
    return new String_Num(temp[0], Integer.parseInt(temp[1]));
  }

  private static String remove_extra_spaces(String line) {
    line = line.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (Character.isSpace(line.charAt(i))) {
        start = i;
      } else if (i == line.length() - 1 || Character.isSpace(line.charAt(i + 1))) {
        new_str.append(line.substring(start + 1, i + 1));
        if (i < line.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }

  private static void print_all_expressions(List<String> all_expressions) {
    for (int i = 0; i < all_expressions.size(); i++) {
      System.out.printf("%5d. %s\n", i + 1, all_expressions.get(i));
    }
    System.out.println();
  }
}