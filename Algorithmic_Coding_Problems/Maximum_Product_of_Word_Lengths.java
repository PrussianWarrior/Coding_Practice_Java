import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where 
  the two words do not share common letters. You may assume that each word will contain only 
  lowercase letters. If no such two words exist, return 0.
 
  Example 1:
  Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
  Return 16
  The two words can be "abcw", "xtfn".
   
  Example 2:
  Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
  Return 4
  The two words can be "ab", "cd".
   
  Example 3:
  Given ["a", "aa", "aaa", "aaaa"]
  Return 0
  No such pair of words.
*/

public class Maximum_Product_of_Word_Lengths {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<String> list = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            list = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(list);

          long start = System.currentTimeMillis();
          int max_product_word_length_1 = find_max_product_word_length_1(list);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Max product word lengths:");
          System.out.println("SOLUTION 1 = " + max_product_word_length_1 + ", executime time = " + 
              execution_time_soln_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_max_product_word_length_1(List<String> words) {
    if (words.size() <= 1) {
      return 0;
    }

    int[] char_map = new int[words.size()];
    for (int i = 0; i < words.size(); i++) {
      String word = words.get(i);
      for (int j = 0; j < word.length(); j++) {
        char_map[i] |= (1 << (word.charAt(j) - 'a'));
      }
    }

    int max_product = 0;
    for (int i = 0; i < words.size(); i++) {
      for (int j = i + 1; j < words.size(); j++) {
        if ((char_map[i] & char_map[j]) == 0) {
          max_product = Math.max(max_product, words.get(i).length() * words.get(j).length());
        }
      }
    }
    return max_product;
  }

  private static List<String> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] integer_strings = line.split(" ");
    List<String> words = new ArrayList<>();
    for (String str : integer_strings) {
      words.add(str);
    }
    return words;
  }

  private static String remove_extra_spaces_1(String line) {
    line = line.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < line.length(); i++) {
      if (Character.isSpace(line.charAt(i))) {
        start = i;
      } else if (i == line.length() - 1 || Character.isSpace(line.charAt(i+1))) {
        new_str.append(line.substring(start + 1, i + 1));
        if (i < line.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }

  private static void print_list(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %s\n", i, list.get(i));
    }
    System.out.println();
  }
}