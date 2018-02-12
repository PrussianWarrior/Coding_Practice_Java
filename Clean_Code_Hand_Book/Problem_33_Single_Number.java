import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_33_Single_Number {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          List<Integer> input = process_input(line);

          int single_number_soln_1 = find_single_number_1(input);
          int single_number_soln_2 = find_single_number_2(input);
          int single_number_soln_3 = find_single_number_3(input);

          System.out.println("SINGLE NUMBER:");
          System.out.println("Solution 1: " + single_number_soln_1);
          System.out.println("Solution 2: " + single_number_soln_2);
          System.out.println("Solution 3: " + single_number_soln_3);

          if (single_number_soln_1 != single_number_soln_2 ||
              single_number_soln_1 != single_number_soln_3) {
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

  private static int find_single_number_1(List<Integer> input) {
    Map<Integer, Integer> int_freq = new HashMap<>();
    for (int n : input) {
      int curr_val = int_freq.getOrDefault(n, 0);
      int_freq.put(n, curr_val + 1);
    }
    for (Map.Entry<Integer, Integer> entry : int_freq.entrySet()) {
      if (entry.getValue() == 1) {
        return entry.getKey();
      }
    }

    throw new IllegalArgumentException("Single number doesn't exist.");
  }

  private static int find_single_number_2(List<Integer> input) {
    int single_num = 0;
    for (int n : input) {
      single_num ^= n;
    }
    return single_num;
  }

  private static int find_single_number_3(List<Integer> input) {
    Set<Integer> set = new HashSet<>();
    for (int n : input) {
      if (set.contains(n)) {
        set.remove(n);
      } else {
        set.add(n);
      }
    }
    return set.iterator().next();
  }

  private static List<Integer> process_input(String line) {
    String[] words = line.trim().split(" ");
    List<Integer> list = new ArrayList<>();
    for (String word : words) {
      list.add(Integer.parseInt(word.trim()));
    }
    return list;
  }
}