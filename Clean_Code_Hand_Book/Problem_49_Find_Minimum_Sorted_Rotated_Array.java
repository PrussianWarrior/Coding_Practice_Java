import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_49_Find_Minimum_Sorted_Rotated_Array {
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
          
          List<Integer> len_min_max_shift = process_input(line);
          List<Integer> input = generate_random_int_list(len_min_max_shift.get(0), 
            len_min_max_shift.get(1), len_min_max_shift.get(2));

          rorate_list_1(input, len_min_max_shift.get(3));
          print(input);

          int min_soln_1 = find_min_1(input);
          int min_soln_2 = find_min_2(input);

          System.out.println("Min value and index:");
          System.out.println("Solution 1: " + input.get(min_soln_1) + " - " + min_soln_1);
          System.out.println("Solution 2: " + input.get(min_soln_2) + " - " + min_soln_2);

          if (min_soln_1 != min_soln_2) {
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

  private static int find_min_1(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }
      
    int min = Integer.MAX_VALUE;
    int index_of_min = -1;
    for (int i = 0; i < list.size(); i++) {
      if (min > list.get(i)) {
        min = list.get(i);
        index_of_min = i;
      }
    }
    return index_of_min;
  }

  private static int find_min_2(List<Integer> list) {
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
      Since the array is rotated, the following must be true:
        1/ list.get(start) >= list.get(end)
        2/ given mid = start + (end - start)/2:
          - if list.get(mid) >= list.get(end): then the min must be in the range [mid + 1, end] 
            because list.get(mid) >= list.get(end) implies that list.get(mid) >= list.get(start).
            Were this not true (meaning that list.get(mid) < list.get(start)), then that means the array is 
            not sorted, which violates the precondition that the array is sorted.
          - else (meaning that list.get(mid) < list.get(end) <= list.get(start)), the min must be in
            the range [start, mid]
    */

    while (start < end && list.get(start) >= list.get(end)) {
      int mid = start + (end - start)/2;
      if (list.get(mid) > list.get(end)) {
        start = mid + 1;
      } else {
        end = mid;
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

  private static void rorate_list_1(List<Integer> list, int k) {
    k %= list.size();
    if (k == 0) {
      return;
    }
    reverse_list_1(list, 0, list.size() - 1);
    reverse_list_1(list, 0, k - 1);
    reverse_list_1(list, k, list.size() - 1);
  }

  private static void rotate_list_2(List<Integer> list, int k) {

  }

  private static void reverse_list_1(List<Integer> list, int start, int end) {
    while (start < end) {
      int temp = list.get(start);
      list.set(start++, list.get(end));
      list.set(end--, temp);
    }
  }

  private static int gcd_1(int m, int n) {
    if (n == 0 || m == 0) {
      return 0;
    }
    while (m % n != 0) {
      int rem = m % n;
      m = n;
      n = rem;
    }
    return n;
  }
}