import java.util.Stack;

//
// Enter your name: Adam Cain
// Enter your student ID: 201572027
//
class COMP108W08 {

	// findDegree() find the degree of a graph
	// which is maximum degree of vertices.
	// Degree of a vertex is the number of neighbours it has.
	// adjMatrix[][] is adjacency matrix, gSize is number of vertices
	static int findDegree(int[][] adjMatrix, int gSize) {
		int degree = 0;
		for (int[] row : adjMatrix) {
			int rowCount = 0;
			for (int vertice : row) {
				if(vertice==1){
					rowCount++;
				}
			}
			if(rowCount > degree){
				degree = rowCount;
			}
		}
		return degree;
	}

	// distance() takes two vertex indices and check if their distance
	// is up to distance 2. Return -1 if they are not connected at distance 2.
	static int distance(int[][] adjMatrix, int gSize, int v1, int v2){
		if(v1==v2){return -1;}
		boolean visted[] = new boolean[gSize];
		return distanceRec(adjMatrix,gSize,v1,v2, visted,0);
	}

	static int distanceRec(int[][] adjMatrix, int gSize, int currentV, int targetV, boolean[] visited, int depth){
		int distance=-1;
		visited[currentV] = true;

		if(currentV == targetV){return depth;}
		if(depth > 2){return -1;}
		depth++;
		for (int i = 0; i < gSize; i++) {
			if(adjMatrix[currentV][i] == 1 && !visited[i]){
				distance = distanceRec(adjMatrix, gSize, i, targetV, visited, depth);
			} 
			if(distance!= -1){return distance;}
		}
		return distance;	
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

