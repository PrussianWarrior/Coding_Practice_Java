import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a non-empty string str and an integer k, rearrange the string such that the same 
  characters are at least distance k from each other.
 
  All input strings are given in lowercase letters. If it is not possible to rearrange the string, 
  return an empty string "".
   
  Example:
  str = "aabbcc", k = 3

  Result: "abcabc"

  The same letters are at least distance 3 from each other.
*/

public class Rearrange_String_K_Distance_Apart {
  private static class String_Distance {
    String str;
    int distance;

    public String_Distance(String str, int distance) {
      this.str = str;
      this.distance = distance;
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

          String_Distance str_distance = null;
          if (line.indexOf("Customized:") >= 0) {
            str_distance = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          String rearranged_str_1 = rearrange_str_k_distance_1(str_distance.str, str_distance.distance);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("REARRANGED STRING");
          System.out.println("Method 1 = " + rearranged_str_1 + ", execution time = " + execution_time_soln_1);
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static String rearrange_str_k_distance_1(String str, int distance) {
    if (distance == 0) {
      return str;
    }

    Map<Character, Integer> char_count = new HashMap<>();
    for (char c : str.toCharArray()) {
      int curr_count = char_count.containsKey(c) ? char_count.get(c) : 0;
      char_count.put(c, curr_count + 1);
    }

    StringBuilder rearranged_str = new StringBuilder();
    PriorityQueue<Character> pq = new PriorityQueue<Character>(new Comparator<Character>() {
      @Override
      public int compare(Character c1, Character c2) {
        if (char_count.get(c1) != char_count.get(c2)) {
          return char_count.get(c2) - char_count.get(c1);
        } else {
          return c1.compareTo(c2);
        }
      }
    });

    for (char c : char_count.keySet()) {
      pq.add(c);
    }

    int str_len = str.length();

    while (!pq.isEmpty()) {
      int limit = Math.min(str_len, distance);
      List<Character> recycled_char = new ArrayList<>();

      for (int i = 1; i <= limit; i++) {
        if (pq.isEmpty()) {
          return "";
        }

        char c = pq.poll();
        rearranged_str.append(c);

        char_count.put(c, char_count.get(c) - 1);

        if (char_count.get(c) > 0) {
          recycled_char.add(c);
        }
        str_len--;
      }

      for (char c : recycled_char) {
        pq.add(c);
      }
    }

    return rearranged_str.toString();
  }

  private static String_Distance get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = remove_extra_spaces_1(line.substring(index_of_colon + 1));
    int index_of_space = line.indexOf(" ");

    String str = line.substring(0, index_of_space);
    int distance = Integer.parseInt(line.substring(index_of_space + 1));
    return new String_Distance(str, distance);
  }

  private static String remove_extra_spaces_1(String str) {
    str = str.trim();
    StringBuilder new_str = new StringBuilder();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || Character.isSpace(str.charAt(i + 1))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i < str.length() - 1) {
          new_str.append(" ");
        }
      }
    }

    return new_str.toString();
  }
}