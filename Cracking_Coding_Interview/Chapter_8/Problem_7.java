import java.util.*;

public class Problem_7 {
  public static void main(String[] args) {
    String word = args[0];

    System.out.println("All permutations using method 1:");
    List<String> all_permutations_1 = generate_permutations_1(word);
    for (int i = 0; i < all_permutations_1.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, all_permutations_1.get(i));
    }
    System.out.println();

    System.out.println("_________________________________________________________________________________________________");
    System.out.println("All permutations using method 2:");
    List<String> all_permutations_2 = generate_permutations_2(word);
    for (int i = 0; i < all_permutations_2.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, all_permutations_2.get(i));
    }
    System.out.println();
  }

  private static List<String> generate_permutations_1(String word) {
    List<String> all_permutations = new ArrayList<>();
    if (word.isEmpty()) {
      all_permutations.add("");
      return all_permutations;
    }

    char first_letter = word.charAt(0);
    List<String> permutation_smaller_word = generate_permutations_1(word.substring(1));
    for (String subword_perm : permutation_smaller_word) {
      for (int i = 0; i <= subword_perm.length(); i++) {
        all_permutations.add(insert_letter_at_pos(subword_perm, first_letter, i));
      }
    }
    return all_permutations;
  }

  private static String insert_letter_at_pos(String word, char letter, int i) {
    String left = word.substring(0, i);
    String right = word.substring(i);
    return left + Character.toString(letter) + right;
  }

  private static List<String> generate_permutations_2(String word) {
    Map<Character, Integer> letter_count = new HashMap<>();
    List<String> all_permutations = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
      int freq = letter_count.containsKey(word.charAt(i)) ? letter_count.get(word.charAt(i)) : 0;
      letter_count.put(word.charAt(i), freq + 1);
    }
    generate_permutations_2(word, new LinkedList<>(), letter_count, all_permutations);
    return all_permutations;
  }

  private static void generate_permutations_2(String word, LinkedList<Character> temp, 
      Map<Character, Integer> letter_count, List<String> all_permutations) {
    if (temp.size() == word.length()) {
      StringBuilder new_perm = new StringBuilder();
      for (char c : temp) {
        new_perm.append(c);
      }
      all_permutations.add(new_perm.toString());
      return;
    }

    for (char c : letter_count.keySet()) {
      int freq = letter_count.get(c);
      if (freq > 0) {
        temp.add(c);
        letter_count.put(c, freq - 1);
        generate_permutations_2(word, temp, letter_count, all_permutations);
        letter_count.put(c, freq);
        temp.removeLast();
      }
    }
  }
}