import java.util.*;
import java.lang.*;

/*
  Problem description:
  Given numRows, generate the first numRows of Pascal's triangle.
  For example, given numRows = 5,
   
  Return
  [
       [1],
      [1,1],
     [1,2,1],
    [1,3,3,1],
   [1,4,6,4,1]
  ]
*/

public class Pascal_Triangle {
  public static void main(String[] args) {
    for (int num_rows = 2; num_rows <= 10; num_rows++) {
      System.out.println("Number of rows = " + num_rows);
      long start = System.currentTimeMillis();
      List<List<Integer>> pascal_triangles_1 = generate_pascal_triangles_1(num_rows);
      long end = System.currentTimeMillis();
      long generate_pascal_triangle_execution_time_1 = end - start;

      start = System.currentTimeMillis();
      List<Integer> kth_row_in_pascal_triangle_1 = get_kth_row_in_pascal_triangle_1(num_rows);
      end = System.currentTimeMillis();
      long get_kth_row_in_triangle_execution_time_1 = end - start;

      start = System.currentTimeMillis();
      List<List<Integer>> pascal_triangles_2 = generate_pascal_triangles_2(num_rows);
      end = System.currentTimeMillis();
      long generate_pascal_triangle_execution_time_2 = end - start;

      System.out.println("Pascal triangle");
      System.out.println("METHOD 1:");
      print_all_triangles(pascal_triangles_1);
      System.out.println("EXECUTION TIME = " + generate_pascal_triangle_execution_time_1);

      System.out.println("Pascal triangle");
      System.out.println("METHOD 2:");
      print_all_triangles(pascal_triangles_2);
      System.out.println("EXECUTION TIME = " + generate_pascal_triangle_execution_time_2);

      System.out.println("Kth row in Pascal triangle");
      System.out.println("METHOD 1:");
      print_list(kth_row_in_pascal_triangle_1);
      System.out.println("EXECUTION TIME = " + get_kth_row_in_triangle_execution_time_1);      

      if (!are_triangles_identical(pascal_triangles_1, pascal_triangles_2)) {
        System.out.println("generate_pascal_triangles_1 != generate_pascal_triangles_2");
        break;
      }
      System.out.println("------------------------------------------------------------------------------");
    }
  }

  private static List<List<Integer>> generate_pascal_triangles_1(int num_rows) {
    if (num_rows <= 0) {
      return new ArrayList<>();
    }

    List<List<Integer>> pascal_triangles = new ArrayList<>();
    List<Integer> prec_row = new ArrayList<>();
    prec_row.add(1);
    pascal_triangles.add(prec_row);

    for (int n = 2; n <= num_rows; n++) {
      List<Integer> new_row = new ArrayList<>();
      new_row.add(1);
      for (int i = 1; i < prec_row.size(); i++) {
        new_row.add(prec_row.get(i - 1) + prec_row.get(i));
      }
      new_row.add(1);
      pascal_triangles.add(new_row);
      prec_row = new_row;
    }

    return pascal_triangles;
  }

  private static List<List<Integer>> generate_pascal_triangles_2(int num_rows) {
    if (num_rows <= 0) {
      return new ArrayList<>();
    }

    List<List<Integer>> pascal_triangles = new ArrayList<>();
    for (int i = 1; i <= num_rows; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < i; j++) {
        if (j == 0 || j == i - 1) {
          row.add(1);
        } else {
          row.add(pascal_triangles.get(i - 2).get(j) + pascal_triangles.get(i - 2).get(j - 1));
        }
      }
      pascal_triangles.add(row);
    }
    return pascal_triangles;
  }

  private static List<Integer> get_kth_row_in_pascal_triangle_1(int num_rows) {
    if (num_rows <= 0) {
      return new ArrayList<>();
    }

    List<Integer> row = new ArrayList<>();
    row.add(1);
    for (int n = 2; n <= num_rows; n++) {
      List<Integer> new_row = new ArrayList<>();
      new_row.add(1);
      for (int i = 1; i < row.size(); i++) {
        new_row.add(row.get(i) + row.get(i - 1));
      }
      new_row.add(1);
      row = new_row;
    }

    return row;
  }

  private static void print_all_triangles(List<List<Integer>> all_triangles) {
    for (int i = 0; i < all_triangles.size(); i++) {
      System.out.printf("n = %5d: ", i + 1);
      print_list(all_triangles.get(i));
    }
    System.out.println();
  }

  private static void print_list(List<Integer> list) {
    for (int n : list) {
      System.out.printf("%5d", n);
    }
    System.out.println();
  }

  private static boolean are_triangles_identical(List<List<Integer>> triangle_1, 
      List<List<Integer>> triangle_2) {
    if (triangle_1.size() != triangle_2.size()) {
      return false;
    }

    for (int i = 0; i < triangle_1.size(); i++) {
      if (!are_lists_equal(triangle_1.get(i), triangle_2.get(i))) {
        return false;
      }
    }
    return true;
  }

  private static boolean are_lists_equal(List<Integer> list_1, List<Integer> list_2) {
    if (list_1.size() != list_2.size()) {
      return false;
    }

    for (int i = 0; i < list_1.size(); i++) {
      if (list_1.get(i) != list_2.get(i)) {
        return false;
      }
    }
    return true;
  }
}
