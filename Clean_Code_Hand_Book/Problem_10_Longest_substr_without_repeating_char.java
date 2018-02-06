import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_10_Longest_substr_without_repeating_char {	
	public static void main(String[] args) {
		for (String file_name : args) {
			int counter = 1;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file_name));
				String current_line = br.readLine();
				while (current_line != null) {
					System.out.printf("input %5d: %-1s\n", counter++, current_line);
					String soln_1 = longest_substr_without_repeating_char_1(current_line);
					String soln_2 = longest_substr_without_repeating_char_2(current_line);
					String soln_3 = longest_substr_without_repeating_char_3(current_line);
					String soln_4 = longest_substr_without_repeating_char_4(current_line);
					String soln_5 = longest_substr_without_repeating_char_5(current_line);

					System.out.println("Solution 1: " + soln_1 + " - all characters are distinct = " + contains_unique_characters(soln_1));
					System.out.println("Solution 2: " + soln_2 + " - all characters are distinct = " + contains_unique_characters(soln_2));
					System.out.println("Solution 3: " + soln_3 + " - all characters are distinct = " + contains_unique_characters(soln_3));
					System.out.println("Solution 4: " + soln_4 + " - all characters are distinct = " + contains_unique_characters(soln_4));
					System.out.println("Solution 5: " + soln_5 + " - all characters are distinct = " + contains_unique_characters(soln_5));

					if (!soln_1.equals(soln_2) || 
							!soln_1.equals(soln_3) || 
							!soln_1.equals(soln_4) ||
							!soln_1.equals(soln_5)) {
						System.out.println("FAILED!");
						break;
					}
					System.out.println();
					current_line = br.readLine();
				}

			} catch (IOException io_exception) {
				System.out.println("IO Exception occurs. Stack trace");
				io_exception.printStackTrace();
			}
		}

		System.out.println("\n-------------------NOW TEST RANDOMLY GENERATED STRINGS-------------------");
		for (int i = 1; i <= 10; ++i) {
			int len = (int)(Math.random() * 100) + 20;
			String input = generate_random_str(len);
			System.out.printf("input %5d: %-1s\n", i, input);
			String soln_1 = longest_substr_without_repeating_char_1(input);
			String soln_2 = longest_substr_without_repeating_char_2(input);
			String soln_3 = longest_substr_without_repeating_char_3(input);
			String soln_4 = longest_substr_without_repeating_char_4(input);
			String soln_5 = longest_substr_without_repeating_char_5(input);

			System.out.println("Solution 1: " + soln_1 + " - all characters are distinct = " + contains_unique_characters(soln_1));
			System.out.println("Solution 2: " + soln_2 + " - all characters are distinct = " + contains_unique_characters(soln_2));
			System.out.println("Solution 3: " + soln_3 + " - all characters are distinct = " + contains_unique_characters(soln_3));
			System.out.println("Solution 4: " + soln_4 + " - all characters are distinct = " + contains_unique_characters(soln_4));
			System.out.println("Solution 5: " + soln_5 + " - all characters are distinct = " + contains_unique_characters(soln_5));
			if (!soln_1.equals(soln_2) || 
					!soln_1.equals(soln_3) || 
					!soln_1.equals(soln_4) ||
					!soln_1.equals(soln_5)) {
				System.out.println("FAILED!");
				break;
			}
			System.out.println();
		}
	}

	/*
		Brute force approach
	*/
	public static String longest_substr_without_repeating_char_1(String str) {
		String longest = "";
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			int index = index_of_target_char(temp.toString(), c);
			if (index >= 0) {
				if (temp.length() > longest.length()) {
					longest = temp.toString();
				}
				temp.delete(0, index + 1);
			}
			temp.append(c);
		}

		if (temp.length() > longest.length()) {
			longest = temp.toString();
		}
		return longest;
	}

	public static String longest_substr_without_repeating_char_2(String str) {
		String longest = "";
		int i = 0;
		int j = 0;
		boolean already_seen_char[] = new boolean[256];
		Arrays.fill(already_seen_char, false);
		for (; j < str.length(); ++j) {
			char c = str.charAt(j);
			while (already_seen_char[c]) {
				already_seen_char[str.charAt(i++)] = false;
			}
			already_seen_char[c] = true;
			if (j - i + 1 > longest.length()) {
				longest = str.substring(i, j + 1);
			}
		}
		return longest;
	}

	public static String longest_substr_without_repeating_char_3(String str) {
		String longest = "";
		Set<Character> already_seen_char = new HashSet<>();
		int i = 0;
		int j = 0;
		for (; j < str.length(); ++j) {
			while (already_seen_char.contains(str.charAt(j))) {
				already_seen_char.remove(str.charAt(i++));
			}
			already_seen_char.add(str.charAt(j));
			if (j - i + 1 > longest.length()) {
				longest = str.substring(i, j + 1);
			}
		}
		return longest;
	}

	public static String longest_substr_without_repeating_char_4(String str) {
		String longest = "";
		int i = 0;
		int j = 0;
		int[] char_pos = new int[256];
		Arrays.fill(char_pos, -1);
		for (; j < str.length(); ++j) {
			if (char_pos[str.charAt(j)] >= i) {
				i = char_pos[str.charAt(j)] + 1;
			}
			char_pos[str.charAt(j)] = j;
			if (j - i + 1 > longest.length()) {
				longest = str.substring(i, j + 1);
			}
		}
		return longest;
	}

	public static String longest_substr_without_repeating_char_5(String str) {
		String longest = "";
		int i = 0;
		int j = 0;
		Map<Character, Integer> char_pos = new HashMap<>();
		for (; j < str.length(); ++j) {
			int index = char_pos.getOrDefault(str.charAt(j), -1);
			if (index >= i) {
				i = index + 1;
			}
			char_pos.put(str.charAt(j), j);
			if (j - i + 1 > longest.length()) {
				longest = str.substring(i, j + 1);
			}
		}
		return longest;
	}

	private static int index_of_target_char(String str, char target) {
		for (int i = 0; i < str.length(); ++i) {
			if (str.charAt(i) == target) {
				return i;
			}
		}
		return -1;
	}

	private static String generate_random_str(int len) {
    StringBuilder ans = new StringBuilder();
    for (int i = 1; i <= len; i++) {
      int rand = (int)(Math.random() * 26);
      ans.append((char)(rand + 'a'));
    }
    return ans.toString();
  }

  private static boolean contains_unique_characters(String str) {
  	Set<Character> already_seen_char = new HashSet<>();
  	for (char c : str.toCharArray()) {
  		if (already_seen_char.contains(c)) {
  			return false;
  		}
  		already_seen_char.add(c);
  	}
  	return true;
  }
}