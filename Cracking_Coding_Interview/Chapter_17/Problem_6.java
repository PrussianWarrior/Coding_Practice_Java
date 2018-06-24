public class Problem_6 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);

    int num_of_2_between_0_and_N_1 = count_number_of_2_between_0_and_N_1(N);
    int num_of_2_between_0_and_N_2 = count_number_of_2_between_0_and_N_2(N);
    System.out.println("Number of 2 between 0 and " + N);
    System.out.println("Method 1: " + num_of_2_between_0_and_N_1);
    System.out.println("Method 2: " + num_of_2_between_0_and_N_2);

    if (num_of_2_between_0_and_N_1 != num_of_2_between_0_and_N_2) {
      System.out.println("count_number_of_2_between_0_and_N_1 does not yield the same result as " + 
        "count_number_of_2_between_0_and_N_2");
    }
  }

  private static int count_number_of_2_between_0_and_N_1(int N) {
    int count = 0;
    for (int n = 1; n <= N; n++) {
      count += count_number_of_2_in_N(n);
    }
    return count;
  }

  private static int count_number_of_2_in_N(int N) {
    int count = 0;
    while (N != 0) {
      if (N % 10 == 2) {
        count++;
      }
      N /= 10;
    }
    return count;
  }

  private static int count_number_of_2_between_0_and_N_2(int N) {
    int num_of_digits = find_num_of_digits(N);
    int count = 0;
    for (int kth = 0; kth < num_of_digits; kth++) {
      count += find_num_of_2_at_digit_kth(N, kth);
    }
    return count;
  }

  private static int find_num_of_2_at_digit_kth(int N, int kth) {
    int power_of_10 = (int)Math.pow(10, kth);
    int next_power_of_10 = power_of_10 * 10;
    int right = N % power_of_10;

    int round_down = N - N % next_power_of_10;
    int round_up = round_down + next_power_of_10;

    int kth_digit = (N / power_of_10) % 10;
    if (kth_digit < 2) {
      return round_down / 10;
    } else if (kth_digit == 2) {
      return round_down / 10 + right + 1;
    } else {
      return round_up / 10;
    }
  }

  private static int find_num_of_digits(int N) {
    return (int)(Math.floor(Math.log10(N))) + 1;
  }
}