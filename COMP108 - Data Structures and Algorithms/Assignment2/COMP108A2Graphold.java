//
// Coded by Prudence Wong 2022-03-13
//
// Name: Adam Cain	
// Student ID: 201572027
// University email address: sgacain2@liverpool.ac.uk
//
// Time Complexity and explanation:
// n denotes the number of vertices
//
//
// neighbourhood():
//	
//

class COMP108A2Graph {
	static int[][] neibourMatrix;
	static COMP108A2Node head;
	static COMP108A2Node tail;
	// input parameter: an integer distance
	// output: compute neighbourhood matrix for distance 
	static COMP108A2Output neighbourhood(int[][] adjMatrix, int gSize) {
		COMP108A2Output output = new COMP108A2Output(1, gSize);
		int count;
		neibourMatrix = new int[gSize][gSize];

		//j = start node
		for (int j = 0; j < gSize; j++) {
			emptyQ();
			pushQ(j);
			count = 0;
			Boolean[] visted = new Boolean[gSize];
			System.out.println("###"+head.data);
			printSquareArray(neibourMatrix, gSize);
			while(head!= null){
				COMP108A2Node curr = popQ(); 
				for (int i = 0; i < gSize; i++) {
					if(adjMatrix[curr.data][i] == 1 && visted[i] == null){
						//addEdge(1, curr.data, i);
						addEdge(count, i, j);
						System.out.println("Found: "+ curr.data+","+i);
						pushQ(i);
						//addEdge(count,curr.data,j);
						visted[i] =true;
					}
				}
				count++;
			}
		}
		// do not remove this last statement
		output.neighbourMatrix = neibourMatrix;
		System.out.println("\n");
		return output;
	}

	public static void addEdge(int distance, int x, int y){
		neibourMatrix[x][y] = distance;
		neibourMatrix[y][x] = distance;
	}

	public static void pushQ(int val) {
		COMP108A2Node newNode = new COMP108A2Node(val);
		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	public static COMP108A2Node popQ() {
		COMP108A2Node curr = head;

		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}

	public static void emptyQ() {
		while (head != null)
			popQ();
	}
	

	// DO NOT change this method, you can use it if you want
	static void printSquareArray(int array[][], int size) {
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				System.out.print(array[x][y] + " ");
			}
			System.out.println();
		}
	}
}

