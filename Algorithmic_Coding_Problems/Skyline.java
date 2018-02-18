import java.lang.*;
import java.util.*;
import java.io.*;

public class Skyline {
  private static class Edges_Height {
    int left_edge;
    int right_edge;
    int height;

    public Edges_Height(int left_edge, int right_edge, int height) {
      this.left_edge = left_edge;
      this.right_edge = right_edge;
      this.height = height;
    }

    @Override
    public String toString() {
      return String.format("Left edge = %5d, Right edge = %5d, Height = %5d", 
        left_edge, right_edge, height);
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);
          List<Edges_Height> input = process_input(line);
          
          System.out.println("Buildings:");
          for (Edges_Height edge_height : input) {
            System.out.println(edge_height);
          }
          System.out.println();

          List<int[]> skyline_soln_1 = find_skyline_1(input);
          List<int[]> skyline_soln_2 = find_skyline_2(input);
          List<int[]> skyline_soln_3 = find_skyline_3(input);
          List<int[]> skyline_soln_4 = find_skyline_4(input);

          System.out.println("Skyline:");
          System.out.println("SOLUTION 1:");
          print_skyline(skyline_soln_1);
          
          System.out.println("SOLUTION 2:");
          print_skyline(skyline_soln_2);

          System.out.println("SOLUTION 3:");
          print_skyline(skyline_soln_3);

          System.out.println("SOLUTION 4:");
          print_skyline(skyline_soln_4);

          if (!check_skyline_for_equality(skyline_soln_1, skyline_soln_2) ||
              !check_skyline_for_equality(skyline_soln_1, skyline_soln_3) ||
              !check_skyline_for_equality(skyline_soln_1, skyline_soln_4)
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

  private static List<int[]> find_skyline_1(List<Edges_Height> list) {
    List<int[]> start_end_height = new ArrayList<>();
    for (Edges_Height edge_height : list) {
      start_end_height.add(new int[]{edge_height.left_edge, -edge_height.height});
      start_end_height.add(new int[]{edge_height.right_edge, edge_height.height});
    }

    Collections.sort(start_end_height, (a_1, a_2) -> (a_1[0] == a_2[0] ? a_1[1] - a_2[1] : a_1[0] - a_2[0]));
    // print_skyline(start_end_height);
    TreeMap<Integer, Integer> height_count = new TreeMap<>(Collections.reverseOrder());
    height_count.put(0, 1);
    int current_max_height = 0;

    List<int[]> skyline = new ArrayList<>();
    for (int[] edge_height : start_end_height) {
      if (edge_height[1] < 0) {
        int count = height_count.containsKey(-edge_height[1]) ? height_count.get(-edge_height[1]) : 0;
        height_count.put(-edge_height[1], count + 1);
      } else {
        int count = height_count.get(edge_height[1]);    
        if (count == 1) {
          height_count.remove(edge_height[1]);
        } else {
          height_count.put(edge_height[1], count - 1);
        }
      }

      int max_height = height_count.firstKey();
      if (max_height != current_max_height) {
        current_max_height = max_height;
        skyline.add(new int[]{edge_height[0], current_max_height});
      }      
    }
    return skyline;
  }

  private static List<int[]> find_skyline_2(List<Edges_Height> list) {
    List<int[]> start_end_height = new ArrayList<>();
    for (Edges_Height edge_height : list) {
      start_end_height.add(new int[]{edge_height.left_edge, -edge_height.height});
      start_end_height.add(new int[]{edge_height.right_edge, edge_height.height});
    }
    Collections.sort(start_end_height, (a_1, a_2) -> (a_1[0] == a_2[0] ? a_1[1] - a_2[1] : a_1[0] - a_2[0]));
    // print_skyline(start_end_height);
    TreeMap<Integer, Integer> height_count = new TreeMap<>();
    height_count.put(0, 1);

    int current_max_height = 0;

    List<int[]> skyline = new ArrayList<>();    

    for (int[] edge_height : start_end_height) {
      if (edge_height[1] < 0) {
        int count = height_count.containsKey(-edge_height[1]) ? height_count.get(-edge_height[1]) : 0;
        height_count.put(-edge_height[1], count + 1);
      } else {
        int count = height_count.get(edge_height[1]);
        if (count == 1) {
          height_count.remove(edge_height[1]);
        } else {
          height_count.put(edge_height[1], count - 1);
        }
      }
      int max_height = height_count.lastKey();
      if (max_height != current_max_height) {
        current_max_height = max_height;
        skyline.add(new int[]{edge_height[0], current_max_height});
      }
    }
    return skyline;
  }

  private static List<int[]> find_skyline_3(List<Edges_Height> list) {
    List<int[]> start_end_height = new ArrayList<>();
    for (Edges_Height edge_height : list) {
      start_end_height.add(new int[]{edge_height.left_edge, edge_height.height});
      start_end_height.add(new int[]{edge_height.right_edge, -edge_height.height});
    }

    Collections.sort(start_end_height, (a_1, a_2) -> (a_1[0] == a_2[0] ? a_2[1] - a_1[1] : a_1[0] - a_2[0]));
    // print_skyline(start_end_height);
    TreeMap<Integer, Integer> height_count = new TreeMap<>(Collections.reverseOrder());
    height_count.put(0, 1);

    int current_max_height = 0;

    List<int[]> skyline = new ArrayList<>();
    for (int[] edge_height : start_end_height) {
      if (edge_height[1] > 0) {
        int count = height_count.containsKey(edge_height[1]) ? height_count.get(edge_height[1]) : 0;
        height_count.put(edge_height[1], count + 1);
      } else {
        int count = height_count.get(-edge_height[1]);
        if (count == 1) {
          height_count.remove(-edge_height[1]);
        } else {
          height_count.put(-edge_height[1], count - 1);
        }
      }

      int max_height = height_count.firstKey();
      if (max_height != current_max_height) {
        current_max_height = max_height;
        skyline.add(new int[]{edge_height[0], current_max_height});
      }
    }
    return skyline;
  }

  private static List<int[]> find_skyline_4(List<Edges_Height> list) {
    List<int[]> start_end_height = new ArrayList<>();
    for (Edges_Height edge_height : list) {
      start_end_height.add(new int[]{edge_height.left_edge, edge_height.height});
      start_end_height.add(new int[]{edge_height.right_edge, -edge_height.height});
    }
    Collections.sort(start_end_height, (a_1, a_2) -> (a_1[0] == a_2[0] ? a_2[1] - a_1[1] : a_1[0] - a_2[0]));

    TreeMap<Integer, Integer> height_count = new TreeMap<>();
    height_count.put(0,1);

    int current_max_height = 0;

    List<int[]> skyline = new ArrayList<>();

    for (int[] edge_height : start_end_height) {
      if (edge_height[1] > 0) {
        int count = height_count.containsKey(edge_height[1]) ? height_count.get(edge_height[1]) : 0;
        height_count.put(edge_height[1], count + 1);
      } else {
        int count = height_count.get(-edge_height[1]);
        if (count == 1) {
          height_count.remove(-edge_height[1]);
        } else {
          height_count.put(-edge_height[1], count - 1);
        }
      }

      int max_height = height_count.lastKey();
      if (max_height != current_max_height) {
        current_max_height = max_height;
        skyline.add(new int[]{edge_height[0], current_max_height});
      }
    }
    return skyline;
  }

  private static List<Edges_Height> process_input(String line) {
    String[] all_edge_heights = line.split(",");
    List<Edges_Height> list = new ArrayList<>();
    for (String edge_height : all_edge_heights) {
      System.out.println(edge_height);
      int left_brace_index = edge_height.indexOf("{");
      int right_brace_index = edge_height.indexOf("}");
      String data = edge_height.substring(left_brace_index + 1, right_brace_index);
      String[] tokens = data.split(" ");
      
      int left_edge  = Integer.parseInt(tokens[0].trim());
      int right_edge = Integer.parseInt(tokens[1].trim());
      int height     = Integer.parseInt(tokens[2].trim());

      if (height <= 0) {
        continue;
      }
      list.add(new Edges_Height(left_edge, right_edge, height));
    }
    return list;
  }

  private static boolean check_skyline_for_equality(List<int[]> skyline_1, List<int[]> skyline_2) {
    if (skyline_1.size() != skyline_2.size()) {
      return false;
    }
    for (int i = 0; i < skyline_1.size(); i++) {
      int[] data_1 = skyline_1.get(i);
      int[] data_2 = skyline_2.get(i);
      if (data_1[0] != data_2[0] || data_1[1] != data_2[1]) {
        return false;
      }
    }
    return true;
  }

  private static void print_skyline(List<int[]> skyline) {
    for (int[] edge_height : skyline) {
      System.out.printf("%5d - %5d\n", edge_height[0], edge_height[1]);
    }
    System.out.println();
  }
}