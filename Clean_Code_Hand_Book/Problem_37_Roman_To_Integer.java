import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_37_Roman_To_Integer {
  private static final int[] VALUE = {
    1000, 900, 500, 400,
     100,  90,  50,  40,
      10,   9,   5,   4,
       1
  };

  private static String[] ROMAN_SYMBOLS = {
    "M", "CM", "D", "CD",
    "C", "XC", "L", "XL",
    "X", "IX", "V", "IV",
    "I"
  };

  private static Map<Character, Integer> ROMAN_INT = new HashMap<Character, Integer>() {{
    put('I', 1); put('V', 5); put('X', 10);
    put('L', 50); put('C', 100); put('D', 500);
    put('M', 1000);
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
          int input = Integer.parseInt(line.trim());
          String roman_soln_1 = convert_int_to_roman_1(input);
          int int_soln_1 = convert_roman_to_int_1(roman_soln_1);

          System.out.println("Roman - Integer equivalence:");
          System.out.println("Solution 1: " + roman_soln_1 + " - " + int_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }   
  }

  private static String convert_int_to_roman_1(int n) {
    StringBuilder roman = new StringBuilder();
    int i = 0;
    while (n > 0 && i < VALUE.length) {
      int multiple = n/VALUE[i];
      for (int j = 1; j <= multiple; j++) {
        roman.append(ROMAN_SYMBOLS[i]);
        n -= VALUE[i];
      }
      i++;
    }
    return roman.toString();
  }

  private static int convert_roman_to_int_1(String roman) {
    int prev = 0;
    int total = 0;
    for (char c : roman.toCharArray()) {
      int val = ROMAN_INT.get(c);
      total += val + (val > prev ? -2*prev : 0);
      prev = val;
    }
    return total;
  }

  private static int num_of_digits(int n) {
    return (int)(Math.floor(Math.log10(n))) + 1;
  }
}