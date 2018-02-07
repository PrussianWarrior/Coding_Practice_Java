import java.util.*;
import java.lang.*;
import java.io.*;

public class Problem_19_Palindrome_Number {
	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				int counter = 1;			

				while (line != null) {
					int input = Integer.parseInt(line);					
					
					System.out.printf("%5d: %-1d\n", counter++, input);
					// System.out.println("Number of digits = " + num_of_digits(input));
					boolean soln_1 = is_palindrome_1(input);
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

	public static boolean is_palindrome_1(int n) {
		if (n < 0) {
			return false;
		}
		int num_of_digits = num_of_digits(n);
		int pow10 = (int)(Math.pow(10, num_of_digits - 1));
		while (n > 0) {
			if (n % 10 != n / pow10) {
				return false;
			}
			n = (n % pow10)/10;
			pow10 /= 100;
		}
		return true;
	}

	private static int num_of_digits(int n) {
		return (int)Math.floor(Math.log10(n)) + 1;
	}

}