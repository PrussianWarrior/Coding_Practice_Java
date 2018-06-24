public class Problem_21 {
  public static void main(String[] args) {
    int[] arr_1 = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0};
    System.out.println("arr_1");
    print_arr(arr_1);

    int volume_of_trapped_rain_water_arr_1_method_1 = compute_volume_water_1(arr_1);
    int volume_of_trapped_rain_water_arr_1_method_2 = compute_volume_water_2(arr_1);

    System.out.println("Volume of trapped rain water:");
    System.out.println("Method 1: " + volume_of_trapped_rain_water_arr_1_method_1);
    System.out.println("Method 2: " + volume_of_trapped_rain_water_arr_1_method_2);

    if (volume_of_trapped_rain_water_arr_1_method_1 != volume_of_trapped_rain_water_arr_1_method_2) {
      System.out.println("compute_volume_water_1 does not yield the same result as compute_volume_water_2");
      return;
    }

    System.out.println("________________________________________________________________________________________");
    int N = Integer.parseInt(args[0]);
    int min = Integer.parseInt(args[1]);
    int max = Integer.parseInt(args[2]);
    int[] arr_2 = generate_random_arr(N, min, max);

    System.out.println("arr_2");
    print_arr(arr_2);

    int volume_of_trapped_rain_water_arr_2_method_1 = compute_volume_water_1(arr_2);
    int volume_of_trapped_rain_water_arr_2_method_2 = compute_volume_water_2(arr_2);

    System.out.println("Volume of trapped rain water:");
    System.out.println("Method 1: " + volume_of_trapped_rain_water_arr_2_method_1);
    System.out.println("Method 2: " + volume_of_trapped_rain_water_arr_2_method_2);

    if (volume_of_trapped_rain_water_arr_2_method_1 != volume_of_trapped_rain_water_arr_2_method_2) {
      System.out.println("compute_volume_water_1 does not yield the same result as compute_volume_water_2");
      return;
    }
  }

  private static int compute_volume_water_1(int[] arr) {
    int[] max_from_left = new int[arr.length];
    int[] max_from_right = new int[arr.length];
    max_from_left[0] = arr[0];
    max_from_right[arr.length - 1] = arr[arr.length - 1];

    for (int i = 1; i < arr.length; i++) {
      max_from_left[i] = Math.max(arr[i], max_from_left[i - 1]);
      max_from_right[arr.length - 1 - i] = Math.max(arr[arr.length - 1 - i], max_from_right[arr.length - i]);
    }

    int volume = 0;
    for (int i = 0; i < arr.length; i++) {
      volume += Math.min(max_from_left[i], max_from_right[i]) - arr[i];
    }
    return volume;
  }

  private static int compute_volume_water_2(int[] arr) {
    int index_of_max = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] > arr[index_of_max]) {
        index_of_max = i;
      }
    }

    int volume = 0;
    int curr_max = arr[0];
    for (int i = 0; i < index_of_max; i++) {
      if (curr_max < arr[i]) {
        curr_max = arr[i];
      } else {
        volume += curr_max - arr[i];
      }
    }

    curr_max = arr[arr.length - 1];
    for (int i = arr.length - 1; i > index_of_max; i--) {
      if (curr_max < arr[i]) {
        curr_max = arr[i];
      } else {
        volume += curr_max - arr[i];
      }
    }
    return volume;
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