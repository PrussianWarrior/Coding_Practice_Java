import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
  add spaces in s to construct a sentence where each word is a valid dictionary word. You
  may assume the dictionary does not contain duplicate words.

  Return all such possible sentences.

  For example, given
  s = "catsanddog",
  dict = ["cat", "cats", "and", "sand", "dog"].

  A solution is ["cats and dog", "cat sand dog"].
*/

public class Word_Break_2 {
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

          List<String> all_split_strs_1 = break_word_1(dictionary, word);

          System.out.println("All split strings:");
          System.out.println("SOLUTION 1:");
          for (int j = 0; j < all_split_strs_1.size(); j++) {
            System.out.printf("%5d: %-1s\n", j + 1, all_split_strs_1.get(j));
          }
          System.out.println();

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

  private static List<String> break_word_1(Set<String> dictionary, String word) {
    List<List<String>> substr_ending_at_positions = new ArrayList<>();
    for (int i = 0; i <= word.length(); i++) {
      substr_ending_at_positions.add(null);
    }
    substr_ending_at_positions.set(0, new ArrayList<String>());

    for (int end_index = 0; end_index < word.length(); end_index++) {
      if (substr_ending_at_positions.get(end_index) == null) {
        continue;
      }

      for (int i = end_index + 1; i <= word.length(); i++) {
        String substr =  word.substring(end_index, i);
        if (dictionary.contains(substr)) {
          if (substr_ending_at_positions.get(i) == null) {
            substr_ending_at_positions.set(i, new ArrayList<String>());
          }
          substr_ending_at_positions.get(i).add(substr);
        }
      }
    }

    List<String> all_split_strs = new ArrayList<>();
    if (substr_ending_at_positions.get(word.length()) != null) {
      break_word_1(substr_ending_at_positions, word.length(), all_split_strs, new ArrayList<String>());
    }
    return all_split_strs;
  }

  private static void break_word_1(List<List<String>> substr_at_positions, int end_index, 
      List<String> all_strings, List<String> temp) {
    if (end_index <= 0) {
      StringBuilder split_str = new StringBuilder();
      for (int i = temp.size() - 1; i >= 1; i--) {
        split_str.append(temp.get(i)).append(" ");
      }
      split_str.append(temp.get(0));
      all_strings.add(split_str.toString());
      return;
    }

    for (String substr : substr_at_positions.get(end_index)) {
      temp.add(substr);
      break_word_1(substr_at_positions, end_index - substr.length(), all_strings, temp);
      temp.remove(temp.size() - 1);
    }
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