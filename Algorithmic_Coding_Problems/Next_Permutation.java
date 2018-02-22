import java.lang.*;import java.util.*;import java.io.*;/*  Problem description:  Implement next permutation, which rearranges numbers into the lexicographically  next greater permutation of numbers.  If such arrangement is not possible, it must rearrange it as the lowest possible order  (ie, sorted in ascending order).  The replacement must be in-place, do not allocate extra memory.  Here are some examples. Inputs are in the left-hand column and its corresponding outputs  are in the right-hand column.  1,2,3 → 1,3,2  3,2,1 → 1,2,3  1,1,5 → 1,5,1*/public class Next_Permutation {  public static void main(String[] args) {    Scanner in = new Scanner(System.in);    for (String file_name : args) {      try {        BufferedReader br = new BufferedReader(new FileReader(file_name));        String line = br.readLine();        int counter = 1;        while (line != null) {          System.out.printf("CASE %5d: %-1s\n", counter++, line);          List<Integer> input = null;          if (line.indexOf("Customized:") >= 0) {            input = get_customized_input(line);          } else if (line.indexOf("Randomized:") >= 0) {          } else {            throw new IllegalArgumentException("The input line must begin with either " +              "Customized: or Randomized:");          }          System.out.println("Original permutation:");          print_list_without_index(input);          get_next_permutation_1(input);          System.out.println("Next permutation:");          print_list_without_index(input);          System.out.println("--------------------------------------------------------------------------------------\n");          line = br.readLine();        }      } catch (IOException io_exception) {        System.err.println("IOException occurs");        io_exception.printStackTrace();      }    }  }  private static void get_next_permutation_1(List<Integer> list) {    int k = list.size() - 2;    while (k >= 0 && list.get(k) >= list.get(k + 1)) {      k--;    }    if (k >= 0) {      int i = k + 1;      while (i < list.size() && list.get(i) > list.get(k)) {        i++;      }      i--;      int temp = list.get(k);      list.set(k, list.get(i));      list.set(i, temp);    }    reverse(list, k + 1, list.size() - 1);  }  private static void reverse(List<Integer> list, int start, int end) {    while (start < end) {      int temp = list.get(start);      list.set(start, list.get(end));      list.set(end, temp);      start++;      end--;    }  }  private static List<Integer> get_customized_input(String line) {    int index_of_colon = line.indexOf(":");    line = line.substring(index_of_colon + 1).trim();    List<Integer> list = new ArrayList<>();    for (String int_str : line.split(" ")) {      list.add(Integer.parseInt(int_str.trim()));    }    return list;  }  

  private static void print_list_with_index(List<Integer> list) {
    int longest_index = num_of_digits(list.size());
    int largest_length = largest_length(list);
    String index_width_format = "%" + longest_index + "d";
    String num_width_format = "%" + largest_length + "d";

    for (int i = 0; i < list.size(); i++) {
      System.out.printf(index_width_format + " - " + num_width_format + "\n", i, list.get(i));
    }
    System.out.println();
  }

  private static void print_list_without_index(List<Integer> list) {
    int largest_length = largest_length(list);
    String num_width_format = "%" + (largest_length + 1) + "d";
    for (int n : list) {
      System.out.printf(num_width_format, n);
    }
    System.out.println();
  }

  private static int num_of_digits(int N) {
    return (int)Math.floor(Math.log10(N)) + 1;
  }

  private static int largest_length(List<Integer> list) {
    int max_len = 0;
    for (int n : list) {
      max_len = Math.max(max_len, num_of_digits(n));
    }
    return max_len;
  }
}