import java.util.*;

public class Problem_6 {
  public static void main(String[] args) {
    int N_1 = Integer.parseInt(args[0]);
    int N_2 = Integer.parseInt(args[1]);
    System.out.println("Bianry representation of N_1 = " + binary_representation(N_1));
    System.out.println("Bianry representation of N_2 = " + binary_representation(N_2));

    int num_of_switch = find_num_of_switch_1(N_1, N_2);
    System.out.println("Number of switches = " + num_of_switch);
  }

  private static int find_num_of_switch_1(int N1, int N2) {
    int xor = N1^N2;
    int count = 0;
    while (xor != 0) {
      if ((xor & 1) != 0) {
        count++;
      }
      xor >>= 1;
    }
    return count;
  }

  private static String binary_representation(int N) {
    String default_representation = Integer.toBinaryString(N);
    StringBuilder temp = new StringBuilder();
    for (int i = 1; i <= 32 - default_representation.length(); i++) {
      temp.append(0);
    }
    temp.append(default_representation);
    return temp.toString();
  }
}