import java.util.*;

public class Problem_3 {
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    System.out.println("Bianry representation of N = " + Integer.toBinaryString(N));
    int length_longest_seq_1 = length_of_longest_sequence_of_1(N);
    System.out.println("Length of longest sequence of 1 = " + length_longest_seq_1);
  }

  private static int length_of_longest_sequence_of_1(int n) {
    List<Integer> pos_0s = new ArrayList<>();
    int pos = 0;
    for (int copy_n = n; copy_n != 0; copy_n >>= 1) {
      if ((copy_n & 1) == 0) {
        pos_0s.add(pos);
      }
      pos++;
    }

    if (pos_0s.size() <= 1) {
      return pos;
    }

    int length_longest_seq_1 = 0;
    int right = -1;
    for (int i = 1; i < pos_0s.size(); i++) {
      int length_right_seq = pos_0s.get(i-1) - right - 1;
      int length_left_seq = pos_0s.get(i) - pos_0s.get(i - 1) - 1;
      if (length_left_seq + length_right_seq + 1 > length_longest_seq_1) {
        length_longest_seq_1 = length_left_seq + length_right_seq + 1;
      }
      right = pos_0s.get(i - 1);
    }

    int length_right_seq = pos_0s.get(pos_0s.size() - 1) - right - 1;
    int length_left_seq = pos - pos_0s.get(pos_0s.size() - 1) - 1;
    if (length_left_seq + length_right_seq + 1 > length_longest_seq_1) {
      length_longest_seq_1 = length_left_seq + length_right_seq + 1;
    }
    return length_longest_seq_1;
  }
}