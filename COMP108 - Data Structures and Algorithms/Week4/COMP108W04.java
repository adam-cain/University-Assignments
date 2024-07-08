//
// Enter your name: Adam Cain
// Enter your student ID: 201572027
//

class COMP108W04 {

	// print the content of an array of size n
	static void printArray(int[] data, int n) {
		int i;

		for (i=0; i < n; i++)
			System.out.print(data[i] + " ");
		System.out.println();
	}

	// Input: array1[] with size1 entries and array2[] with size2 entries
	// print all entries of array1[] that does not exist in array2[]
	
	// time complexity of method is O(n^2), as it is a nested loop if given n 
	//and m if n increases by one it will increase by +m iterations
	static void notExists(int array1[], int size1, int array2[], int size2) {
		for(int one = 0; one < size1; one++){
			boolean exists = true;
			for(int two = 0; two < size2; two++){
				if(array1[one] == array2[two]){
					exists = false;
				}
			}
			if(exists) System.out.print(array1[one]+" ");
		}
	}
		
	// Input: array1[] with size1 entries and array2[] with size2 entries
	// for each entry in array2[], count how many times it appears in array1[]

	// time complexity of method is O(n^2), as it is a nested loop if given n 
	//and m if n increases by one it will increase by +m iterations
	static void count(int array1[], int size1, int array2[], int size2) {
		for(int two = 0; two < size2; two++){
			int count = 0;
			for(int one = 0; one < size1; one++){
				if(array1[one] == array2[two]){
					count++;
				}
			}
			System.out.print(count +" ");
		}
	}

}
