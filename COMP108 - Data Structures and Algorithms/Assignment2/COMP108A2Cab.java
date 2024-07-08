import java.util.Scanner;

//
// Note: You are allowed to add additional methods if you need.
// Coded by Prudence Wong 2021-03-06
//
// Name: Adam Cain
// Student ID: 201572027 
// MWS username: sgacain2
//
// Time Complexity and explanation: 
// f denotes initial cabinet size
// n denotes the total number of requests 
// d denotes number of distinct requests
// You can use any of the above notations or define additional notation as you wish.
// 
// appendIfMiss():
// 	
// moveToFront():
// 	
// freqCount():
// 

class COMP108A2Cab {

	public COMP108A2Node head, tail;
	
	public COMP108A2Cab() {
		head = null;
		tail = null;
	}

	// append to end of list when miss
	public COMP108A2Output appendIfMiss(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
		COMP108A2Node currentNode;
		boolean hit;
		int count;
		for (int i = 0; i < rSize; i++) {
			currentNode = head;
			hit = false;
			count = 0;
			do{
				count++;
				if(currentNode.data == rArray[i]){
					output.hitCount++;
					output.compare[i] = count;
					hit = true;
					break;
				}
				currentNode = currentNode.next;
			}while(currentNode != null);
			if(!hit){
				insertTail(new COMP108A2Node(rArray[i]));
				output.missCount++;
				output.compare[i] = count;
			}
		}

		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		return output;
	}

	// move the file requested to the beginning of the list
	public COMP108A2Output moveToFront(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
		COMP108A2Node currentNode;
		boolean hit;
		int count;
		for (int i = 0; i < rSize; i++) {
			currentNode = head;
			hit = false;
			count = 0;
			do{
				count++;
				if(currentNode.data == rArray[i]){
					output.hitCount++;
					output.compare[i] = count;
					if(currentNode != head){
						insertHead(new COMP108A2Node(rArray[i]));
						currentNode.next.prev = currentNode.prev;
						currentNode.prev.next = currentNode.next;
					}
					hit = true;
					break;
				}
				currentNode = currentNode.next;
			}while(currentNode != null);
			if(!hit){
				insertHead(new COMP108A2Node(rArray[i]));
				output.missCount++;
				output.compare[i] = count;
			}
		}

		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		return output;	
	}

	// move the file requested so that order is by non-increasing frequency
	public COMP108A2Output freqCount(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
		
		COMP108A2Node currentNode;
		boolean hit;
		int count;
		for (int i = 0; i < rSize; i++) {
			currentNode = head;
			hit = false;
			count = 0;
			do{
				count++;
				if(currentNode.data == rArray[i]){
					currentNode.freq++;
					output.hitCount++;
					output.compare[i] = count;
					hit = true;
					if(currentNode != head){
						
						COMP108A2Node tempNode = currentNode.prev;
						COMP108A2Node highestFreqNode = null;
						
						do{
							if(currentNode.freq > tempNode.freq){
								highestFreqNode = tempNode;
							}
							tempNode = tempNode.prev;
						}while(tempNode!= null);
						if(highestFreqNode != null){
							System.out.println("Rearraging");
							//delete the node
							currentNode.next.prev = currentNode.prev;
							currentNode.prev.next = currentNode.next;
							//insert new node at place before
							currentNode.next = highestFreqNode;
							currentNode.prev = highestFreqNode.prev;
							
							if(highestFreqNode.prev != null){
							highestFreqNode.prev.next = currentNode;
							}
							highestFreqNode.prev = currentNode;
						}
					}
					break;
				}
				currentNode = currentNode.next;
			}while(currentNode != null);
			if(!hit){
				System.out.println("Insert new: " + rArray[i]);
				insertTail(new COMP108A2Node(rArray[i]));
				output.missCount++;
				output.compare[i] = count;
			}
			System.out.println(headToTail());
		}

		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		output.cabFromHeadFreq = headToTailFreq();
		return output;		
	}

	// private void sortByFreq(COMP108A2Node currentNode){
	// 	System.out.println("Sorting: "+currentNode.data);
	// 	COMP108A2Node currentNode = currentNode.prev;
	// 	COMP108A2Node highestFreqNode = null;
		
	// 	do{
	// 		if(currentNode.freq > currentNode.freq){
	// 			highestFreqNode = currentNode;
	// 		}
	// 		currentNode = currentNode.prev;
	// 	}while(currentNode!= null);
	// 	if(highestFreqNode == null){return;}
		
	// 	System.out.println("Rearraging");
	// 	currentNode = highestFreqNode;
	// 	System.out.println(currentNode);
	// 	highestFreqNode.next = currentNode.next;
	// 	highestFreqNode.prev = currentNode.prev;
	// 	currentNode.next = currentNode.next;
	// 	currentNode.prev = currentNode.prev;
	// 	System.out.println(headToTail());
	// 	return;
	// }

	// DO NOT change this method
	// insert newNode to head of list
	public void insertHead(COMP108A2Node newNode) {		

		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to tail of list
	public void insertTail(COMP108A2Node newNode) {

		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	public COMP108A2Node deleteHead() {
		COMP108A2Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}
	
	// DO NOT change this method
	// empty the cabinet by repeatedly removing head from the list
	public void emptyCab() {
		while (head != null)
			deleteHead();
	}


	// DO NOT change this method
	// this will turn the list into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTail() {
		COMP108A2Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the list into a String from tail to head
	// Only to be used for output, do not use it to manipulate the list
	public String tailToHead() {
		COMP108A2Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the frequency of the list nodes into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTailFreq() {
		COMP108A2Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.freq;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

}
