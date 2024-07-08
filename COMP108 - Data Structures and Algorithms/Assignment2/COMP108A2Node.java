//
// Coded by Prudence Wong 2021-03-06
// Do not change this file
// Changes in this file will NOT be graded
//
class COMP108A2Node {
	public int data; 
	public COMP108A2Node next;
	public COMP108A2Node prev;
	public int freq; // only to be used in freqCount algorithm
    
	// constructor to create a new node with data equals to parameter i
	public COMP108A2Node (int i) {
		next = null;
		prev = null;
		data = i;
		freq = 1;
	}
}
