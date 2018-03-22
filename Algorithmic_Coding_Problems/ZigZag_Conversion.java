import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: 
  (you may want to display this pattern in a fixed font for better legibility)
 
  P   A   H   N
  A P L S I I G
  Y   I   R

  And then read line by line: "PAHNAPLSIIGYIR"
  Write the code that will take a string and make this conversion given a number of rows:
  string convert(string text, int nRows);

  convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR".
*/

public class ZigZag_Conversion {
  private static class String_Row {
    String str;
    int row;

    public String_Row(String str, int row) {
      this.str = str;
      this.row = row;
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

          String_Row str_row = null;
          if (line.indexOf("Customized:") >= 0) {
            str_row = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          String zigzag_str_1 = convert_zigzag_pattern_1(str_row.str, str_row.row);
          long stop = System.currentTimeMillis();
          long convert_to_zigzag_execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          String original_str_1 = recover_original_string_1(zigzag_str_1, str_row.row);
          stop = System.currentTimeMillis();
          long convert_to_original_execution_time_soln_1 = stop - start;

          // start = System.currentTimeMillis();
          // String zigzag_str_2 = convert_zigzag_pattern_2(str_row.str, str_row.row);
          // stop = System.currentTimeMillis();
          // long convert_to_zigzag_execution_time_soln_2 = stop - start;

          System.out.println("ZIGZAG STRING");
          System.out.println("METHOD 1: " + zigzag_str_1 + ", execution time = " + convert_to_zigzag_execution_time_soln_1);
          // System.out.println("METHOD 2: " + zigzag_str_2 + ", execution time = " + convert_to_zigzag_execution_time_soln_2);

          // if (!zigzag_str_1.equals(zigzag_str_2)) {
          //   System.out.println("convert_zigzag_pattern_1 and convert_zigzag_pattern_2 do not yield the same result");
          //   break;
          // }

          System.out.println("RECOVER ORIGINAL STRING");
          System.out.println("METHOD 1: " + original_str_1 + ", execution time = " + convert_to_original_execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static String convert_zigzag_pattern_1(String str, int num_rows) {
    if (num_rows == 1) {
      return str;
    }

    StringBuilder zigzag_str = new StringBuilder();
    int step = 2 * num_rows - 2;
    for (int i = 0; i < num_rows; i++) {
      if (i == 0 || i == num_rows - 1) {
        for (int j = i; j < str.length(); j+= step) {
          zigzag_str.append(str.charAt(j));
        }
      } else {
        int j = i;
        boolean use_step_1 = true;
        int step_1 = 2 * (num_rows - i - 1);
        int step_2 = step - step_1;
        while (j < str.length()) {
          zigzag_str.append(str.charAt(j));
          j += use_step_1 ? step_1 : step_2;
          use_step_1 = !use_step_1;
        }
      }
    }

    return zigzag_str.toString();
  }

  private static String convert_zigzag_pattern_2(String str, int num_row) {
    if (num_row == 1) {
      return str;
    }

    StringBuilder[] str_rows = new StringBuilder[num_row];
    Arrays.fill(str_rows, new StringBuilder());
    int i = 0;
    while (i < str.length()) {
      for (int row = 0; row < num_row && i < str.length(); row++) {
        str_rows[row].append(str.charAt(i++));
        System.out.println(str_rows[row].toString());
      }
      for (int row = num_row - 2; row >= 1 && i < str.length(); row--) {
        str_rows[row].append(str.charAt(i++));
      }
    }

    for (StringBuilder str_builder : str_rows) {
      System.out.println(str_builder.toString());
    }

    for (int j = 1; j < num_row; j++) {
      str_rows[0].append(str_rows[j]);
    }

    return str_rows[0].toString();
  }

  private static String recover_original_string_1(String zigzag_str, int num_row) {
    if (num_row == 1) {
      return zigzag_str;
    }

    int index = 0;
    int step = 2 * (num_row - 1);
    char[] original_str = new char[zigzag_str.length()];
    for (int i = 0; i < num_row; i++) {
      if (i == 0 || i == num_row - 1) {
        for (int j = i; j < zigzag_str.length() && index < zigzag_str.length(); j+= step) {
          original_str[j] = zigzag_str.charAt(index++);
        }
      } else {
        int j = i;
        boolean use_step_1 = true;
        int step_1 = 2 * (num_row - i - 1);
        int step_2 = step - step_1;
        while (j < zigzag_str.length() && index < zigzag_str.length()) {
          original_str[j] = zigzag_str.charAt(index++);
          j += use_step_1 ? step_1 : step_2;
          use_step_1 = !use_step_1;
        }
      }
    }

    return new String(original_str);
  }

  private static String_Row get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    int index_of_space = line.indexOf(" ");
    String str = line.substring(0, index_of_space);
    int row = Integer.parseInt(line.substring(index_of_space + 1));
    return new String_Row(str, row);
  }
}