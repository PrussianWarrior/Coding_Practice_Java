import java.util.*;
import java.io.*;

public class Problem_6 {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;

        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String str = "";
          if (line.indexOf("Customized:") >= 0) {
            str = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("Compressed string of '" + str + "':");
          String soln_1 = compress_str_1(str);

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

  private static String compress_str_1(String str) {
    if (str.isEmpty()) {
      throw new IllegalArgumentException("Input string is empty. It cannot be empty");
    }

    StringBuilder compressed_str = new StringBuilder();
    int count = 1;
    char current_char = str.charAt(0);
    
    for (int i = 1; i < str.length(); i++) {
      if (str.charAt(i) == current_char) {
        count++;
      } else {
        compressed_str.append(current_char).append(count);
        count = 1;
        current_char = str.charAt(i);
      }
    }

    compressed_str.append(current_char).append(count);
    return compressed_str.length() >= str.length() ? str : compressed_str.toString();
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }
}