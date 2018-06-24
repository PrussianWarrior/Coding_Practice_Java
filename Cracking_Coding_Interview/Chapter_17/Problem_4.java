import java.util.*;

public class Problem_4 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int missing_num = Integer.parseInt(args[1]);

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      if (i != missing_num) {
        list.add(i);
      }
    }

    int missing_num_1 = find_missing_num_1(list);
    int missing_num_2 = find_missing_num_2(list);
    int missing_num_3 = find_missing_num_3(list);

    System.out.println("Missing number is:");
    System.out.println("Method 1: " + missing_num_1);
    if (missing_num_1 != -1 && missing_num_1 != missing_num) {
      System.out.println("find_missing_num_1 does not yield the correct answer");
      return;
    }

    System.out.println("Method 2: " + missing_num_2);
    if (missing_num_2 != -1 && missing_num_2 != missing_num) {
      System.out.println("find_missing_num_2 does not yield the correct answer");
      return;
    }

    System.out.println("Method 3: " + missing_num_3);
    if (missing_num_3 != -1 && missing_num_3 != missing_num) {
      System.out.println("find_missing_num_3 does not yield the correct answer");
      return;
    }
  }

  private static int find_missing_num_1(List<Integer> list) {
    int prev = -1;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) - prev > 1) {
        return list.get(i) - 1;
      }
      prev = list.get(i);
    }

    return list.size() + 1 - prev > 1 ? prev + 1 : -1;
  }

  private static int find_missing_num_2(List<Integer> list) {
    if (list.size() == list.get(list.size() - 1) + 1) {
      return -1;
    }
    return find_missing_num_2(list, 0);
  }

  private static int find_missing_num_2(List<Integer> list, int kth) {
    if (kth >= 32) {
      return 0;
    }
    List<Integer> num_with_lsb_1 = new ArrayList<>();
    List<Integer> num_with_lsb_0 = new ArrayList<>();

    for (int n : list) {
      if (get_bit_kth(n, kth) == 1) {
        num_with_lsb_1.add(n);
      } else {
        num_with_lsb_0.add(n);
      }
    }

    if (num_with_lsb_0.size() <= num_with_lsb_1.size()) {
      int result = find_missing_num_2(num_with_lsb_0, kth + 1);
      return (result << 1) | 0;
    } else {
      int result = find_missing_num_2(num_with_lsb_1, kth + 1);
      return (result << 1) | 1;
    }
  }

  private static int find_missing_num_3(List<Integer> list) {
    if (list.size() == list.get(list.size() - 1) + 1) {
      return -1;
    }
    return find_missing_num_3(list, 0);
  }

  private static int find_missing_num_3(List<Integer> list, int kth) {
    if (kth == 32 || list.isEmpty()) {
      return 0;
    }

    List<Integer> num_with_lsb_0 = new ArrayList<>();
    List<Integer> num_with_lsb_1 = new ArrayList<>();

    for (int n : list) {
      if (get_bit_kth(n, kth) == 1) {
        num_with_lsb_1.add(n);
      } else {
        num_with_lsb_0.add(n);
      }
    }

    if (num_with_lsb_0.size() <= num_with_lsb_1.size()) {
      int result = find_missing_num_3(num_with_lsb_0, kth + 1);
      return result << 1;
    } else {
      int result = find_missing_num_3(num_with_lsb_1, kth + 1);
      return result << 1 | 1;
    }
  }

  private static int get_bit_kth(int num, int kth) {
    return (num & (1 << kth)) != 0 ? 1 : 0;
  }
}