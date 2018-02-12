import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_36_Integer_To_Roman {
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
          
          System.out.println("Roman equivalent:");
          System.out.println("Solution 1: " + roman_soln_1);
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

  private static int num_of_digits(int n) {
    return (int)(Math.floor(Math.log10(n))) + 1;
  }
}