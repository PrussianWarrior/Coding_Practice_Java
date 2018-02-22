import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
  determine if s can be segmented into a space-separated sequence of one or more dictionary 
  words. You may assume the dictionary does not contain duplicate words.

  For example, given
  s = "leetcode",
  dict = ["leet", "code"].

  Return true because "leetcode" can be segmented as "leet code".
*/

public class Word_Break_1 {
  public static void main(String[] args) {
    Set<String> dictionary = get_dictionary(args[0]);

    for (int i = 1; i < args.length; i++) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(args[i]));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String word = null;
          if (line.indexOf("Customized:") >= 0) {
            word = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("Input word = " + word);

          boolean is_word_breakable_soln_1 = is_breakable_1(dictionary, word);
          boolean is_word_breakable_soln_2 = is_breakable_2(dictionary, word);

          System.out.println("Is breakable: ");
          System.out.println("Solution 1: " + is_word_breakable_soln_1);
          System.out.println("Solution 2: " + is_word_breakable_soln_2);

          if (is_word_breakable_soln_1 != is_word_breakable_soln_2) {
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

  private static boolean is_breakable_1(Set<String> dictionary, String word) {
    return is_breakable_1(dictionary, word, true);
  }

  private static boolean is_breakable_1(Set<String> dictionary, String word, boolean is_original_word) {
    if (!is_original_word && dictionary.contains(word)) {
      return true;
    }

    for (int i = 1; i < word.length(); i++) {
      if (dictionary.contains(word.substring(0, i))) {
        if (is_breakable_1(dictionary, word.substring(i), false)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean is_breakable_2(Set<String> dictionary, String word) {
    boolean[] substr_breakable = new boolean[word.length() + 1];
    Arrays.fill(substr_breakable, false);

    substr_breakable[0] = true;
    for (int end_prefix = 0; end_prefix < word.length(); end_prefix++) {
      if (!substr_breakable[end_prefix]) {
        continue;
      }

      for (int j = end_prefix + 1; j <= word.length(); j++) {
        if (dictionary.contains(word.substring(end_prefix, j))) {
          substr_breakable[j] = true;
        }
      }
    }

    return substr_breakable[word.length()];
  }

  private static Set<String> get_dictionary(String dictionary_filename) {
    Set<String> dictionary = new HashSet<>();

    try {
      BufferedReader br = new BufferedReader(new FileReader(dictionary_filename));
      String line = br.readLine();

      while (line != null) {
        dictionary.add(line.trim());
        line = br.readLine();
      }
      br.close();
    } catch (IOException io_exception) {
      System.err.println("IOException occurs");
      io_exception.printStackTrace();
    }

    return dictionary;
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }
}