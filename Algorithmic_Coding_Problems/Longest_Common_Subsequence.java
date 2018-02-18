import java.lang.*;
import java.util.*;
import java.io.*;

public class Longest_Common_Subsequence {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);         
          List<String> input = null;

          if (line.indexOf("Customized:") >= 0) {
            input = get_customized_string(line);
          } else if (line.indexOf("Random:") >= 0) {
            input = generate_random_str(line);
          } else {
            throw new IllegalArgumentException("The line must begin with either Customized: or Random:");
          }

          System.out.println("String 1 = " + input.get(0));
          System.out.println("String 2 = " + input.get(1));

          int longest_common_subsequence_1 = length_longest_common_subsequence_1(
            input.get(0), input.get(1));

          int longest_common_subsequence_2 = length_longest_common_subsequence_2(
            input.get(0), input.get(1));

          String longest_common_subsequence_3 = length_longest_common_subsequence_3(
            input.get(0), input.get(1));
        
          System.out.println("Longest common subsequence:");
          System.out.println("SOLUTION 1: " + longest_common_subsequence_1);
          System.out.println("SOLUTION 2: " + longest_common_subsequence_2);
          System.out.println("SOLUTION 3: " + longest_common_subsequence_3.length() + " - " 
            + longest_common_subsequence_3);                    

          if (longest_common_subsequence_1 != longest_common_subsequence_2 ||
              longest_common_subsequence_1 != longest_common_subsequence_3.length()) {
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

  private static int length_longest_common_subsequence_1(String str_1, String str_2) {
    return length_longest_common_subsequence_1(str_1, str_2, str_1.length() - 1, str_2.length() - 1);
  }

  private static int length_longest_common_subsequence_1(String str_1, String str_2, int i_1, int i_2) {
    if (i_1 < 0 || i_2 < 0) {
      return 0;
    }
    return str_1.charAt(i_1) == str_2.charAt(i_2) ?
        1 + length_longest_common_subsequence_1(str_1, str_2, i_1 - 1, i_2 - 1) : 
        Math.max(length_longest_common_subsequence_1(str_1, str_2, i_1 - 1, i_2),
                 length_longest_common_subsequence_1(str_1, str_2, i_1, i_2 - 1));
  }

  private static int length_longest_common_subsequence_2(String str_1, String str_2) {
    return length_longest_common_subsequence_2(str_1, str_2, new HashMap<String, Integer>(),
      str_1.length() - 1, str_2.length() - 1);
  }

  private static int length_longest_common_subsequence_2(String str_1, String str_2, 
    Map<String, Integer> cache, int i_1, int i_2) {
    if (i_1 < 0 || i_2 < 0) {
      return 0;
    }
    String key = i_1 + "-" + i_2;
    if (cache.containsKey(key)) {
      // System.out.println("Returning cached result for key = " + key);
      return cache.get(key);
    }
    int subproblem_result = str_1.charAt(i_1) == str_2.charAt(i_2) ?
      1 + length_longest_common_subsequence_2(str_1, str_2, cache, i_1 - 1, i_2 - 1) :
      Math.max(length_longest_common_subsequence_2(str_1, str_2, cache, i_1 - 1, i_2),
               length_longest_common_subsequence_2(str_1, str_2, cache, i_1, i_2 - 1));

    cache.put(key, subproblem_result);
    return subproblem_result;
  }

  private static String length_longest_common_subsequence_3(String str_1, String str_2) {
    if (str_1.isEmpty() || str_2.isEmpty()) {
      return "";
    }
    int len_1 = str_1.length();
    int len_2 = str_2.length();
    int[][] soln_subproblem = new int[len_1 + 1][len_2 + 1];  
    for (int i_1 = 1; i_1 <= len_1; i_1++) {
      for (int i_2 = 1; i_2 <= len_2; i_2++) {      
        soln_subproblem[i_1][i_2] = str_1.charAt(i_1 - 1) == str_2.charAt(i_2 - 1) ? 
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
      if (str_1.charAt(i_1) == str_2.charAt(i_2)) {
        temp[i--] = str_1.charAt(i_1);
        i_1--;
        i_2--;
      } else if (soln_subproblem[i_1 + 1][i_2 + 1] == soln_subproblem[i_1][i_2 + 1]) {
        i_1--;
      } else if (soln_subproblem[i_1 + 1][i_2 + 1] == soln_subproblem[i_1 + 1][i_2]) {
        i_2--;
      }
    }
    return new String(temp);
  }

  private static List<String> generate_random_str(String line) {
    List<String> random_str = new ArrayList<>();
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] len_pair = line.split(" ");
    for (String len_str : len_pair) {
      int len = Integer.parseInt(len_str.trim());
      StringBuilder str = new StringBuilder();
      for (int i = 1; i <= len; i++) {
        str.append((char)('a' + (int)(Math.random() * 26)));
      }
      random_str.add(str.toString());
    }
    return random_str;
  }

  private static List<String> get_customized_string(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    List<String> customized_strings = new ArrayList<>();
    for (String str : line.split(" ")) {
      customized_strings.add(str.trim());
    }
    return customized_strings;
  }
}