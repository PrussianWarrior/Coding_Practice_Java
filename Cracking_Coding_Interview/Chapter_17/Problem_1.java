public class Problem_1 {
  public static void main(String[] args) {
    int num_1 = Integer.parseInt(args[0]);
    int num_2 = Integer.parseInt(args[1]);

    int sum_conventional = num_1 + num_2;
    int sum_new_1 = add_new_1(num_1, num_2);
    int sum_new_2 = add_new_2(num_1, num_2);
    System.out.println(num_1 + " + " + num_2);
    System.out.println("Convention add: " + sum_conventional);
    System.out.println("     New add 1: " + sum_new_1);
    System.out.println("     New add 2: " + sum_new_2);

    if (sum_conventional != sum_new_1) {
      System.out.println("ERROR. add_new_1 does not yield correct result.");
    }

    if (sum_conventional != sum_new_2) {
      System.out.println("ERROR. add_new_2 does not yield correct result.");
    }
  }

  private static int add_new_1(int n_1, int n_2) {
    while (n_2 != 0) {
      int xor = n_1 ^ n_2;
      int carry = (n_1 & n_2) << 1;
      n_1 = xor;
      n_2 = carry;
    }
    return n_1;
  }

  private static int add_new_2(int n_1, int n_2) {
    return n_2 == 0 ? n_1 : add_new_2(n_1 ^ n_2, (n_1 & n_2) << 1);
  }
}