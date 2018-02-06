import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_11_Longest_Substr_At_Most_K_Chars {
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				int counter = 1;
				int k = 2;

				while (line != null) {
					System.out.printf("input %5d: %-1s\n", counter++, line);
					String soln_1 = longest_substr_at_most_k_char_1(line, k);
					String soln_2 = longest_substr_at_most_k_char_2(line, k);
					String soln_3 = longest_substr_at_most_k_char_3(line, k);
					// String soln_4 = longest_substr_at_most_k_char_4(line, k);

					System.out.println("Solution 1: " + soln_1 + " - contains at most " + k + " characters = " + constains_at_most_k_distinct_char(soln_1, k));
					System.out.println("Solution 2: " + soln_2 + " - contains at most " + k + " characters = " + constains_at_most_k_distinct_char(soln_2, k));
					System.out.println("Solution 3: " + soln_3 + " - contains at most " + k + " characters = " + constains_at_most_k_distinct_char(soln_3, k));
					// System.out.println("Solution 4: " + soln_4 + " - contains at most " + k + " characters = " + constains_at_most_k_distinct_char(soln_4, k));
					
					if (!soln_1.equals(soln_2) ||
							!soln_1.equals(soln_3)
					) {
						System.out.println("FAILED");
						break;
					}
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
	public static String longest_substr_at_most_k_char_1(String str, int num_distinct_char) {
		String longest = "";
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			temp.append(str.charAt(i));
			if (constains_at_most_k_distinct_char(temp.toString(), num_distinct_char)) {
				if (temp.length() > longest.length()) {
					longest = temp.toString();
				}
			} else {
				while (!constains_at_most_k_distinct_char(temp.toString(), num_distinct_char)) {
					temp.delete(0, 1);
				}
			}
		}
		return longest;
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

	/*
		This method is faulty. Do not use.
	*/
	public static String longest_substr_at_most_k_char_4(String str, int num_distinct_char) {
		int start = 0;
		int end = -1;
		int counter = 0;
		int i1 = 0;
		int i2 = 0;
		Map<Character, Integer> already_seen_char = new HashMap<>();
		while (i2 < str.length()) {
			if (counter <= num_distinct_char) {
				char c = str.charAt(i2);
				int n = already_seen_char.getOrDefault(c, 0);
				already_seen_char.put(c, n + 1);
				if (already_seen_char.get(c) == 1) {
					counter++;
				}
			} else {
				char c = str.charAt(i1++);
				int n = already_seen_char.getOrDefault(c, 0);
				if (n >= 1) {
					already_seen_char.put(c, n - 1);
					if (already_seen_char.get(c) == 0) {
						counter--;
					}				
				}
			}

			if (counter <= num_distinct_char && (i2 - i1 > end - start)) {
				start = i1;
				end = i2;
			}
			i2++;
		}
		return str.substring(start, end + 1);
	}

	private static boolean constains_at_most_k_distinct_char(String str, int k) {
		int counter = 0;
		Set<Character> already_seen_char = new HashSet<>();
		for (char c : str.toCharArray()) {
			if (!already_seen_char.contains(c)) {
				counter++;
				already_seen_char.add(c);
			}
		}
		return counter <= k;

	}
}