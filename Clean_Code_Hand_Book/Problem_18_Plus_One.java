import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_18_Plus_One {
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				int counter = 1;
				int k = 2;

				while (line != null) {
					List<Integer> num = process_input(line);
					print(num);
					add_one(num);
					System.out.println("Solution 1: ");
					print(num);
					
					System.out.println("--------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				System.err.println("IOException occurs");
				io_exception.printStackTrace();
			}
		}		
	}

	public static void add_one(List<Integer> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) < 9) {
				list.set(i, list.get(i) + 1);
				return;
			} else {
				list.set(i, 0);
			}
		}
		list.add(0);
		list.set(0, 1);
	}

	private static void print(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i));
		}
		System.out.println();
	}

	private static List<Integer> process_input(String line) {
		List<Integer> digits = new ArrayList<>();
		for (char c : line.toCharArray()) {
			if (Character.isWhitespace(c)) {
				continue;
			}
			digits.add(Character.getNumericValue(c));
		}
		return digits;
	}
}