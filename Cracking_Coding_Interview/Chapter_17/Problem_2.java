public class Problem_2 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);
    int max = Integer.parseInt(args[2]);

    int[] rand_arr = generate_random_arr(N, min, max);
    System.out.println("Before shuffling:");
    print_arr(rand_arr);

    shuffle_arr(rand_arr);
    System.out.println("After shuffling:");
    print_arr(rand_arr);
  }

  private static void shuffle_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      int random_index = (int)(Math.random() * (i + 1));
      int temp = arr[i];
      arr[i] = arr[random_index];
      arr[random_index] = temp;
    }
  }

  private static int[] generate_random_arr(int N, int min, int max) {
    int[] rand_arr = new int[N];
    for (int i = 0; i < N; i++) {
      rand_arr[i] = (int)(Math.random() * (max - min + 1)) + min;
    }
    return rand_arr;
  }

  private static void print_arr(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%5d: %5d\n", i, arr[i]);
    }
    System.out.println();
  }
}