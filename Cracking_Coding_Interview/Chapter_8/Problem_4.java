import java.util.*;

public class Problem_4 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int[] arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = i + 1;
    }

    List<List<Integer>> all_subsets_1 = generate_all_subsets_1(arr);
    List<List<Integer>> all_subsets_2 = generate_all_subsets_2(arr);

    System.out.println("All subsets method 1:");
    for (int i = 0; i < all_subsets_1.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : all_subsets_1.get(i)) {
        System.out.printf("%5d", n);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println("__________________________________________________________________________________________");

    System.out.println("All subsets method 2:");
    for (int i = 0; i < all_subsets_2.size(); i++) {
      System.out.printf("%5d: ", i + 1);
      for (int n : all_subsets_2.get(i)) {
        System.out.printf("%5d", n);
      }
      System.out.println();
    }
    System.out.println();
  }

  private static List<List<Integer>> generate_all_subsets_1(int[] arr) {
    List<List<Integer>> all_subset = new ArrayList<>();
    all_subset.add(new ArrayList<>());
    int N = (1 << arr.length) - 1;
    for (int n = 1; n <= N; n++) {
      all_subset.add(generate_subset(arr, n));
    }
    return all_subset;
  }

  private static List<Integer> generate_subset(int[] arr, int N) {
    List<Integer> subset = new ArrayList<>();
    for (int i = 0; N > 0 && i < arr.length; i++) {
      if ((N & 1) != 0) {
        subset.add(arr[i]);
      }
      N >>= 1;
    }
    return subset;
  }

  private static List<List<Integer>> generate_all_subsets_2(int[] arr) {
    return generate_all_subsets_2(arr, 0);
  }

  private static List<List<Integer>> generate_all_subsets_2(int[] arr, int index) {
    List<List<Integer>> all_subsets = new ArrayList<>();
    if (index == arr.length) {
      all_subsets.add(new ArrayList<>());
      return all_subsets;
    }

    all_subsets = generate_all_subsets_2(arr, index + 1);
    List<List<Integer>> additional_subsets = new ArrayList<>();
    for (List<Integer> subset : all_subsets) {
      List<Integer> new_subset = new ArrayList<>();
      new_subset.add(arr[index]);
      new_subset.addAll(subset);
      additional_subsets.add(new_subset);
    }
    all_subsets.addAll(additional_subsets);
    return all_subsets;
  }
}