import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_14_One_Edit_Distance {
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				while (line != null) {
					String[] words = process_input(line);
					System.out.println("Word 1 = " + words[0]);
					System.out.println("Word 2 = " + words[1]);
					boolean soln_1 = one_edit_distance_1(words[0], words[1]);
					
					System.out.println("Solution 1 = " + soln_1);
							
					System.out.println("--------------------------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				io_exception.printStackTrace();
			}
		}
	}

	public static boolean one_edit_distance_1(String word_1, String word_2) {
		if (word_1.length() < word_2.length()) {
			return one_edit_distance_1(word_2, word_1);
		}
		if (word_1.length() - word_2.length() > 1) {
			return false;
		}

		int len_diff = word_1.length() - word_2.length();
		int i = 0;
		while (i < word_2.length() && word_1.charAt(i) == word_2.charAt(i)) {
			i++;
		}
		if (i == word_2.length()) {
			return len_diff == 1;
		} else if (len_diff == 0) {
			i++;
		}
		while (i < word_2.length() && word_1.charAt(i + len_diff) == word_2.charAt(i)) {
			i++;
		}

		return i == word_2.length();
	}

	private static String[] process_input(String line) {
		String[] words = line.split(" ");
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].trim();
		}
		return words;
	}
}