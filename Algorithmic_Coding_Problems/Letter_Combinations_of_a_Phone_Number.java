import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a digit string, return all possible letter combinations that the number could represent.
  
  2 : abc
  3 : def
  4 : ghi
  5 : jkl
  6 : mno
  7 : pqrs
  8 : tuv
  9 : wxyz
  
  Input:Digit string "23"
  Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

*/

public class Letter_Combinations_of_a_Phone_Number {
  private static final Map<Character, char[]> DIGIT_CHAR_MAP = new HashMap<Character, char[]>() {{
    put('0', new char[]{'0'});
    put('1', new char[]{'1'});
    put('2', new char[]{'a', 'b', 'c'});
    put('3', new char[]{'d', 'e', 'f'});
    put('4', new char[]{'g', 'h', 'i'});
    put('5', new char[]{'j', 'k', 'l'});
    put('6', new char[]{'m', 'n', 'o'});
    put('7', new char[]{'p', 'q', 'r' ,'s'});
    put('8', new char[]{'t', 'u' ,'v'});
    put('9', new char[]{'w', 'x', 'y', 'z'});
  }};

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String phone_nubmer = "";
          if (line.indexOf("Customized:") >= 0) {
            phone_nubmer = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<String> all_combinations_1 = find_letter_combinations_1(phone_nubmer);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          List<String> all_combinations_2 = find_letter_combinations_2(phone_nubmer);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          start = System.currentTimeMillis();
          List<String> all_combinations_3 = find_letter_combinations_3(phone_nubmer);
          stop = System.currentTimeMillis();
          long execution_time_soln_3 = stop - start;

          start = System.currentTimeMillis();
          List<String> all_combinations_4 = find_letter_combinations_4(phone_nubmer);
          stop = System.currentTimeMillis();
          long execution_time_soln_4 = stop - start;

          System.out.println("Closest sum:");
          System.out.println("Solution 1:");
          print_combinations(all_combinations_1);
          System.out.println("Execution time = " + execution_time_soln_1);

          System.out.println("Solution 2:");
          print_combinations(all_combinations_2);
          System.out.println("Execution time = " + execution_time_soln_2);

          System.out.println("Solution 3:");
          print_combinations(all_combinations_3);
          System.out.println("Execution time = " + execution_time_soln_3);

          System.out.println("Solution 4:");
          print_combinations(all_combinations_4);
          System.out.println("Execution time = " + execution_time_soln_4);

          if (!are_lists_identical(all_combinations_1, all_combinations_2)) {
            System.out.println("all_combinations_1 != all_combinations_2");
            break;
          }

          if (!are_lists_identical(all_combinations_1, all_combinations_3)) {
            System.out.println("all_combinations_1 != all_combinations_3");
            break;
          }

          if (!are_lists_identical(all_combinations_1, all_combinations_4)) {
            System.out.println("all_combinations_1 != all_combinations_4");
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

  private static List<String> find_letter_combinations_1(String phone_nubmer) {
    if (phone_nubmer.isEmpty()) {
      return new ArrayList<>();
    }

    LinkedList<String> queue = new LinkedList<>();
    queue.add("");

    for (int i = 0; i < phone_nubmer.length(); i++) {
      while (queue.getFirst().length() == i) {
        String str = queue.removeFirst();
        for (char c : DIGIT_CHAR_MAP.get(phone_nubmer.charAt(i))) {
          queue.addLast(str + c);
        }
      }
    }

    List<String> all_combinations = new ArrayList<>(queue);
    Collections.sort(all_combinations);
    return all_combinations;
  }

  private static List<String> find_letter_combinations_2(String phone_nubmer) {
    List<String> all_combinations = new ArrayList<>();
    find_max_sum_sub_sequence_2_helper(phone_nubmer, all_combinations, new ArrayList<>());
    Collections.sort(all_combinations);
    return all_combinations;
  }

  private static void find_max_sum_sub_sequence_2_helper(String phone_nubmer, List<String> all_combinations, 
      List<Character> letters) {
    if (phone_nubmer.isEmpty()) {
      StringBuilder combination = new StringBuilder();
      for (char c : letters) {
        combination.append(c);
      }
      all_combinations.add(combination.toString());
      return;
    }

    char first_letter = phone_nubmer.charAt(0);
    for (char c : DIGIT_CHAR_MAP.get(first_letter)) {
      letters.add(c);
      find_max_sum_sub_sequence_2_helper(phone_nubmer.substring(1), all_combinations, letters);
      letters.remove(letters.size() - 1);
    }
  }

  private static List<String> find_letter_combinations_3(String phone_nubmer) {
    List<String> all_combinations = new ArrayList<>();
    find_letter_combinations_3_helper(phone_nubmer, 0, all_combinations, new char[phone_nubmer.length()]);
    Collections.sort(all_combinations);
    return all_combinations;
  }

  private static void find_letter_combinations_3_helper(String phone_nubmer, int index, List<String> all_combinations,
      char[] letters) {
    if (index == phone_nubmer.length()) {
      all_combinations.add(new String(letters));
      return;
    }

    char letter_at_index = phone_nubmer.charAt(index);
    for (char c : DIGIT_CHAR_MAP.get(letter_at_index)) {
      letters[index] = c;
      find_letter_combinations_3_helper(phone_nubmer, index + 1, all_combinations, letters);
    }
  }

  private static List<String> find_letter_combinations_4(String phone_nubmer) {
    List<String> all_combinations = new ArrayList<>();
    find_letter_combinations_4_helper(phone_nubmer, 0, all_combinations, new StringBuilder());
    Collections.sort(all_combinations);
    return all_combinations;
  }

  private static void find_letter_combinations_4_helper(String phone_nubmer, int index, List<String> all_combinations,
      StringBuilder combination) {
    if (index == phone_nubmer.length()) {
      all_combinations.add(combination.toString());
      return;
    }

    char letter_at_index = phone_nubmer.charAt(index);
    for (char c : DIGIT_CHAR_MAP.get(letter_at_index)) {
      combination.append(c);
      find_letter_combinations_4_helper(phone_nubmer, index + 1, all_combinations, combination);
      combination.deleteCharAt(combination.length() - 1);
    }
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }

  private static void print_combinations(List<String> all_combinations) {
    for (int i = 0; i < all_combinations.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, all_combinations.get(i));
    }
    System.out.println();
  }

  private static boolean are_lists_identical(List<String> list_1, List<String> list_2) {
    if (list_1.size() != list_2.size()) {
      return false;
    }

    for (int i = 0; i < list_1.size(); i++) {
      if (!list_1.get(i).equals(list_2.get(i))) {
        return false;
      }
    }

    return true;
  }
}