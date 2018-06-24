import java.util.*;

public class Problem_15 {
  private static class String_Length_Comparator implements Comparator<String> {
    @Override
    public int compare(String str_1, String str_2) {
      return str_2.length() - str_1.length();
    }
  }

  public static void main(String[] args) {
    List<String> list_of_words = new ArrayList<>();
    list_of_words.add("tigersarebeautiful");
    list_of_words.add("longlivehumanintelligence");
    list_of_words.add("alberteinsteinisagenius");
    list_of_words.add("longlivegermany");
    list_of_words.add("mypersonalheroissirandrewjohnwiles");
    // list_of_words.add("andrewjohnwilesisabrilliantbritishmathematician");
    list_of_words.add("andrew");
    list_of_words.add("john");
    list_of_words.add("wiles");
    list_of_words.add("brilliant");
    list_of_words.add("british");
    list_of_words.add("mathematician");
    list_of_words.add("genius");
    list_of_words.add("is");
    list_of_words.add("a");
    list_of_words.add("albert");
    list_of_words.add("einstein");
    list_of_words.add("personal");
    list_of_words.add("hero");
    list_of_words.add("my");
    list_of_words.add("tigers");
    list_of_words.add("are");
    list_of_words.add("beautiful");
    list_of_words.add("long");
    list_of_words.add("live");
    list_of_words.add("human");
    list_of_words.add("intelligence");
    list_of_words.add("germany");

    String longest_word_composed_of_smaller_words_1 = find_longest_word_composed_of_smaller_words_1(list_of_words);
    System.out.println("Longest word composed of smaller words = ");
    System.out.println("Method 1: " + longest_word_composed_of_smaller_words_1);
  }

  private static String find_longest_word_composed_of_smaller_words_1(List<String> list_of_words) {
    Collections.sort(list_of_words, new String_Length_Comparator());
    Set<String> set_of_words = new HashSet<>();
    for (String word : list_of_words) {
      set_of_words.add(word);
    }
    for (String word : list_of_words) {
      if (is_breakable_1(word, set_of_words)) {
        return word;
      }
    } 
    return "";
  }

  private static boolean is_breakable_1(String word, Set<String> set_of_words) {
    boolean[] is_breakable = new boolean[word.length() + 1];
    is_breakable[0] = true;

    for (int i_1 = 0; i_1 <= word.length(); i_1++) {
      if (!is_breakable[i_1]) {
        continue;
      }

      for (int i_2 = i_1 + 1; i_2 <= word.length(); i_2++) {
        if (set_of_words.contains(word.substring(i_1, i_2)) && 
            ((i_1 == 0 && i_2 < word.length()) || (i_1 > 0 && i_2 <= word.length()))) {
          is_breakable[i_2] = true;
        }
      }
    }
    return is_breakable[word.length()];
  }

}