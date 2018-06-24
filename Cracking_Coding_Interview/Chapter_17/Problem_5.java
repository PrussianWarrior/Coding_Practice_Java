import java.util.*;

public class Problem_5 {
  public static void main(String[] args) {
    char[] arr_1 = {'a', 'a', 'a', 'a', '1', '1', 'a', '1', '1', 'a', 'a', '1', 'a', 'a', '1', 'a', 'a', 'a', 'a'};

    int[] longest_subarr_equal_number_letters_numbers_1 = find_longest_subarr_equal_number_of_letters_numbers_1(arr_1);
    int[] longest_subarr_equal_number_letters_numbers_2 = find_longest_subarr_equal_number_of_letters_numbers_2(arr_1);
    int[] longest_subarr_equal_number_letters_numbers_2_review = 
        find_longest_subarr_equal_number_of_letters_numbers_2_review(arr_1);

    System.out.println("Longest subarray with equal number of letters and numbers:");
    System.out.println("Method 1: [" + longest_subarr_equal_number_letters_numbers_1[0] + ", " 
      + longest_subarr_equal_number_letters_numbers_1[1] + "]");
    System.out.println("Method 2: [" + longest_subarr_equal_number_letters_numbers_2[0] + ", " 
      + longest_subarr_equal_number_letters_numbers_2[1] + "]");
    System.out.println("Method 2 review: [" + longest_subarr_equal_number_letters_numbers_2_review[0] + ", " 
      + longest_subarr_equal_number_letters_numbers_2_review[1] + "]");

    if ((longest_subarr_equal_number_letters_numbers_1[0] != longest_subarr_equal_number_letters_numbers_2[0]) &&
        (longest_subarr_equal_number_letters_numbers_1[1] != longest_subarr_equal_number_letters_numbers_2[1])) {
      System.out.println("find_longest_subarr_equal_number_of_letters_numbers_1 does not yield the same result as " +
        "find_longest_subarr_equal_number_of_letters_numbers_2");
    }
  }

  private static int[] find_longest_subarr_equal_number_of_letters_numbers_1(char[] arr) {
    int start_length = arr.length % 2 == 0 ? arr.length : arr.length - 1;
    for (int subarr_len = start_length; subarr_len >= 2; subarr_len -= 2) {
      for (int start = 0; start <= arr.length - subarr_len; start++) {
        if (has_equal_number_of_letters_numbers(arr, start, start + subarr_len - 1)) {
          return new int[]{start, start + subarr_len - 1};
        }
      }
    }
    return new int[]{-1, -1};
  }

  private static boolean has_equal_number_of_letters_numbers(char[] arr, int start, int end) {
    int count = 0;
    for (int i = start; i <= end; i++) {
      if (Character.isLetter(arr[i])) {
        count++;
      } else if (Character.isDigit(arr[i])) {
        count--;
      } else {
        throw new IllegalArgumentException("ERROR. '" + arr[i] + "' is neither a character nor digit.");
      }
    }
    return count == 0;
  }

  private static int[] find_longest_subarr_equal_number_of_letters_numbers_2(char[] arr) {
    int[] diff_between_letters_and_numbers = compute_diff_between_number_of_letters_and_numbers(arr);
    Map<Integer, Integer> diff_pos = new HashMap<>();
    diff_pos.put(0, -1);

    int start = -1;
    int end = -1;

    for (int i = 0; i < arr.length; i++) {
      if (!diff_pos.containsKey(diff_between_letters_and_numbers[i])) {
        diff_pos.put(diff_between_letters_and_numbers[i], i);
      } else {
        int k = diff_pos.get(diff_between_letters_and_numbers[i]);
        if (i - k > end - start + 1) {
          start = k + 1;
          end = i;
        }
      }
    }
    return new int[]{start, end};
  }

  private static int[] compute_diff_between_number_of_letters_and_numbers(char[] arr) {
    int delta = 0;
    int[] diff_between_letters_and_numbers = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      if (Character.isDigit(arr[i])) {
        delta--;
      } else if (Character.isLetter(arr[i])) {
        delta++;
      } else {
        throw new IllegalArgumentException("ERROR. '" + arr[i] + "' is neither a character nor digit.");
      }
      diff_between_letters_and_numbers[i] = delta;
    }
    return diff_between_letters_and_numbers;
  }

  private static int[] find_longest_subarr_equal_number_of_letters_numbers_2_review(char[] arr) {
    int[] diff_between_letters_and_numbers = new int[arr.length];
    int delta = 0;
    for (int i = 0; i < arr.length; i++) {
      if (Character.isLetter(arr[i])) {
        delta++;
      } else if (Character.isDigit(arr[i])) {
        delta--;
      }
      diff_between_letters_and_numbers[i] = delta;
    }

    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);
    int start = -1;
    int end = -1;
    for (int i = 0; i < diff_between_letters_and_numbers.length; i++) {
      if (!map.containsKey(diff_between_letters_and_numbers[i])) {
        map.put(diff_between_letters_and_numbers[i], i);
      } else {
        int k = map.get(diff_between_letters_and_numbers[i]);
        if (i - k > end - start + 1) {
          start = k + 1;
          end = i;
        }
      }
    }

    return new int[]{start, end};
  }
}