import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given two words (beginWord and endWord), and a dictionary's word list, find all shortest
  transformation sequence(s) from beginWord to endWord, such that:

  Only one letter can be changed at a time
  Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
  For example,

  Given:
  beginWord = "hit"
  endWord = "cog"
  wordList = ["hot","dot","dog","lot","log","cog"]
  Return
    [
      ["hit","hot","dot","dog","cog"],
      ["hit","hot","lot","log","cog"]
    ]

*/

public class Word_Ladder_2 {
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

          List<List<String>> all_transformation_sequences_1 = find_shortest_transformation_sequence_1(dictionary,
              words.get(0), words.get(1));

          System.out.println("All sequences transformation:");
          System.out.println("SOLUTION 1: ");
          for (int j = 0; j < all_transformation_sequences_1.size(); j++) {
            System.out.printf("%5d: ", j + 1);
            List<String> seq = all_transformation_sequences_1.get(j);
            for (int k = 0; k <  seq.size() - 1; k++) {
              System.out.print(seq.get(k) + " -> ");
            }
            System.out.println(seq.get(seq.size() - 1));
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

  private static List<List<String>> find_shortest_transformation_sequence_1(Set<String> dictionary, 
      String start, String end) {

    Set<String> current_level_words = new HashSet<>();
    current_level_words.add(start);
    Set<String> next_level_words = new HashSet<>();
    Map<String, List<String>> word_parents = new HashMap<>();
    List<List<String>> all_seq = new ArrayList<>();
    dictionary.add(end);

    do {
      for (String current_word : current_level_words) {
        dictionary.remove(current_word);
        find_next_level_words(dictionary, current_word, next_level_words, word_parents);
      }

      if (next_level_words.contains(end)) {
        LinkedList<String> sequence = new LinkedList<>();
        sequence.addFirst(end);
        get_all_transformation_sequences(all_seq, sequence, word_parents, start);
        break;
      }

      System.out.println("Next level words");
      for (String word : next_level_words) {
        System.out.println(word);
      }

      current_level_words.clear();
      current_level_words.addAll(next_level_words);
      next_level_words.clear();
    } while (!next_level_words.isEmpty());

    return all_seq;
  }

  private static void find_next_level_words(Set<String> dictionary, String start, Set<String> next_level_words,
      Map<String, List<String>> word_parents) {
    char[] char_arr = start.toCharArray();
    for (int i = 0; i < char_arr.length; i++) {
      char current_char = char_arr[i];
      for (char letter = 'a'; letter <= 'z'; letter++) {
        if (letter != current_char) {
          char_arr[i] = letter;
        }
        String new_word = new String(char_arr);
        if (dictionary.contains(new_word)) {
          next_level_words.add(new_word);
          if (!word_parents.containsKey(new_word)) {
            word_parents.put(new_word, new ArrayList<>());
          }
          word_parents.get(new_word).add(start);
        }
      }
      char_arr[i] = current_char;
    }
  }

  private static void get_all_transformation_sequences(List<List<String>> all_seq, LinkedList<String> sequence,
      Map<String, List<String>> word_parents, String start) {
    if (sequence.getFirst().equals(start)) {
      all_seq.add(new ArrayList<>(sequence));
      return;
    }

    for (String parent_word : word_parents.get(sequence.getFirst())) {
      sequence.addFirst(parent_word);
      get_all_transformation_sequences(all_seq, sequence, word_parents, start);
      sequence.removeFirst();
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