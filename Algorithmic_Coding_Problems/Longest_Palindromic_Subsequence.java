import java.lang.*;
import java.util.*;
import java.io.*;

public class Longest_Palindromic_Subsequence {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);         
          String input = null;

          if (line.indexOf("Customized:") >= 0) {
            input = get_customized_string(line);
          } else if (line.indexOf("Random:") >= 0) {
            input = generate_random_str(line);
          } else {
            throw new IllegalArgumentException("The line must begin with either Customized: or Random:");
          }

          System.out.println("Input = " + input);
          int longest_palindromic_subsequence_1 = length_longest_palindromic_subsequence_1(input);

          int longest_palindromic_subsequence_2 = length_longest_palindromic_subsequence_2(input);          

          String longest_palindromic_subsequence_3 = length_longest_palindromic_subsequence_3(input);            
        
          System.out.println("Longest palindromic subsequence:");
          System.out.println("SOLUTION 1: " + longest_palindromic_subsequence_1);
          System.out.println("SOLUTION 2: " + longest_palindromic_subsequence_2);
          System.out.println("SOLUTION 3: " + longest_palindromic_subsequence_3.length() + " - " 
            + longest_palindromic_subsequence_3);                    

          if (longest_palindromic_subsequence_1 != longest_palindromic_subsequence_2 ||
              longest_palindromic_subsequence_1 != longest_palindromic_subsequence_3.length()) {
            System.out.println("FAILED");
            break;
          }

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
        br.close();
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int length_longest_palindromic_subsequence_1(String str) {
    return length_longest_palindromic_subsequence_1(str, 0, str.length() - 1);
  }

  private static int length_longest_palindromic_subsequence_1(String str, int i_1, int i_2) {
    if (i_1 > i_2) {
      return 0;
    }
    if (i_1 == i_2) {
      return 1;
    }

    return str.charAt(i_1) == str.charAt(i_2) ?
        2 + length_longest_palindromic_subsequence_1(str, i_1 + 1, i_2 - 1) : 
        Math.max(length_longest_palindromic_subsequence_1(str, i_1 + 1, i_2),
                 length_longest_palindromic_subsequence_1(str, i_1, i_2 - 1));
  }

  private static int length_longest_palindromic_subsequence_2(String str) {
    return length_longest_palindromic_subsequence_2(str, new HashMap<String, Integer>(), 0, str.length() - 1);
  }

  private static int length_longest_palindromic_subsequence_2(String str, Map<String, Integer> cache, 
      int i_1, int i_2) {
    if (i_1 > i_2) {
      return 0;
    }
    if (i_1 == i_2) {
      return 1;
    }
    String key = i_1 + "-" + i_2;
    if (cache.containsKey(key)) {
      // System.out.println("Returning cached result for key = " + key);
      return cache.get(key);
    }
    int subproblem_result = str.charAt(i_1) == str.charAt(i_2) ?
      2 + length_longest_palindromic_subsequence_2(str, cache, i_1 + 1, i_2 - 1) :
      Math.max(length_longest_palindromic_subsequence_2(str, cache, i_1 + 1, i_2),
               length_longest_palindromic_subsequence_2(str, cache, i_1, i_2 - 1));
    cache.put(key, subproblem_result);
    return subproblem_result;
  }

  private static String length_longest_palindromic_subsequence_3(String str) {
    if (str.isEmpty()) {
      return "";
    }
    String reverseStr = new StringBuilder(str).reverse().toString();
    
    int len_1 = str.length();
    int len_2 = reverseStr.length();
    int[][] soln_subproblem = new int[len_1 + 1][len_2 + 1];
    for (int i_1 = 1; i_1 <= len_1; i_1++) {
      for (int i_2 = 1; i_2 <= len_2; i_2++) {        
        soln_subproblem[i_1][i_2] = str.charAt(i_1 - 1) == reverseStr.charAt(i_2 - 1) ? 
          1 + soln_subproblem[i_1 - 1][i_2 - 1] :
          Math.max(soln_subproblem[i_1 - 1][i_2], soln_subproblem[i_1][i_2 - 1]);
      }
    }
    // System.out.printf("%5s", " ");
    // for (int c = 0; c <= len_2; c++) {
    //   System.out.printf("%5d", c);
    // }
    // System.out.println();

    // for (int r = 0; r <= len_1; r++) {
    //   System.out.printf("%5d", r);
    //   for (int c = 0; c <= len_2; c++) {
    //     System.out.printf("%5d", soln_subproblem[r][c]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();

    char[] temp = new char[soln_subproblem[len_1][len_2]];
    int i = temp.length - 1;
    int i_1 = len_1 - 1;
    int i_2 = len_2 - 1;
    while (i >= 0 && i_1 >= 0 && i_2 >= 0) {
      if (str.charAt(i_1) == reverseStr.charAt(i_2)) {
        temp[i--] = str.charAt(i_1);
        i_1--;
        i_2--;
      } else if (soln_subproblem[i_1 + 1][i_2 + 1] == soln_subproblem[i_1 + 1][i_2]) {
        i_2--;
      } else if (soln_subproblem[i_1 + 1][i_2 + 1] == soln_subproblem[i_1][i_2 + 1]) {
        i_1--;
      }
    }
    return new String(temp);
  }

  private static String generate_random_str(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    int len = Integer.parseInt(line);
    StringBuilder str = new StringBuilder();
    for (int i = 1; i <= len; i++) {
      str.append((char)('a' + (int)(Math.random() * 26)));
    }
    return str.toString();
  }

  private static String get_customized_string(String line) {
    int index_of_colon = line.indexOf(":");
    return line = line.substring(index_of_colon + 1).trim();    
  }
}