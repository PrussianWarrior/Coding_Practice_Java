import java.util.*;
import java.io.*;

public class Problem_3 {
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
          
          String url_1 = turn_str_into_url_1(str);
          String url_2 = turn_str_into_url_2(str);
          
          System.out.println("Solution 1: " + url_1);
          System.out.println("Solution 2: " + url_2);

          if (!url_1.equals(url_2)) {
            System.out.println("SOLUTION 1 != SOLUTION 2");
            break;
          }

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static String turn_str_into_url_1(String str) {
    str = str.trim();
    StringBuilder url = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == ' ') {
        url.append("%20");
      } else {
        url.append(c);
      }
    }
    return url.toString();
  }

  private static String turn_str_into_url_2(String str) {
    str = str.trim();
    int num_of_spaces = 0;
    int num_of_char = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == ' ') {
        num_of_spaces++;
      } else {
        num_of_char++;
      }
    }

    char[] arr = new char[num_of_char + 3 * num_of_spaces];
    int index = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == ' ') {
        arr[index++] = '%';
        arr[index++] = '2';
        arr[index++] = '0';
      } else {
        arr[index++] = str.charAt(i);
      }
    }
    return new String(arr);
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    return line;
  }
}