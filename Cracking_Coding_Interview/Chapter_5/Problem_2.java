import java.util.*;

public class Problem_2 {
  public static void main(String[] args) {
    double N = Double.parseDouble(args[0]);

    String bin_representation_1 = binary_representation_1(N);
    String bin_representation_2 = binary_representation_2(N);

    System.out.println("Binary representation of " + N + ":");
    System.out.println("Method 1: " + bin_representation_1);
    System.out.println("Method 2: " + bin_representation_2);
  }

  private static String binary_representation_1(double n) {
    if (n >= 1 || n <= 0) {
      return "ZERO";
    }

    StringBuilder bin_representation = new StringBuilder();
    bin_representation.append(".");
    while (n > 0) {
      if (bin_representation.length() > 32) {
        return "ZERO";
      }

      n *= 2;
      if (n >= 1) {
        bin_representation.append("1");
        n -= 1;
      } else {
        bin_representation.append("0");
      }
    }
    return bin_representation.toString();
  }

  private static String binary_representation_2(double n) {
    if (n >= 1 || n <= 0) {
      return "ZERO";
    }

    StringBuilder bin_representation = new StringBuilder();
    bin_representation.append(".");
    double frac = 0.5;
    while (n > 0) {
      if (bin_representation.length() > 32) {
        return "ZERO";
      }

      if (n >= frac) {
        bin_representation.append("1");
        n -= frac;
      } else {
        bin_representation.append("0");
      }
      frac /= 2;
    }
    return bin_representation.toString();
  }
}