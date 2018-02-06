import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_12_Missing_Ranges {
	private static class Arr_Range {
		int[] arr;
		int start;
		int end;
	}

	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				while (line != null) {
					Arr_Range input = str_2_arr_range(line);
					print(input);

					List<String> soln_1 = find_missing_ranges_1(input.arr, input.start, input.end);

					System.out.println("Solution 1");
					for (String range : soln_1) {
						System.out.println(range);
					}
					System.out.println();
					System.out.println("--------------------------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				io_exception.printStackTrace();
			}
		}
	}

	public static List<String> find_missing_ranges_1(int[] arr, int start, int end) {
		List<String> missing_ranges = new ArrayList<>();
		int prec = start - 1;
		for (int i = 0; i <= arr.length; i++) {
			int curr = i == arr.length ? end + 1 : arr[i];
			if (curr - prec > 1) {
				missing_ranges.add(get_range(prec + 1, curr - 1));
			}
			prec = curr;
		}
		return missing_ranges;
	}

	private static String get_range(int i1, int i2) {
		return i1 == i2 ? Integer.toString(i1) : i1 + " -> " + i2;
	}

	private static Arr_Range str_2_arr_range(String str) {
		int index_of_opening_square_bracket = str.indexOf("[");
		int index_of_closing_square_bracket = str.indexOf("]");
		String[] str_arr = str.substring(0, index_of_opening_square_bracket).trim().split(",");
		
		Arr_Range result = new Arr_Range();
		result.arr = new int[str_arr.length];
		for (int i = 0; i < str_arr.length; i++) {
			result.arr[i] = Integer.parseInt(str_arr[i].trim());
		}
		String range_substr = str.substring(index_of_opening_square_bracket + 1, index_of_closing_square_bracket);
		String[] range = range_substr.trim().split(",");
		result.start = Integer.parseInt(range[0].trim());
		result.end = Integer.parseInt(range[1].trim());
		return result;
	}

	private static void print(Arr_Range arr_range) {
		System.out.printf("Start = %5d\n", arr_range.start);
		System.out.printf("End   = %5d\n", arr_range.end);
		for (int i = 0; i < arr_range.arr.length; i++) {
			System.out.printf("%5d", arr_range.arr[i]);
			if ((i + 1) % 10 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}
}