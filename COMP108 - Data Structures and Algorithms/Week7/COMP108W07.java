//
// by Prudence Wong 2021-03-20
//
// Name: Adam Cain
// Student ID: 201572027
//

class COMP108W07 {

	public Node head, tail;
	
	public COMP108W07() {
		head = null;
		tail = null;
	}

	// sequential search if key is in the list
	// move the node to head if found, do nothing if not found
	public void searchMoveToHead(int key) {
		Node currentNode = head;
		do {
			if (currentNode.data == key){
				if(currentNode != head){
					currentNode.prev.next= currentNode.next;
					if(currentNode != tail){
						currentNode.next.prev= currentNode.prev;
					}
					else
					{
						tail = currentNode.prev;
					}
					insertHead(currentNode);
				}
				break;
			}
			currentNode = currentNode.next;
		} while (currentNode != null);
	}


	// sequential search if key is in the list
	// move the node to tail if found, do nothing if not found
	public void searchMoveToTail(int key) {
		Node currentNode = head;
		do {
			if (currentNode.data == key){
				if(currentNode != tail){
					if(currentNode != head){
						currentNode.prev.next= currentNode.next;
					}
					else
					{
						head = currentNode.next;
					}
					currentNode.next.prev= currentNode.prev;
					insertTail(currentNode);
				}
				break;
			}
			currentNode = currentNode.next;
		} while (currentNode != null);
	}


	// DO NOT change this method
	// insert newNode to the head of the list
	public void insertHead(Node newNode) {
		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to the tail of the list
	public void insertTail(Node newNode) {
		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// this will turn the list into a String from head to tail
	public String headToTail(){
		Node curr;
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
	public String tailToHead(){
		Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

}
