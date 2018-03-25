import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Implement wildcard pattern matching with support for '?' and '*'.
  '?' Matches any single character.
  '*' Matches any sequence of characters (including the empty sequence).

  The matching should cover the entire input string (not partial).

  The function prototype should be:
  bool isMatch(const char *s, const char *p)

  Some examples:
  isMatch("aa","a") → false
  isMatch("aa","aa") → true
  isMatch("aaa","aa") → false
  isMatch("aa", "*") → true
  isMatch("aa", "a*") → true
  isMatch("ab", "?*") → true
  isMatch("aab", "c*a*b") → false
*/

public class Wildcard_Matching {
  private static class String_Pattern {
    String str;
    String pat;

    public String_Pattern(String str, String pat) {
      this.str = str;
      this.pat = pat;
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

          String_Pattern str_pat = null;
          if (line.indexOf("Customized:") >= 0) {
            str_pat = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }


          long start = System.currentTimeMillis();
          boolean str_matches_pat_1 = does_string_match_pattern_1(str_pat.str, str_pat.pat);
          long stop = System.currentTimeMillis();
          long does_string_match_pattern_execution_time_1 = stop - start;

          System.out.println("Does string match pattern:");
          System.out.println("METHOD 1: " + str_matches_pat_1 + ", execution time = " + does_string_match_pattern_execution_time_1);

          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static boolean does_string_match_pattern_1(String str, String pat) {
    int str_index = 0;
    int pat_index = 0;
    int prev_str_index = -1;
    int star_index = -1;

    while (str_index < str.length()) {
      if (pat_index < pat.length() && (str.charAt(str_index) == pat.charAt(pat_index) || str.charAt(str_index) == '?')) {
        str_index++;
        pat_index++;
      } else if (pat_index < pat.length() && pat.charAt(pat_index) == '*') {
        star_index = pat_index;
        prev_str_index = str_index;
        pat_index++;
      } else if (star_index != -1) {
        pat_index = star_index + 1;
        str_index = prev_str_index + 1;
        prev_str_index++;
      } else {
        return false;
      }
    }

    while (pat_index < pat.length() && pat.charAt(pat_index) == '*') {
      pat_index++;
    }

    return pat_index == pat.length();
  }

  private static String_Pattern get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] str_pat = line.split(" ");
    return new String_Pattern(str_pat[0], str_pat[1]);
  }

}