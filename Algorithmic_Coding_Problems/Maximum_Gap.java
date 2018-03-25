import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

  Try to solve it in linear time/space.

  Return 0 if the array contains less than 2 elements.

  You may assume all elements in the array are non-negative integers and fit in the 32-bit signed 
  integer range.
*/

public class Maximum_Gap {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          List<Integer> list = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            list = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
            list = get_randomized_input(line);
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          print_list(list);

          long start = System.currentTimeMillis();
          int max_gap_1 = find_max_gap_1(list);
          long stop = System.currentTimeMillis();
          long find_max_gap_execution_time_1 = stop - start;

          start = System.currentTimeMillis();
          int max_gap_2 = find_max_gap_2(list);
          stop = System.currentTimeMillis();
          long find_max_gap_execution_time_2 = stop - start;

          System.out.println("Max gap:");
          System.out.println("METHOD 1: " + max_gap_1 + ", execution time = " + find_max_gap_execution_time_1);
          System.out.println("METHOD 2: " + max_gap_2 + ", execution time = " + find_max_gap_execution_time_2);

          if (max_gap_1 != max_gap_2) {
            System.out.println("find_max_gap_1 != find_max_gap_2");
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

  private static int find_max_gap_1(List<Integer> list) {
    if (list.size() < 2) {
      return 0;
    }

    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int n : list) {
      max = Math.max(max, n);
      min = Math.min(min, n);
    }

    if (max == min) {
      return 0;
    }

    int gap = (int)Math.ceil((double)(max - min)/list.size());
    int[] min_bucket = new int[list.size()];
    int[] max_bucket = new int[list.size()];
    Arrays.fill(min_bucket, Integer.MAX_VALUE);
    Arrays.fill(max_bucket, Integer.MIN_VALUE);

    for (int n : list) {
      int bucket_index = (n - min)/gap;
      min_bucket[bucket_index] = Math.min(min_bucket[bucket_index], n);
      max_bucket[bucket_index] = Math.max(max_bucket[bucket_index], n);
    }

    int max_gap = Integer.MIN_VALUE;;
    int prec_high = max_bucket[0];
    for (int i = 1; i < min_bucket.length; i++) {
      if (min_bucket[i] != Integer.MAX_VALUE) {
        max_gap = Math.max(max_gap, min_bucket[i] - prec_high);
        prec_high = max_bucket[i];
      }
    }

    return max_gap;
  }

  private static int find_max_gap_2(List<Integer> list) {
    Collections.sort(list);
    int max_gap = Integer.MIN_VALUE;
    for (int i = 1; i < list.size(); i++) {
      max_gap = Math.max(max_gap, list.get(i) - list.get(i - 1));
    }
    return max_gap;
  }

  private static List<Integer> get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] num_str = line.split(" ");

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < num_str.length; i++) {
      list.add(Integer.parseInt(num_str[i]));
    }
    return list;
  } 

  private static List<Integer> get_randomized_input(String line) {
    int index_of_colon = line.indexOf(":");
    line = line.substring(index_of_colon + 1).trim();
    String[] len_min_max = line.split(" ");
    int list_len = Integer.parseInt(len_min_max[0]);
    int min = Integer.parseInt(len_min_max[1]);
    int max = Integer.parseInt(len_min_max[2]);

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < list_len; i++) {
      int random_num = (int)(Math.random() * (max - min + 1)) + min;
      list.add(random_num);
    }
    return list;
  }

  private static void print_list(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%5d: %5d\n", i, list.get(i));
    }
    System.out.println();
  }
}