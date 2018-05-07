import java.util.*;

public class Problem_7 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);

    System.out.println("Bianry representation of N\n" + binary_representation(N));
    int N_swapped_odd_even_bits = swap_even_odd_bits(N);
    System.out.println("Binary representation of N after swapping bit and even odds\n" 
      + binary_representation(N_swapped_odd_even_bits));
  }

  private static int swap_even_odd_bits(int N) {
    return ((N & 0x55555555) << 1) | ((N & 0xaaaaaaaa) >> 1);
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