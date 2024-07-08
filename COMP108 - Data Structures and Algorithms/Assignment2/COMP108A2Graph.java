//
// Coded by Prudence Wong 2022-03-13
//
// Name:
// Student ID:
// University email address:
//
// Time Complexity and explanation:
// n denotes the number of vertices
//
//
// neighbourhood():
//	
//



class COMP108A2Graph {


	// input parameter: an integer distance
	// output: compute neighbourhood matrix for distance 
	static COMP108A2Output neighbourhood(int[][] adjMatrix, int gSize) {
		COMP108A2Output output = new COMP108A2Output(1, gSize);

		// do not remove this last statement
		return output;
	}

	// DO NOT change this method, you can use it if you want
	static void printSquareArray(int array[][], int size) {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}


}

