import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given two words word1 and word2, find the minimum number of steps required to convert
  word1 to word2. (each operation is counted as 1 step.)
  You have the following 3 operations permitted on a word:
  a) Insert a character
  b) Delete a character
  c) Replace a character

*/

public class Edit_Distance {
  private static class Words_Costs {
    String word_1;
    String word_2;
    int insert_cost;
    int delete_cost;
    int replace_cost;

    public Words_Costs(String word_1, String word_2, int insert_cost, int delete_cost, int replace_cost) {
      this.word_1 = word_1;
      this.word_2 = word_2;
      this.insert_cost = insert_cost;
      this.delete_cost = delete_cost;
      this.replace_cost = replace_cost;
    }

    @Override
    public String toString() {
      StringBuilder display = new StringBuilder();
      display.append("Word 1 = ").append(word_1).append("\n");
      display.append("Word 2 = ").append(word_2).append("\n");
      display.append("Insert cost = ").append(insert_cost).append("\n");
      display.append("Delete cost = ").append(delete_cost).append("\n");
      display.append("Replace cost = ").append(replace_cost).append("\n");
      return display.toString();
    }
  }

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          Words_Costs words_costs = null;
          if (line.indexOf("Customized:") >= 0) {
            words_costs = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println(words_costs);

          long start = System.currentTimeMillis();
          int edit_distance_1 = edit_distance_1(words_costs.word_1, words_costs.word_2, words_costs.insert_cost,
              words_costs.delete_cost, words_costs.replace_cost);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          int edit_distance_2 = edit_distance_2(words_costs.word_1, words_costs.word_2, words_costs.insert_cost,
              words_costs.delete_cost, words_costs.replace_cost);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          int edit_distance_3 = edit_distance_3(words_costs.word_1, words_costs.word_2, words_costs.insert_cost,
              words_costs.delete_cost, words_costs.replace_cost);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          System.out.println("Edit distance:");
          System.out.println("SOLUTION 1 = " + edit_distance_1 + ", executime time = " + 
              execution_time_soln_1);

          System.out.println("SOLUTION 2 = " + edit_distance_2 + ", executime time = " + 
              execution_time_soln_2);

          System.out.println("SOLUTION 3 = " + edit_distance_3 + ", executime time = " + 
              execution_time_soln_3);

          if (edit_distance_1 != edit_distance_2 ||
              edit_distance_1 != edit_distance_3) {
            System.out.println("FAILED");
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

  private static int edit_distance_1(String word_1, String word_2, int insert_cost, int delete_cost, int replace_cost) {
    return edit_distance_1_helper(word_1, word_2, word_1.length() - 1, word_2.length() - 1, 
      insert_cost, delete_cost, replace_cost);
  }

  private static int edit_distance_1_helper(String word_1, String word_2, int i_1, int i_2,
      int insert_cost, int delete_cost, int replace_cost) {
    if (i_1 < 0) {
      return i_2 + 1;
    }

    if (i_2 < 0) {
      return i_1 + 1;
    }

    if (word_1.charAt(i_1) == word_2.charAt(i_2)) {
      return edit_distance_1_helper(word_1, word_2, i_1 - 1, i_2 - 1, insert_cost, delete_cost, replace_cost);
    }

    int min_edit_distance_replace = edit_distance_1_helper(word_1, word_2, i_1 - 1, i_2 - 1, 
        insert_cost, delete_cost, replace_cost) + replace_cost;
    int min_edit_distance_insert = edit_distance_1_helper(word_1, word_2, i_1, i_2 - 1, 
        insert_cost, delete_cost, replace_cost) + insert_cost;
    int min_edit_distance_delete = edit_distance_1_helper(word_1, word_2, i_1 - 1, i_2, 
        insert_cost, delete_cost, replace_cost) + delete_cost;

    return min_of_3(min_edit_distance_replace, min_edit_distance_delete, min_edit_distance_insert);
  }

  private static int edit_distance_2(String word_1, String word_2, int insert_cost, int delete_cost, int replace_cost) {
    return edit_distance_2_helper(word_1, word_2, word_1.length() - 1, word_2.length() - 1, 
      insert_cost, delete_cost, replace_cost, new HashMap<String, Integer>());
  }

  private static int edit_distance_2_helper(String word_1, String word_2, int i_1, int i_2,
      int insert_cost, int delete_cost, int replace_cost, Map<String, Integer> cache) {
    if (i_1 < 0) {
      return i_2 + 1;
    }

    if (i_2 < 0) {
      return i_1 + 1;
    }

    String key = i_1 + " - " + i_2;
    if (cache.containsKey(key)) {
      // System.out.println("returning cached result for key = \'" + key + "\"");
      return cache.get(key);
    }

    if (word_1.charAt(i_1) == word_2.charAt(i_2)) {
      return edit_distance_2_helper(word_1, word_2, i_1 - 1, i_2 - 1, insert_cost, delete_cost, replace_cost, cache);
    }

    int min_edit_distance_replace = edit_distance_2_helper(word_1, word_2, i_1 - 1, i_2 - 1, 
        insert_cost, delete_cost, replace_cost, cache) + replace_cost;
    int min_edit_distance_insert = edit_distance_2_helper(word_1, word_2, i_1, i_2 - 1, 
        insert_cost, delete_cost, replace_cost, cache) + insert_cost;
    int min_edit_distance_delete = edit_distance_2_helper(word_1, word_2, i_1 - 1, i_2, 
        insert_cost, delete_cost, replace_cost, cache) + delete_cost;

    int returned_result = min_of_3(min_edit_distance_replace, min_edit_distance_delete, min_edit_distance_insert);
    cache.put(key, returned_result);
    return returned_result;
  }


  private static int edit_distance_3(String word_1, String word_2, int insert_cost, int delete_cost, int replace_cost) {
    int word_1_len = word_1.length();
    int word_2_len = word_2.length();

    int[][] edit_distance = new int[word_1_len + 1][word_2_len + 1];
    for (int r = 1; r <= word_1_len; r++) {
      edit_distance[r][0] = r;
    }

    for (int c = 1; c <= word_2_len; c++) {
      edit_distance[0][c] = c;
    }

    for (int r = 1; r <= word_1_len; r++) {
      for (int c = 1; c <= word_2_len; c++) {
        if (word_1.charAt(r - 1) == word_2.charAt(c - 1)) {
          edit_distance[r][c] = edit_distance[r - 1][c - 1];
        } else {
          edit_distance[r][c] = min_of_3(
            edit_distance[r - 1][c - 1] + replace_cost,
            edit_distance[r - 1][c] + delete_cost,
            edit_distance[r][c - 1] + insert_cost
          );
        }
      }
    }

    return edit_distance[word_1_len][word_2_len];
  }

  private static Words_Costs get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    String[] words_costs = line.split(" ");
    String word_1 = words_costs[0];
    String word_2 = words_costs[1];
    int insert_cost = Integer.parseInt(words_costs[2]);
    int delete_cost = Integer.parseInt(words_costs[3]);
    int replace_cost = Integer.parseInt(words_costs[4]);

    return new Words_Costs(word_1, word_2, insert_cost, delete_cost, replace_cost);
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

  private static int min_of_3(int n_1, int n_2, int n_3) {
    return Math.min(n_1, Math.min(n_2, n_3));
  }
}