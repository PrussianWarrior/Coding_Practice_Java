import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_21_Add_Two_Numbers_LL {
	private static class Node {
		int data;
		Node next;
		Node(int data) {
			this.data = data;
		}
	}

	public static void main(String[] args) {
		for (String file_name : args) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file_name));
				String line = br.readLine();
				int counter = 1;

				while (line != null) {
					System.out.printf("%5d: %-1s\n", counter++, line);
					String[] data = process_input(line);
					Node num_1 = convert_int_2_list(data[0]);
					Node num_2 = convert_int_2_list(data[1]);
					Node sum = add_2_reversed_int_1(num_1, num_2);

					System.out.printf("%10s: ", "Num 1");
					print_list(num_1);
					System.out.printf("%10s: ", "Num 2");
					print_list(num_2);
					System.out.printf("%10s: ", "Sum");
					print_list(sum);
					
					System.out.println("--------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				System.err.println("IOException occurs");
				io_exception.printStackTrace();
			}
		}		
	}

	public static Node add_2_reversed_int_1(Node num_1, Node num_2) {
		Node dummy_head = new Node(0);
		Node iter = dummy_head;
		int carry = 0;

		while (num_1 != null || num_2 != null) {
			int sum = carry;
			if (num_1 != null) {
				sum += num_1.data;
				num_1 = num_1.next;
			}

			if (num_2 != null) {
				sum += num_2.data;
				num_2 = num_2.next;
			}

			iter.next = new Node(sum % 10);
			iter = iter.next;
			carry = sum / 10;
		}
		if (carry != 0) {
			iter.next = new Node(carry);
		}
		return dummy_head.next;
	}

	private static Node convert_int_2_list(String int_str) {
		Node dummy_head = new Node(0);
		Node iter = dummy_head;
		for (char c : int_str.toCharArray()) {
			iter.next = new Node((int)(c - '0'));
			iter = iter.next;
		}
		return dummy_head.next;
	}

	private static void print_list(Node head) {
		while (head != null) {		
			System.out.print(head.data + " ");
			head = head.next;
		}
		System.out.println();
	}

	private static String[] process_input(String line) {
		String[] words = line.split(" ");
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].trim();
		}
		return words;
	}

}