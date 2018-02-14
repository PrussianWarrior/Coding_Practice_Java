import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_48_Search_Insert_Position {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          if (line.isEmpty()) {
            line = br.readLine();
            continue;
          }
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          
          List<Integer> len_min_max = process_input(line);
          List<Integer> input = generate_random_int_list(len_min_max.get(0), len_min_max.get(1), 
              len_min_max.get(2));
          print(input);

          char choice;
          int target;
          boolean results_match = true;

          do {
            target = in.nextInt();
            int insert_pos_soln_1 = find_insert_position_1(input, target);
            int insert_pos_soln_2 = find_insert_position_2(input, target);

            System.out.println("INSERT POSITION:");
            System.out.println("Solution 1: " + insert_pos_soln_1);
            System.out.println("Solution 2: " + insert_pos_soln_2);

            if (insert_pos_soln_1 != insert_pos_soln_2) {
              System.out.println("FAILED");
              results_match = false;
              break;
            }
            choice = in.next().charAt(0);
          } while (choice == 'Y' || choice == 'y');

          if (!results_match) {
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

  private static int find_insert_position_1(List<Integer> list, int target) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }
    
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) >= target) {
        return i;
      }
    }
    return list.size();
  }

  private static int find_insert_position_2(List<Integer> list, int target) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }

    int start = 0;
    int end = list.size() - 1;

    /*
      mid is always >= 0 and <= list.size() - 1
    */

    while (start <= end) {
      int mid = start + (end - start)/2;
      if (list.get(mid) == target) {
        if (mid == 0 || mid == list.size() - 1 || list.get(mid - 1) < target) {
          return mid;
        }
        end = mid - 1;
      } else if (list.get(mid) > target) {
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }
    return start;
  }

  private static List<Integer> process_input(String line) {
    String[] tokens = line.trim().split(" ");
    List<Integer> list = new ArrayList<>();
    for (String token : tokens) {
      list.add(Integer.parseInt(token.trim()));
    }
    return list;
  }

  private static List<Integer> generate_random_int_list(int len, int min, int max) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= len; i++) {
      list.add((int)(Math.random() * (max - min + 1)) + min);
    }
    Collections.sort(list);
    return list;
  }

  private static void print(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d (%5d)", list.get(i), i);
      if ((i + 1) % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
  }

  private static boolean is_list_sorted(List<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i - 1) > list.get(i)) {
        return false;
      }
    }
    return true;
  }
}