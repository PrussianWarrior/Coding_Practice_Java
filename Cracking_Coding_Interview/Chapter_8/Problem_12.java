import java.util.*;

public class Problem_12 {
  public static void main(String[] args) {
    int num_queens = Integer.parseInt(args[0]);
    arrange_N_queens_on_N_by_N_board_1(num_queens);
  }

  private static void arrange_N_queens_on_N_by_N_board_1(int num_queens) {
    int[] counter = {0};
    int[] col_pos = new int[num_queens + 1];
    arrange_N_queens_on_N_by_N_board_1(num_queens, col_pos, 1, counter);
  }

  private static void arrange_N_queens_on_N_by_N_board_1(int num_queens, int[] queen_col_pos, int nth_queen, 
      int[] counter) {
    if (nth_queen == num_queens + 1) {
      System.out.printf("%5d", ++counter[0]);
      for (int i = 1; i <= num_queens; i++) {
        System.out.printf("%5d", queen_col_pos[i]);
      }
      System.out.println();
      return;
    }
    
    for (int i = 1; i <= num_queens; i++) {
      if (is_placement_of_nth_queen_on_nth_row_valid(queen_col_pos, nth_queen, i)) {
        queen_col_pos[nth_queen] = i;
        arrange_N_queens_on_N_by_N_board_1(num_queens, queen_col_pos, nth_queen + 1, counter);
      }
    }
  }

  private static boolean is_placement_of_nth_queen_on_nth_row_valid(int[] queen_col_pos, int nth_row_queen, 
      int nth_row_queen_col_pos) {
    for (int i = 1; i < nth_row_queen; i++) {
      if (nth_row_queen_col_pos == queen_col_pos[i] || 
          Math.abs(nth_row_queen - i) == Math.abs(queen_col_pos[i] - nth_row_queen_col_pos)) {
        return false;
      }
    }
    return true;
  }
}