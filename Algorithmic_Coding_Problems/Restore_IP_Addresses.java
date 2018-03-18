import java.lang.*;
import java.util.*;
import java.io.*;

/*
  Problem description: 
  Given a string containing only digits, restore it by returning all possible valid IP address combinations.
 
  For example:
  Given "25525511135",
  return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
*/

public class Restore_IP_Addresses {
  public static void main(String[] args) {
    for (String file_name : args) {
      try {
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = br.readLine();
        int counter = 1;
        while (line != null) {
          System.out.printf("CASE %5d: %-1s\n", counter++, line);

          String phone_nubmer = "";
          if (line.indexOf("Customized:") >= 0) {
            phone_nubmer = get_customized_input(line);
          } else if (line.indexOf("Randomized:") >= 0) {
          } else {
            throw new IllegalArgumentException("The input line must begin with either " + 
              "Customized: or Randomized:");
          }

          long start = System.currentTimeMillis();
          List<String> ip_addresses_1 = restore_ip_addresses_1(phone_nubmer);
          long stop = System.currentTimeMillis();
          long execution_time_soln_1 = stop - start;

          start = System.currentTimeMillis();
          List<String> ip_addresses_2 = restore_ip_addresses_2(phone_nubmer);
          stop = System.currentTimeMillis();
          long execution_time_soln_2 = stop - start;

          System.out.println("IP ADDRESSES:");
          System.out.println("Solution 1:");
          print_combinations(ip_addresses_1);
          System.out.println("Execution time = " + execution_time_soln_1);

          System.out.println("Solution 2:");
          print_combinations(ip_addresses_2);
          System.out.println("Execution time = " + execution_time_soln_2);

          if (!are_lists_identical(ip_addresses_1, ip_addresses_2)) {
            System.out.println("ip_addresses_1 != ip_addresses_2");
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

  private static List<String> restore_ip_addresses_1(String str) {
    if (str.isEmpty()) {
      return new ArrayList<>();
    }

    List<String> ip_addresses = new ArrayList<>();
    for (int i_1 = 1; i_1 <= 3; i_1++) {
      for (int i_2 = 1; i_2 <= 3; i_2++) {
        for (int i_3 = 1; i_3 <= 3; i_3++) {
          for (int i_4 = 1; i_4 <= 3; i_4++) {
            if (i_1 + i_2 + i_3 + i_4 == str.length()) {
              int seg_1_val = Integer.parseInt(str.substring(0, i_1));
              int seg_2_val = Integer.parseInt(str.substring(i_1, i_1 + i_2));
              int seg_3_val = Integer.parseInt(str.substring(i_1 + i_2, i_1 + i_2 + i_3));
              int seg_4_val = Integer.parseInt(str.substring(i_1 + i_2 + i_3, i_1 + i_2 + i_3 + i_4));

              if (seg_1_val <= 255 && seg_2_val <= 255 && seg_3_val <= 255 && seg_4_val <= 255) {
                StringBuilder potential_ip_addresses = new StringBuilder();
                potential_ip_addresses.append(Integer.toString(seg_1_val)).append(".");
                potential_ip_addresses.append(Integer.toString(seg_2_val)).append(".");
                potential_ip_addresses.append(Integer.toString(seg_3_val)).append(".");
                potential_ip_addresses.append(Integer.toString(seg_4_val));
                if (potential_ip_addresses.length() == str.length() + 3) {
                  ip_addresses.add(potential_ip_addresses.toString());
                }
              }
            }
          }
        }
      }
    }

    Collections.sort(ip_addresses);
    return ip_addresses;
  }

  private static List<String> restore_ip_addresses_2(String str) {
    List<String> ip_addresses = new ArrayList<>();
    restore_ip_addresses_2_helper(str, 0, ip_addresses, "", 0);
    Collections.sort(ip_addresses);
    return ip_addresses;
  }

  private static void restore_ip_addresses_2_helper(String str, int start_index, List<String> ip_addresses,
      String restored_ip, int count) {
    // System.out.println(restored_ip);
    if (count == 4) {
      if (start_index == str.length()) {
        ip_addresses.add(restored_ip);
      }
      return;
    }

    for (int i = 1; i <= 3; i++) {
      if (start_index + i > str.length()) {
        return;
      }
      String substr = str.substring(start_index, start_index + i);
      if ((substr.length() > 1 && substr.charAt(0) == '0') || 
          (Integer.parseInt(substr) > 255)) {
        break;
      }
      restore_ip_addresses_2_helper(str, start_index + i, ip_addresses, restored_ip + substr 
        + (count == 3 ? "" : "."), count + 1);
    }
  }

  private static String get_customized_input(String line) {
    int index_of_colon = line.indexOf(":");
    return line.substring(index_of_colon + 1).trim();
  }

  private static void print_combinations(List<String> all_combinations) {
    for (int i = 0; i < all_combinations.size(); i++) {
      System.out.printf("%5d: %s\n", i + 1, all_combinations.get(i));
    }
    System.out.println();
  }

  private static boolean are_lists_identical(List<String> list_1, List<String> list_2) {
    if (list_1.size() != list_2.size()) {
      return false;
    }

    for (int i = 0; i < list_1.size(); i++) {
      if (!list_1.get(i).equals(list_2.get(i))) {
        return false;
      }
    }

    return true;
  }
}