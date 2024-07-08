//
// by Prudence Wong 2021-03-20
//
import java.util.*;
import java.io.*;


class COMP108W07App {

	private static Scanner keyboardInput = new Scanner (System.in);
	private static final int MaxCount = 100;

	public static void main(String[] args) throws Exception {
		COMP108W07 list = new COMP108W07();
		int count = 0, key = -1, headOrTail=0;

		try {
			System.out.println();
			System.out.print("Enter the number of values to store (1-" + MaxCount + "): ");
			count = keyboardInput.nextInt();
			if (count > MaxCount || count <= 0)
				System.exit(0);
			System.out.print("Enter " + count + " integers: ");
			for (int i=0; i<count; i++) {
				list.insertTail(new Node(keyboardInput.nextInt()));
			}
		}
		catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}

		
		System.out.println();
		System.out.println("Head to tail: " + list.headToTail());
		System.out.println("Tail to head: " + list.tailToHead());


		do {
			try {
				System.out.print("Enter a number to search (-1 to exit): ");
				key = keyboardInput.nextInt();
				if (key != -1) {
					System.out.print("Do you want to move it to (1) head or (2) tail: ");
					headOrTail = keyboardInput.nextInt();
					if (headOrTail == 1)
						list.searchMoveToHead(key);
					if (headOrTail == 2)
						list.searchMoveToTail(key);
					System.out.println("Head to tail: " + list.headToTail());
					System.out.println("Tail to head: " + list.tailToHead());
				}
			}
			catch (Exception e) {
				keyboardInput.next();
				System.exit(0);
			}
			
		} while (key != -1);	
	}

}
