import java.util.*;
import java.io.*;

public class Problem_5 {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String[] words = null;
          if (line.indexOf("Customized:") >= 0) {
            words = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }
          
          String longer_str = words[0].length() >= words[1].length() ? words[0] : words[1];
          String shorter_str = words[0].length() < words[1].length() ? words[0] : words[1];

          System.out.println("'" + shorter_str + "' is one-edit-distance away from '" + longer_str + "'");
          boolean soln_1 = is_str_2_one_edit_distance_from_str_1(longer_str, shorter_str);

          System.out.println("Solution 1: " + soln_1);
          
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static boolean is_str_2_one_edit_distance_from_str_1(String str_1, String str_2) {
    if (str_2.length() > str_1.length()) {
      return is_str_2_one_edit_distance_from_str_1(str_2, str_1);
    }

    int diff_len = str_1.length() - str_2.length();
    if (diff_len > 1) {
      return false;
    }
    int index = 0;
    while (index < str_2.length() && str_1.charAt(index) == str_2.charAt(index)) {
      index++;
    }

    if (index == str_2.length()) {
      return diff_len == 1;
    }

    if (diff_len == 0) {
      index++;
    }

    while (index < str_2.length() && str_1.charAt(index + diff_len) == str_2.charAt(index)) {
      index++;
    }
    return index == str_2.length();
  }

  private static String[] get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces(line.substring(index_of_colon + 1).trim());
    return line.split(" ");
  }

  private static String remove_extra_spaces(String line) {
    line = line.trim();
    StringBuilder extra_space_removed = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == ' ') {
        start = i;
      } else if (i + 1 == line.length() || line.charAt(i + 1) == ' ') {
        extra_space_removed.append(line.substring(start + 1, i + 1));
        if (i + 1 < line.length()) {
          extra_space_removed.append(" ");
        }
      }
    }

    return extra_space_removed.toString();
  }
}