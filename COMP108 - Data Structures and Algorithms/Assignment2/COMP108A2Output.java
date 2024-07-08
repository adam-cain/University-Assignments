//
// Coded by Prudence Wong 2021-03-06
// Updated by Prudence Wong 2022-03-06
// Do not change this file
// Changes in this file will NOT be graded
//
// A class to store output
//
class COMP108A2Output {
	public int hitCount;
	public int missCount;
	public int[] compare;
	public String cabFromHead;
	public String cabFromTail;
	public String cabFromHeadFreq;
	public int[][] neighbourMatrix;
	
	// Constructor
	// parameter size is used to define the size of the array to store number of comparisons
	public COMP108A2Output(int cabSize, int graphSize) {
		hitCount = 0;
		missCount = 0;
		cabFromHead = "";
		cabFromTail = "";
		cabFromHeadFreq = "";
		compare = new int[cabSize];
		for (int i=0; i<cabSize; i++)
			compare[i] = 0;
		neighbourMatrix = new int[graphSize][graphSize];
		for (int i=0; i<graphSize; i++)
			for (int j=0; j<graphSize; j++)
				neighbourMatrix[i][j] = 0;
		
	}

	// an auxiliary method to print its attributes in a readable form
	public void printCab(boolean freq) {
		System.out.println();
		for (int i=0; i<compare.length; i++)
			System.out.print(compare[i] + ",");
		System.out.println();
		System.out.println(hitCount + " h " + missCount + " m");
		System.out.println("From head to tail: " + cabFromHead);
		System.out.println("From tail to head: " + cabFromTail);
		if (freq == true)
			System.out.println("Frequency from head to tail: " + cabFromHeadFreq);
	}	

	// an auxiliary method to print its attributes in a readable form
	public void printGraph(int gSize) {
		System.out.println();
		for (int i=0; i<gSize; i++) {
			for (int j=0; j<gSize; j++)
				System.out.print(neighbourMatrix[i][j] + " ");
			System.out.println();
		}
	}	
}
