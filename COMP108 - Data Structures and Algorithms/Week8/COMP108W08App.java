//
// Coded by Prudence Wong 2022-03-13
//

import java.util.*;
import java.io.*;

class COMP108W08App {

	private static final int MaxVertex = 10;
	private static final int MinVertex = 2;
	private static Scanner keyboardInput = new Scanner (System.in);
	// adjacency matrix, adjMatrix[i][j] = 1 means i and j are adjacent, 0 otherwise
	private static int[][] adjMatrix = new int[MaxVertex][MaxVertex];
	private static int numVertex; // total number of vertices

	// DO NOT change the main method
	public static void main(String[] args) throws Exception {
		boolean userContinue=true;
		int v1, v2, choice=1, distance=0;
		
		input();

		System.out.println("Degree: " + COMP108W08.findDegree(adjMatrix, numVertex));

		try {
			while (choice != -1) {
				System.out.print("Want to continue (1 to continue, -1 to exit): ");
				choice = keyboardInput.nextInt();
				if (choice != -1) {
					System.out.print("Enter two vertex indices (0-" + (numVertex-1) + ") : ");
					v1 = keyboardInput.nextInt();
					v2 = keyboardInput.nextInt();
					if (v1 >= 0 && v1 < numVertex && v2 >= 0 && v2 < numVertex) {
						distance = COMP108W08.distance(adjMatrix, numVertex, v1, v2);
						if (distance < 0) {
							System.out.println("Vertex " + v1 + " and " + v2 + " are not distance 2 apart.");
						} else {
							System.out.println("Distance between vertex " + v1 + " and " + v2 + ": " + distance);
						}
					}
				}
			}
		}
		catch (Exception e) {
			keyboardInput.next();
		}
		
	
	}

	// DO NOT change this method
	static void printSquareArray(int array[][], int size) {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	// DO NOT change this method
	static void input() {
		int i, j;
		boolean success = false;

		try {
			success = true;
			System.out.print("How many vertices (" + MinVertex + "--" + MaxVertex + ")? ");
			numVertex = keyboardInput.nextInt();
			if (numVertex > MaxVertex || numVertex < MinVertex) {
				success = false;
			}
			if (success) {
				System.out.println("Enter adjacency matrix: ");
				for (i=0; i<numVertex; i++)
					for (j=0; j<numVertex; j++)
						adjMatrix[i][j] = keyboardInput.nextInt();
				for (i=0; i<numVertex && success; i++) {
					if (adjMatrix[i][i] != 0)
						success = false;
					for (j=0; j<numVertex; j++) {
						if (adjMatrix[i][j] != adjMatrix[j][i])
							success = false;
					}
				}
			}
			if (!success) {
				System.out.print("Incorrect range ");
				System.out.print("or adjacency matrix not symmetric ");
				System.out.println("or vertex connected to itself");
				System.exit(0);
			}
		}
		catch (Exception e) {
			keyboardInput.next();
		}
	}

}

