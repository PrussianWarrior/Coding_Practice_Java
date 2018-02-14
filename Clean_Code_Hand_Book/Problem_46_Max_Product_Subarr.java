import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_46_Max_Product_Subarr {
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

          int max_product_subarr_soln_1 = find_max_product_subarray_1(input);
          int max_product_subarr_soln_2 = find_max_product_subarray_1(input);

          System.out.println("MAX SUM SUBARRAY:");
          System.out.println("SOLUTION 1: Max product = " + max_product_subarr_soln_1);
          System.out.println("SOLUTION 2: Max product = " + max_product_subarr_soln_2);

          if (max_product_subarr_soln_1 != max_product_subarr_soln_2) {
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

  private static int find_max_product_subarray_1(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }

    int[] largest_product = new int[list.size()];
    int[] smallest_product = new int[list.size()];
    largest_product[0] = list.get(0);
    smallest_product[0] = list.get(0);
    int max_product = Integer.MIN_VALUE;

    for (int i = 1; i < list.size(); i++) {
      largest_product[i] = Math.max(list.get(i), 
        Math.max(largest_product[i - 1] * list.get(i), smallest_product[i - 1] * list.get(i)));
      smallest_product[i] = Math.min(list.get(i), 
        Math.min(largest_product[i - 1] * list.get(i), smallest_product[i - 1] * list.get(i)));
      max_product = Math.max(max_product, largest_product[i]);
    }
    return max_product;
  }

  /*
    Correct but unnecessarily complicated
  */
  private static int find_max_product_subarray_2(List<Integer> list) {
    if (list == null) {
      throw new IllegalArgumentException("ERROR. Input list cannot be null.");
    }
    if (list.isEmpty()) {
      throw new IllegalArgumentException("ERROR. The input list must contain at least one element.");
    }
    int largest_product = list.get(0);
    int smallest_product = list.get(0);
    int max_product = Integer.MIN_VALUE;

    for (int i = 1; i < list.size(); i++) {
      int current_largest = largest_product;
      largest_product = Math.max(list.get(i), 
        Math.max(largest_product * list.get(i), smallest_product * list.get(i)));
      smallest_product = Math.min(list.get(i), 
        Math.min(current_largest * list.get(i), smallest_product * list.get(i)));
      max_product = Math.max(max_product, largest_product);
    }
    return max_product;
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
}