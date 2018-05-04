import java.util.*;
import java.io.*;

public class Problem_1 {
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
          
          System.out.println("The characters in '" + str + "' are unique");
          boolean soln_1 = are_all_characters_unique_1(str);
          boolean soln_2 = are_all_characters_unique_2(str);

          System.out.println("Solution 1 = " + soln_1);
          System.out.println("Solution 2 = " + soln_2);

          if (soln_1 != soln_2) {
            System.out.println("FAILED. Solution 1 = Solution 2");
            break;
          }
          System.out.println("");
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static boolean are_all_characters_unique_1(String str) {
    str = str.toLowerCase();
    Set<Character> already_seen_character = new HashSet<>();
    for (int i = 0; i < str.length(); i++) {
      if (already_seen_character.contains(str.charAt(i))) {
        return false;
      }
      already_seen_character.add(str.charAt(i));
    }
    return true;
  }

  private static boolean are_all_characters_unique_2(String str) {
    str = str.toLowerCase();
    boolean[] already_seen_character = new boolean[256];
    for (int i = 0; i < str.length(); i++) {
      if (already_seen_character[str.charAt(i)]) {
        return false;
      }
      already_seen_character[str.charAt(i)] = true;
    }

    return true;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    return line;
  }
}