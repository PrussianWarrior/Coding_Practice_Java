import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_20_Merge_2_Sorted_List {
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
					List<Integer> data = process_input(line);
					Node list_1 = generate_sorted_list(data.get(0), data.get(1), data.get(2));
					Node list_2 = generate_sorted_list(data.get(3), data.get(4), data.get(5));
					int[] list_1_len = {0};
					int[] list_2_len = {0};
					boolean is_list_1_sorted = is_list_sorted(list_1);
					boolean is_list_2_sorted = is_list_sorted(list_2);					

					System.out.println("Sorted list 1");
					print_list(list_1, list_1_len);
					System.out.println("Length = " + list_1_len[0]);					
					System.out.println("List 1 is sorted = " + is_list_1_sorted + "\n");
					if (!is_list_1_sorted) {
						System.out.println("FAILED");
						break;
					}

					System.out.println("Sorted list 2");
					print_list(list_2, list_2_len);
					System.out.println("Length = " + list_2_len[0]);					
					System.out.println("List 2 is sorted = " + is_list_2_sorted + "\n");
					if (!is_list_2_sorted) {
						System.out.println("FAILED");
						break;
					}

					System.out.println("list_1.data = " + list_1.data);
					System.out.println("list_2.data = " + list_2.data);
					Node merged_list = merge_2_sorted_list_1(list_1, list_2);
					boolean is_merged_list_sorted = is_list_sorted(merged_list);

					int[] merged_list_len = {0};
					System.out.println("Merged sorted list");
					print_list(merged_list, merged_list_len);
					System.out.println("Length = " + merged_list_len[0]);					
					System.out.println("Merged list is sorted = " + is_merged_list_sorted + "\n");
					if (!is_merged_list_sorted) {
						System.out.println("FAILED");
						break;
					}
					System.out.println("merged_list.data = " + merged_list.data);

					if (merged_list_len[0] != list_1_len[0] + list_2_len[0]) {
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

	public static Node merge_2_sorted_list_1(Node list_1, Node list_2) {
		Node dummy_head = new Node(0);
		Node iter = dummy_head;
		while (list_1 != null && list_2 != null) {
			if (list_1.data <= list_2.data) {
				iter.next = list_1;
				list_1 = list_1.next;
			} else {
				iter.next = list_2;
				list_2 = list_2.next;
			}
			iter = iter.next;
		}
		if (list_1 != null) {
			iter.next = list_1;
		} else if (list_2 != null) {
			iter.next = list_2;
		}
		return dummy_head.next;
	}

	private static Node generate_sorted_list(int len, int min, int max) {
		int[] temp = new int[len];
		for (int i = 0; i < len; i++) {
			temp[i] = (int)(Math.random() * (max - min + 1)) + min;
		}
		Arrays.sort(temp);

		Node head = new Node(0);
		Node iter = head;
		for (int n : temp) {		
			iter.next = new Node(n);
			iter = iter.next;
		}
		return head.next;
	}

	private static boolean is_list_sorted(Node head) {
		for (Node curr = head; head != null; head = head.next) {
			if (curr != null) {
				if (curr.data > head.data) {
					return false;
				}
				curr = head;
			}
		}
		return true;
	}

	private static void print_list(Node head, int[] len) {
		int counter = 1;
		while (head != null) {
			len[0]++;
			System.out.printf("%5d", head.data);
			if (counter % 20 == 0) {
				System.out.println();
			}
			counter++;
			head = head.next;
		}
		System.out.println();
	}

	private static List<Integer> process_input(String line) {
		String[] words = line.split(" ");
		List<Integer> data = new ArrayList<>();
		for (String word : words) {
			data.add(Integer.parseInt(word.trim()));
		}
		return data;
	}

}