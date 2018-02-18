import java.lang.*;
import java.util.*;
import java.io.*;

public class Median_Of_2 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);         
          
          List<List<Integer>> list_of_int_lists = process_input(line);
          boolean is_list_1_sorted = is_list_sorted(list_of_int_lists.get(0));
          boolean is_list_2_sorted = is_list_sorted(list_of_int_lists.get(1));

          System.out.println("List 1: ");
          print_list(list_of_int_lists.get(0));
          System.out.println("List 1 is sorted = " + is_list_1_sorted);

          if (!is_list_1_sorted) {
            System.out.println("ERROR. List 1 is not sorted.");
            break;
          }

          System.out.println("List 2: ");
          print_list(list_of_int_lists.get(1));
          System.out.println("List 2 is sorted = " + is_list_2_sorted);

          if (!is_list_2_sorted) {
            System.out.println("ERROR. List 2 is not sorted.");
            break;
          }

          double median_of_2_soln_1 = find_median_of_2_sorted_list_1(list_of_int_lists.get(0), list_of_int_lists.get(1));
          double median_of_2_soln_2 = find_median_of_2_sorted_list_2(list_of_int_lists.get(0), list_of_int_lists.get(1));
          double median_of_2_soln_3 = find_median_of_2_sorted_list_3(list_of_int_lists.get(0), list_of_int_lists.get(1));

          System.out.println("Median of 2 sorted lists:");
          System.out.println("Solution 1: " + median_of_2_soln_1);
          System.out.println("Solution 2: " + median_of_2_soln_2);
          System.out.println("Solution 3: " + median_of_2_soln_3);

          if (median_of_2_soln_1 != median_of_2_soln_2 ||
              median_of_2_soln_1 != median_of_2_soln_3
          ) {
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

  private static double find_median_of_2_sorted_list_1(List<Integer> list_1, List<Integer> list_2) {
    int[] merge = new int[list_1.size() + list_2.size()];
    int i_1 = 0;
    int i_2 = 0;
    for (int i = 0; i < merge.length; i++) {
      if (i_1 >= list_1.size()) {
        merge[i] = list_2.get(i_2++);
      } else if (i_2 >= list_2.size()) {
        merge[i] = list_1.get(i_1++);
      } else if (list_1.get(i_1) <= list_2.get(i_2)) {
        merge[i] = list_1.get(i_1++);
      } else {
        merge[i] = list_2.get(i_2++);
      }
    }
    return merge.length % 2 == 1 ? (double)merge[merge.length/2] : 
           0.5*(merge[merge.length/2 - 1] + merge[merge.length/2]);
  }

  private static double find_median_of_2_sorted_list_2(List<Integer> list_1, List<Integer> list_2) {
    int total_length = list_1.size() + list_2.size();
    int i_1 = 0;
    int i_2 = 0;
    boolean first = true;
    int mid = 0;
    int prec_mid = 0;
    for (int i = 0; i <= total_length/2; i++) {
      if (!first) {
        prec_mid = mid;
      }
      if (i_1 >= list_1.size()) {
        mid = list_2.get(i_2++);
      } else if (i_2 >= list_2.size()) {
        mid = list_1.get(i_1++);
      } else if (list_1.get(i_1) <= list_2.get(i_2)) {
        mid = list_1.get(i_1++);
      } else {
        mid = list_2.get(i_2++);
      }
      first = false;
    }
    return total_length % 2 == 1 ? (double)mid : 0.5*(mid + prec_mid);
  }

  private static double find_median_of_2_sorted_list_3(List<Integer> list_1, List<Integer> list_2) {
    int total_len = list_1.size() + list_2.size();
    return total_len  % 2 == 1 ? find_median_of_2_sorted_list_3(total_len/2 + 1, list_1, list_2, 0, 0) :
      0.5 * (find_median_of_2_sorted_list_3(total_len/2 + 1, list_1, list_2, 0, 0) + 
             find_median_of_2_sorted_list_3(total_len/2, list_1, list_2, 0, 0));
  }

  private static double find_median_of_2_sorted_list_3(int k, List<Integer> list_1, 
      List<Integer> list_2, int start_1, int start_2) {
    if (start_1 >= list_1.size()) {
      return list_2.get(start_2 + k - 1);
    }

    if (start_2 >= list_2.size()) {
      return list_1.get(start_1 + k - 1);
    }

    if (k == 1) {
      return Math.min(list_1.get(start_1), list_2.get(start_2));
    }

    int mid_index_1 = start_1 + k/2 - 1;
    int mid_index_2 = start_2 + k/2 - 1;

    int mid_value_1 = mid_index_1 < list_1.size() ? list_1.get(mid_index_1) : Integer.MAX_VALUE;
    int mid_value_2 = mid_index_2 < list_2.size() ? list_2.get(mid_index_2) : Integer.MAX_VALUE;

    return mid_value_1 <= mid_value_2 ? 
        find_median_of_2_sorted_list_3(k - k/2, list_1, list_2, mid_index_1 + 1, start_2) : 
        find_median_of_2_sorted_list_3(k - k/2, list_1, list_2, start_1, mid_index_2 + 1);
  }

  private static List<List<Integer>> process_input(String line) {
    String[] arrays = line.split(",");
    List<List<Integer>> list_of_int_lists = new ArrayList<>();
    for(String arr : arrays) {
      int index_of_opening_brace = arr.indexOf("{");
      int index_of_closing_brace = arr.indexOf("}");
      String[] integers = arr.substring(index_of_opening_brace + 1, index_of_closing_brace).trim().split(" ");
      List<Integer> int_list = new ArrayList<>();
      for (String num : integers) {
        int_list.add(Integer.parseInt(num.trim()));
      }
      Collections.sort(int_list);
      list_of_int_lists.add(int_list);
    }
    return list_of_int_lists;
  }

  private static boolean is_list_sorted(List<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i - 1) > list.get(i)) {
        return false;
      }
    }
    return true;
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d - %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}