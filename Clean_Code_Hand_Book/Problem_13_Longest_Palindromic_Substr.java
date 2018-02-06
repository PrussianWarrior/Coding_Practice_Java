import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_13_Longest_Palindromic_Substr {
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				while (line != null) {
					System.out.println(line);

					int[] soln_1 = longest_palindromic_substr_1(line);

					System.out.println("Solution 1: " + soln_1[0] + " - " + soln_1[1] + ": " + line.substring(soln_1[0], soln_1[1] + 1) + 
						(is_palindrome(line.substring(soln_1[0], soln_1[1] + 1)) ? "" : " not") + " a palindrome");
					
					System.out.println();
					System.out.println("--------------------------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				io_exception.printStackTrace();
			}
		}
	}

	public static int[] longest_palindromic_substr_1(String str) {
		int start = 0;
		int end = 0;
		for (int i = 0; i < str.length(); i++) {
			int[] range_at_i = range_longest_palindromic_substr(str, i, i);
			if (range_at_i[1] - range_at_i[0] > end - start) {
				start = range_at_i[0];
				end = range_at_i[1];
			}
			int[] range_around_i = range_longest_palindromic_substr(str, i, i + 1);
			if (range_around_i[1] - range_around_i[0] > end - start) {
				start = range_around_i[0];
				end = range_around_i[1];
			}
		}
		return new int[]{start, end};
	}

	private static int[] range_longest_palindromic_substr(String str, int start, int end) {
		while (start >= 0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
			start--;
			end++;
		}
		return new int[]{start + 1, end - 1};
	}

	private static boolean is_palindrome(String str) {
		return is_palindrome(str, 0, str.length() - 1);
	}

	private static boolean is_palindrome(String str, int start, int end) {
		while (start < end) {
			if (str.charAt(start) != str.charAt(end)) {
				return false;
			}
			start++;
			end--;
		}
		return true;
	}
}