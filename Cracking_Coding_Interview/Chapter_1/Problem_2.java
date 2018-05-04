import java.util.*;
import java.io.*;

public class Problem_2 {
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
          
          String str_2 = shuffle(str);
          System.out.println("'" + str_2 + "' is permutation of '" + str + "'");

          boolean soln_1 = are_two_strings_permutations_1(str, str_2);
          boolean soln_2 = are_two_strings_permutations_2(str, str_2);

          System.out.println("Solution 1 = " + soln_1);
          System.out.println("Solution 2 = " + soln_2);

          if (soln_1 != soln_2) {
            System.out.println("FAILED. Solution 1 != Solution 2");
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

  private static boolean are_two_strings_permutations_1(String str_1, String str_2) {
    if (str_1.length() != str_2.length()) {
      return false;
    }

    HashMap<Character, Integer> char_count_str_1 = new HashMap<>();
    for (int i = 0; i < str_1.length(); i++) {
      char c = str_1.charAt(i);
      int count = char_count_str_1.containsKey(c) ? char_count_str_1.get(c) : 0;
      char_count_str_1.put(c, count + 1);
    }

    HashMap<Character, Integer> char_count_str_2 = new HashMap<>();
    for (int i = 0; i < str_2.length(); i++) {
      char c = str_2.charAt(i);
      int count = char_count_str_2.containsKey(c) ? char_count_str_2.get(c) : 0;
      char_count_str_2.put(c, count + 1);
    }

    for (char c : char_count_str_1.keySet()) {
      if (!char_count_str_2.containsKey(c) || char_count_str_2.get(c) != char_count_str_1.get(c)) {
        return false;
      }
    }
    return true;
  }

  private static boolean are_two_strings_permutations_2(String str_1, String str_2) {
    if (str_1.length() != str_2.length()) {
      return false;
    }

    int[] char_count_str_1 = new int[256];
    for (int i = 0; i < str_1.length(); i++) {
      char_count_str_1[str_1.charAt(i)]++;
    }

    int[] char_count_str_2 = new int[256];
    for (int i = 0; i < str_2.length(); i++) {
      char_count_str_2[str_2.charAt(i)]++;
    }

    for (int i = 0; i < char_count_str_1.length; i++) {
      if (char_count_str_1[i] != char_count_str_1[i]) {
        return false;
      }
    }
    return true;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    return line;
  }

  private static String shuffle(String str) {
    char[] copy = str.toCharArray();
    for (int i = 0; i < copy.length; i++) {
      int random_index = (int)(Math.random() * (copy.length - i) + i);
      char temp = copy[i];
      copy[i] = copy[random_index];
      copy[random_index] = temp;
    }

    return new String(copy);
  }
}