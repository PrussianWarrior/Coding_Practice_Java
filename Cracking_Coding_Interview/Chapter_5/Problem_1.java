import java.util.*;

public class Problem_1 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    int M = Integer.parseInt(args[1]);
    int start = Integer.parseInt(args[2]);
    int end = Integer.parseInt(args[3]);

    System.out.println("Binary representation of " + N + " = " + binary_representation(N));
    System.out.println("Binary representation of " + M + " = " + binary_representation(M));

    int result_of_emplacement_1 = emplace_M_in_N_from_start_to_end_1(M, N, start, end);
    System.out.println("Binary representation of emplacing " + M + " into " + N + " from " + start + " to " + end);
    System.out.println(binary_representation(result_of_emplacement_1));
  }

  private static int emplace_M_in_N_from_start_to_end_1(int M, int N, int start, int end) {
    int mask = (1 << (start - end + 1)) - 1;
    mask <<= end;
    System.out.println("Mask = " + binary_representation(mask));
    mask = ~mask;
    System.out.println("Mask = " + binary_representation(mask));
    N &= mask;
    N |= (M << end);
    return N;
  }

  private static String binary_representation(int n) {
    String bin_representation = Integer.toBinaryString(n);
    StringBuilder temp = new StringBuilder();
    for (int i = 1; i <= 32 - bin_representation.length(); i++) {
      temp.append("0");
    }
    temp.append(bin_representation);
    return temp.toString();
  }
}