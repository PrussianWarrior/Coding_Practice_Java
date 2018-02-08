import java.lang.*;
import java.util.*;
import java.io.*;

public class Problem_22_Swap_Nodes_In_Pairs {
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
					Node head = generate_list(Integer.parseInt(line.trim()));
					System.out.println("Before swapping");
					print_list(head);

					Node swap_head = swap_node_in_pairs(head);
					System.out.println("After swapping");
					print_list(swap_head);					
					
					System.out.println("--------------------------------------------------------------------------------------\n");
					line = br.readLine();
				}
			} catch (IOException io_exception) {
				System.err.println("IOException occurs");
				io_exception.printStackTrace();
			}
		}		
	}

	public static Node swap_node_in_pairs(Node head) {
		Node dummy_head = new Node(0);
		dummy_head.next = head;
		Node curr = head;
		Node prec = dummy_head;
		while (curr != null && curr.next != null) {
			Node curr_next = curr.next;
			Node curr_next_next = curr.next.next;
			prec.next = curr_next;
			curr_next.next = curr;
			curr.next = curr_next_next;
			prec = curr;
			curr = curr_next_next;
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

	private static Node generate_list(int len) {
		Node dummy_head = new Node(0);
		Node iter = dummy_head;
		for (int i = 1; i <= len; i++) {
			iter.next = new Node(i);
			iter = iter.next;
		}
		return dummy_head.next;
	}
}
