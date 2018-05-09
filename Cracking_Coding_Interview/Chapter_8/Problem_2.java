import java.util.*;

public class Problem_2 {
  private static class Cell {
    int r, c;
    public Cell(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public String toString() {
      return String.format("Row = %5d, Col = %5d", r, c);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof Cell)) {
        return false;
      }

      Cell casted_obj = (Cell)obj;
      return r == casted_obj.r && c == casted_obj.c;
    }
  }

  public static void main(String[] args) {
    char[][] grid_1 = {
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'X', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', 'X', '0', '0', 'X', 'X', '0', '0'},
      {'0', '0', 'X', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', 'X', '0'},
      {'0', '0', '0', '0', '0', '0', '0', '0'},
    };

    System.out.println("Grid 1");
    print_grid(grid_1);
    LinkedList<Cell> path_grid_1_method_2 = find_path_2(grid_1);
    LinkedList<Cell> path_grid_1_method_3 = find_path_2(grid_1);

    System.out.println("Path in grid 1");
    System.out.println("Method 2:");
    int count = 1;
    for (Cell cell : path_grid_1_method_2) {
      System.out.printf("%5d: %s\n", count++, cell);
    }

    System.out.println();
    System.out.println("Method 3:");
    count = 1;
    for (Cell cell : path_grid_1_method_3) {
      System.out.printf("%5d: %s\n", count++, cell);
    }
    System.out.println("_____________________________________________________________________________");

    char[][] grid_2 = {
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', '0', '0', 'X', '0', '0', '0', '0'},
      {'X', '0', '0', 'X', '0', '0', '0', '0'},
      {'0', 'X', '0', '0', '0', '0', '0', '0'},
      {'0', '0', 'X', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0', 'X'},
    };

    System.out.println("Grid 2");
    print_grid(grid_2);
    LinkedList<Cell> path_grid_2_method_2 = find_path_2(grid_2);
    LinkedList<Cell> path_grid_2_method_3 = find_path_2(grid_2);
    System.out.println("Path in grid 1");
    System.out.println("Method 2:");
    count = 1;
    for (Cell cell : path_grid_2_method_2) {
      System.out.printf("%5d: %s\n", count++, cell);
    }
    System.out.println();

    System.out.println("Method 3:");
    count = 1;
    for (Cell cell : path_grid_2_method_3) {
      System.out.printf("%5d: %s\n", count++, cell);
    }
    System.out.println("_____________________________________________________________________________");
  }

  private static LinkedList<Cell> find_path_1(char[][] grid) {
    LinkedList<Cell> path = new LinkedList<>();
    path.add(new Cell(0, 0));
    find_path_1(grid, path, 0, 0);
    int count = 1;
    for (Cell cell : path) {
      System.out.printf("%5d: %s\n", count++, cell);
    }
    return path.size() == grid.length + grid[0].length - 1 ? path : new LinkedList<>();
  }

  private static void find_path_1(char[][] grid, LinkedList<Cell> path, int curr_row, int curr_col) {
    if (curr_row >= grid.length || curr_col >= grid[0].length || 
        grid[curr_row][curr_col] == 'X') {
      System.out.println("Removing " + path.getLast() + " from path.");
      path.removeLast();
      return;
    }

    Cell last_cell_in_path = path.getLast();
    if (last_cell_in_path.r != grid.length || last_cell_in_path.c != grid[0].length) {
      path.add(new Cell(curr_row + 1, curr_col));
      if (curr_row + 1 == grid.length && curr_col == grid[0].length) {
        return;
      } else {
        find_path_1(grid, path, curr_row + 1, curr_col);
      }
    }

    last_cell_in_path = path.getLast();
    if (last_cell_in_path.r != grid.length || last_cell_in_path.c != grid[0].length) {
      path.add(new Cell(curr_row, curr_col + 1));
      if (curr_row == grid.length && curr_col + 1 == grid[0].length) {
        return;
      } else {
        find_path_1(grid, path, curr_row, curr_col + 1);
      }      
    }
  }

  private static LinkedList<Cell> find_path_2(char[][] grid) {
    LinkedList<Cell> path = new LinkedList<>();
    if (has_path_2(grid, path, grid.length - 1, grid[0].length - 1)) {
      return path;
    }
    return new LinkedList<>();
  }

  private static boolean has_path_2(char[][] grid, LinkedList<Cell> path, int curr_row, int curr_col) {
    if (curr_row < 0 || curr_col < 0 || grid[curr_row][curr_col] == 'X') {
      return false;
    }

    if ((curr_col == 0 && curr_col == 0) || has_path_2(grid, path, curr_row - 1, curr_col) ||
         has_path_2(grid, path, curr_row, curr_col - 1)) {
      path.add(new Cell(curr_row, curr_col));
      return true;
    }
    return false;
  }

  private static LinkedList<Cell> find_path_3(char[][] grid) {
    Map<Cell, Boolean> cached = new HashMap<>();
    LinkedList<Cell> path = new LinkedList<>();
    if (has_path_3(grid, cached, path, grid.length - 1, grid[0].length - 1)) {
      return path;
    }
    return new LinkedList<>();
  }

  private static boolean has_path_3(char[][] grid, Map<Cell, Boolean> cached, LinkedList<Cell> path, 
      int curr_row, int curr_col) {
    if (curr_row < 0 || curr_col < 0 || grid[curr_row][curr_col] == 'X') {
      return false;
    }

    Cell curr_cell = new Cell(curr_row, curr_col);
    if (cached.containsKey(curr_cell)) {
      System.out.println("Returning cached result for " + curr_cell);
      return cached.get(curr_cell);
    }

    boolean has_path = false;
    if ((curr_row == 0 && curr_col == 0) ||
         has_path_3(grid, cached, path, curr_row - 1, curr_col) ||
         has_path_3(grid, cached, path, curr_row, curr_col - 1)) {
      has_path = true;
      path.add(curr_cell);
    }

    cached.put(curr_cell, has_path);
    return has_path;
  }

  private static void print_grid(char[][] grid) {
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        System.out.printf("%2c", grid[r][c]);
      }
      System.out.println();
    }
    System.out.println();
  }
}