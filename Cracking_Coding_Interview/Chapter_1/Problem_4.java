import java.util.*;
import java.io.*;

public class Problem_4 {
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
          
          str = shuffle_letters(str);
          System.out.println("'" + str + "' is a permutation of a palindrome");
          
          boolean soln_1 = is_str_a_permutation_of_a_palindrome_1(str);
          boolean soln_2 = is_str_a_permutation_of_a_palindrome_2(str);

          System.out.println("Solution 1: " + soln_1);
          System.out.println("Solution 2: " + soln_2);

          if (soln_1 != soln_2) {
            System.out.println("FAILED! Solution 1 != Solution 2") ;
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

  private static boolean is_str_a_permutation_of_a_palindrome_1(String str) {
    str = str.toLowerCase();
    Map<Character, Integer> char_count = new HashMap<>();
    int num_of_odd_letter = 0;

    for (int i = 0; i < str.length(); i++) {
      int count = char_count.containsKey(str.charAt(i)) ? char_count.get(str.charAt(i)) : 0;
      char_count.put(str.charAt(i), count + 1);
    }

    for (char c : char_count.keySet()) {
      if (char_count.get(c) % 2 == 1) {
        num_of_odd_letter++;
      }
    }

    return num_of_odd_letter <= 1;
  }

  private static boolean is_str_a_permutation_of_a_palindrome_2(String str) {
    str = str.toLowerCase();
    int[] char_count = new int[256];
    int num_of_odd_letter = 0;

    for (int i = 0; i < str.length(); i++) {
      char_count[str.charAt(i)]++;
    }

    for (int n : char_count) {
      if (n % 2 == 1) {
        num_of_odd_letter++;
      }
    }

    return num_of_odd_letter <= 1;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    return line;
  }

  private static String shuffle_letters(String str) {
    char[] copy = str.toCharArray();
    for (int i = 0; i < str.length(); i++) {
      int random_index = (int)(Math.random() * (copy.length - i) + i);
      char temp = copy[i];
      copy[i] = copy[random_index];
      copy[random_index] = temp;
    }
    return new String(copy);
  }
}