import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
 
  You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its
  next station (i+1). You begin the journey with an empty tank at one of the gas stations.
   
  Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
*/

public class Gas_Station {
  private static class Gas_Cost {
    int[] gas_arr;
    int[] cost_arr;

    public Gas_Cost(int[] gas_arr, int[] cost_arr) {
      this.gas_arr = gas_arr;
      this.cost_arr = cost_arr;
    }

    @Override
    public String toString() {
      StringBuilder display = new StringBuilder();
      display.append(String.format("%10s:", "Gas"));
      for (int n : gas_arr) {
        display.append(String.format("%5d", n));
      }
      display.append("\n");
      display.append(String.format("%10s:", "Cost"));
      for (int n : cost_arr) {
        display.append(String.format("%5d", n));
      }
      display.append("\n");
      return display.toString();
    }
  }

  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          Gas_Cost gas_cost = null;
          List<String> gas_cost_lines = new ArrayList<>();
          if (line.indexOf("Customized:") >= 0) {
            while (!(line = br.readLine()).startsWith("****")) {
              gas_cost_lines.add(line);
            }
            gas_cost = get_customized_input(gas_cost_lines);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          System.out.println(gas_cost);
          
          long start = System.currentTimeMillis();
          int start_gas_station_1 = find_start_gas_station_1(gas_cost.gas_arr, gas_cost.cost_arr);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          System.out.println("Start gas station:");
          System.out.println("Method 1: " + start_gas_station_1 + ", execution time = " + execution_time_soln_1);
        
          System.out.println("--------------------------------------------------------------------------------------\n");
          line = br.readLine();
        }
      } catch (IOException io_exception) {
        System.err.println("IOException occurs");
        io_exception.printStackTrace();
      }
    }
  }

  private static int find_start_gas_station_1(int[] gas, int[] cost) {
    int total_gas_cost_diff = 0;
    int running_gas_cost_diff = 0;
    int start = 0;
    for (int i = 0; i < gas.length; i++) {
      int diff = gas[i] - cost[i];
      if (running_gas_cost_diff >= 0) {
        running_gas_cost_diff += diff;
      } else {
        running_gas_cost_diff = diff;
        start = i;
      }
      total_gas_cost_diff += diff;
    }
    return total_gas_cost_diff >= 0 ? start : -1;
  }

  private static Gas_Cost get_customized_input(List<String> gas_cost) {
    if (!gas_cost.get(0).startsWith("gas")) {
      throw new IllegalArgumentException("The string must begins with the word \"gas\".");
    }

    if (!gas_cost.get(1).startsWith("cost")) {
      throw new IllegalArgumentException("The string must begins with the word \"cost\".");
    }

    String gas_str = gas_cost.get(0);
    int index_of_colon = gas_str.indexOf(":");
    gas_str = remove_extra_spaces_1(gas_str.substring(index_of_colon + 1));

    String[] gas_str_split = gas_str.split(" ");
    int num_gas = gas_str_split.length;
    int[] gas_arr = new int[num_gas];

    for (int i = 0; i < num_gas; i++) {
      gas_arr[i] = Integer.parseInt(gas_str_split[i]);
    }

    String cost_str = gas_cost.get(1);
    index_of_colon = cost_str.indexOf(":");
    cost_str = remove_extra_spaces_1(cost_str.substring(index_of_colon + 1));

    String[] cost_str_split = cost_str.split(" ");
    int num_cost = cost_str_split.length;
    int[] cost_arr = new int[num_cost];

    if (num_gas != num_cost) {
      throw new IllegalArgumentException("the number of gas items != number of cost items.");
    }

    for (int i = 0; i < num_cost; i++) {
      cost_arr[i] = Integer.parseInt(cost_str_split[i]);
    }

    return new Gas_Cost(gas_arr, cost_arr);
  }

  private static String remove_extra_spaces_1(String str) {
    StringBuilder new_str = new StringBuilder();
    str = str.trim();
    int start = -1;
    for (int i = 0; i < str.length(); i++) {
      if (Character.isSpace(str.charAt(i))) {
        start = i;
      } else if (i == str.length() - 1 || (Character.isSpace(str.charAt(i + 1)))) {
        new_str.append(str.substring(start + 1, i + 1));
        if (i < str.length() - 1) {
          new_str.append(" ");
        }
      }
    }
    return new_str.toString();
  }
}