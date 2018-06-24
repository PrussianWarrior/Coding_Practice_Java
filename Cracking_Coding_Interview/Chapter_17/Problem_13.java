import java.util.*;

public class Problem_13 {
  private static class Parse_Result {
    int invalid;
    String parsed_string;

    public Parse_Result(int invalid, String parsed_string) {
      this.parsed_string = parsed_string;
      this.invalid = invalid;
    }

    @Override
    public String toString() {
      return String.format("invalid = %5d, string = %s", invalid, parsed_string);
    }
  }

  public static void main(String[] args) {
    Set<String> set_of_words = new HashSet<String>() {{
      add("looked"); add("like"); add("look");
      add("just"); add("her"); add("brother");
    }};

    String str = "jesslookedjustliketimherbrother";
    // String re_space_1_result = re_space_1(str, set_of_words);
    String re_space_2_result = re_space_2(str, set_of_words);

    System.out.println("Result of:");
    // System.out.println("re_space_1 = " + re_space_1_result);
    System.out.println("re_space_2 = " + re_space_2_result);
  }

  private static String re_space_1(String str, Set<String> set_of_words) {
    return re_space_1(str, set_of_words, 0).parsed_string;
  }

  /*
    This function takes a ridiculous amount of time to finish so do not execute it!
  */
  private static Parse_Result re_space_1(String str, Set<String> set_of_words, int start) {
    if (start >= str.length()) {
      return new Parse_Result(0, "");
    }

    System.out.println("start = " + start);
    int best_invalid = Integer.MAX_VALUE;
    String best_parse = "";
    String prefix = "";
    int index = start;
    while (index < str.length()) {
      System.out.println("index = " + index);
      prefix += str.charAt(index);
      int prefix_invalid = set_of_words.contains(prefix) ? 0 : prefix.length();
      if (prefix_invalid < best_invalid) {
        Parse_Result parse_remainder = re_space_1(str, set_of_words, index + 1);
        if (parse_remainder.invalid + prefix_invalid < best_invalid) {
          best_invalid = parse_remainder.invalid + prefix_invalid;
          best_parse = prefix + " " + parse_remainder.parsed_string;
          if (best_invalid == 0) {
            break;
          }
        }
      }
      index++;
    }

    return new Parse_Result(best_invalid, best_parse);
  }

  private static String re_space_2(String str, Set<String> set_of_words) {
    Parse_Result[] memoized = new Parse_Result[str.length()];
    return re_space_2(str, set_of_words, 0, memoized).parsed_string;
  }

  private static Parse_Result re_space_2(String str, Set<String> set_of_words, int start, Parse_Result[] memoized) {
    if (start >= str.length()) {
      return new Parse_Result(0, "");
    }

    if (memoized[start] != null) {
      return memoized[start];
    }
    
    String best_parse = "";
    String prefix = "";
    int best_invalid = Integer.MAX_VALUE;
    int index = start;
    while (index < str.length()) {
      prefix += str.charAt(index);
      int prefix_invalid = set_of_words.contains(prefix) ? 0 : prefix.length();
      if (prefix_invalid < best_invalid) {
        Parse_Result parse_remainder = re_space_2(str, set_of_words, index + 1, memoized);
        if (prefix_invalid + parse_remainder.invalid < best_invalid) {
          best_invalid = prefix_invalid + parse_remainder.invalid;
          best_parse = prefix + " " + parse_remainder.parsed_string;
          if (best_invalid == 0) {
            break;
          }
        }
      }
      index++;
    }

    memoized[start] = new Parse_Result(best_invalid, best_parse);
    return memoized[start];
  }
}