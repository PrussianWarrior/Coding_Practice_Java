import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest
  transformation sequence from beginWord to endWord, such that:

  Only one letter can be changed at a time.
  Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
  For example,
  Given:
  beginWord = "hit"
  endWord = "cog"
  wordList = ["hot","dot","dog","lot","log","cog"]
  As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
  return its length 5.
  
  Note:
    1/ Return 0 if there is no such transformation sequence.
    2/ All words have the same length.
    3/ All words contain only lowercase alphabetic characters.
    4/ You may assume no duplicates in the word list.
    5/ You may assume beginWord and endWord are non-empty and are not the same.
*/

public class Word_Ladder_1 {
  private static class Word_Level {
    String word;
    int level;

    public Word_Level(String word, int level) {
      this.word = word;
      this.level = level;
    }
  }

  public static void main(String[] args) {
    Set<String> dictionary = get_dictionary(args[0]);

    for (int i = 1; i < args.length; i++) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(args[i]));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<String> words = null;
          if (line.indexOf("Customized:") >= 0) {
            words = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {

          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println("Start word = " + words.get(0) + ", end word = " + words.get(1));

          int length_shortest_transformation_sequence_1 = find_length_shortest_transformation_sequence_1(
              dictionary, words.get(0), words.get(1));

          System.out.println("Length of shortest transformation sequence:");
          System.out.println("SOLUTION 1: " + length_shortest_transformation_sequence_1);

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

  private static int find_length_shortest_transformation_sequence_1(Set<String> dictionary, String start, String end) {
    /*
      if start and end words have an edit distance of 1 (changing one letter in
      start results in end), then return 2
    */

    if (start.length() != end.length()) {
      throw new IllegalArgumentException("The start and end words must be of equal length.");
    }

    if (is_one_letter_difference(start, end)) {
      return 2;
    }

    LinkedList<Word_Level> queue_direction_1 = new LinkedList<>();
    LinkedList<Word_Level> queue_direction_2 = new LinkedList<>();
    Set<String> examined_words_direction_1 = new HashSet<>();
    Set<String> examined_words_direction_2 = new HashSet<>();

    queue_direction_1.add(new Word_Level(start, 1));
    queue_direction_2.add(new Word_Level(end, 1));

    int direction_1_level = 1;
    int direction_2_level = 1;

    while (!queue_direction_1.isEmpty() && !queue_direction_2.isEmpty()) {
      if (queue_direction_1.size() <= queue_direction_2.size()) {
        while (!queue_direction_1.isEmpty() && queue_direction_1.getFirst().level == direction_1_level) {
          Word_Level front = queue_direction_1.getFirst();
          List<String> next_level_words = find_next_level_words(dictionary, front.word);
          for (String word : next_level_words) {
            if (!examined_words_direction_1.contains(word)) {
              examined_words_direction_1.add(word);
              if (examined_words_direction_2.contains(word)) {
                return front.level + queue_direction_2.getFirst().level;
              }
              queue_direction_1.add(new Word_Level(word, direction_1_level + 1));
            }
          }
          queue_direction_1.removeFirst();
        }
        direction_1_level++;
      } else {
        while (!queue_direction_2.isEmpty() && queue_direction_2.getFirst().level == direction_2_level) {
          Word_Level front = queue_direction_2.getFirst();
          List<String> next_level_words = find_next_level_words(dictionary, front.word);
          for (String word : next_level_words) {
            if (!examined_words_direction_2.contains(word)) {
              examined_words_direction_2.add(word);
              if (examined_words_direction_1.contains(word)) {
                return front.level + queue_direction_1.getFirst().level;
              }
              queue_direction_2.add(new Word_Level(word, direction_2_level + 1));
            }
          }
          queue_direction_2.removeFirst();
        }
      }
    }

    return 0;
  }

  private static List<String> find_next_level_words(Set<String> dictionary, String start) {
    char[] char_arr = start.toCharArray();
    List<String> next_level_words = new ArrayList<>();
    for (int i = 0; i < char_arr.length; i++) {
      char current_char = char_arr[i];
      for (char letter = 'a'; letter <= 'z'; letter++) {
        if (letter != current_char) {
          char_arr[i] = letter;
        }
        String new_word = new String(char_arr);
        if (dictionary.contains(new_word)) {
          next_level_words.add(new_word);
        }
      }
      char_arr[i] = current_char;
    }
    return next_level_words;
  }

  private static boolean is_one_letter_difference(String str_1, String str_2) {
    boolean one_difference_encountered = false;
    for (int i = 0; i < str_1.length(); i++) {
      if (str_1.charAt(i) != str_2.charAt(i)) {
        if (one_difference_encountered) {
          return false;
        } else {
          one_difference_encountered = true;
        }
      }
    }
    return true;
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

  private static List<String> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();

    List<String> all_words = new ArrayList<>();
    for (String word : line.split(" ")) {
      all_words.add(word.trim());
    }
    return all_words;
  }
}