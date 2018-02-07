import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_17_Reverse_Integer {
	private static final int MAX = Integer.MAX_VALUE/10;
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				int counter = 1;
	
				while (line != null) {
					int input = Integer.parseInt(line);
					System.out.printf("input %5d: %-1s\n", counter++, input);
					
					int soln_1 = reverse_int_1(input);
					
					System.out.println("Solution 1 = " + soln_1);
					System.out.println("--------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				System.err.println("IOException occurs");
				io_exception.printStackTrace();
			}
		}		
	}

	/*
		Brute force approach guaranteed to always produce the correct result
	*/
	public static int reverse_int_1(int n) {		
		int reversed_int = 0;
		for (; n != 0; n /= 10) {
			if (Math.abs(reversed_int) > MAX) {
				return 0;
			}		
			reversed_int = reversed_int * 10 + n % 10;
		}

		return reversed_int;
	}

	public static String longest_substr_at_most_k_char_2(String str, int num_distinct_char) {
		int start = -1;
		int end = 0;
		int i1 = 0;
		int i2 = 0;
		int counter = 0;
		Map<Character, Integer> already_seen_char = new HashMap<>();

		while (i2 < str.length()) {
			char c = str.charAt(i2);
			int n = already_seen_char.getOrDefault(c, 0);
			already_seen_char.put(c, n + 1);
			if (already_seen_char.get(c) == 1) {
				counter++;
			}

			while (counter > num_distinct_char) {
				c = str.charAt(i1++);
				n = already_seen_char.get(c);
				already_seen_char.put(c, n - 1);
				if (already_seen_char.get(c) == 0) {
					counter--;
					already_seen_char.remove(c);
				}
			}

			if (i2 - i1 > end - start) {
				start = i1;
				end = i2;
			}
			i2++;
		}

		return str.substring(start, end + 1);
	}

	public static String longest_substr_at_most_k_char_3(String str, int num_distinct_char) {
		int start = 0;
		int end = -1;
		int counter = 0;
		Map<Character, Integer> already_seen_char = new HashMap<>();
		for (int i1 = 0, i2 = 0; i2 < str.length(); i2++) {
			char c = str.charAt(i2);
			int n = already_seen_char.getOrDefault(c, 0);
			if (n == 0) {
				counter++;
			}
			already_seen_char.put(c, n + 1);

			while (counter > num_distinct_char) {
				c = str.charAt(i1++);
				n = already_seen_char.get(c);
				already_seen_char.put(c, n - 1);
				if (already_seen_char.get(c) == 0) {
					already_seen_char.remove(c);
					counter--;
				}
			}

			if (i2 - i1 > end - start) {
				end = i2;
				start = i1;
			}
		}
		return str.substring(start, end + 1);
	}
}