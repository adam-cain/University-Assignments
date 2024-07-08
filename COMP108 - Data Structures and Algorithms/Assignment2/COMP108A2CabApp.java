//
// Coded by Prudence Wong 2021-03-06
// Updated by Prudence Wong 2022-03-06
// Do not change this file
// Changes in this file will NOT be graded
//
import java.util.*;
import java.io.*;


class COMP108A2CabApp {

	private static final int MaxInitSize = 10; // maximum cabinet size to start with
	private static final int MaxReqSize = 100; // maximum number of requests

	private static Scanner keyboardInput = new Scanner (System.in);

	public static void main(String[] args) throws Exception {
		COMP108A2Cab cab = new COMP108A2Cab();
		COMP108A2Output output = new COMP108A2Output(MaxReqSize, 1);
		int[] request = new int[MaxReqSize];
		int[] initCab = new int[MaxInitSize];
		int reqSize = 0;
		int initSize = 0;
		
		try {
			System.out.println();
			System.out.print("Enter the initial number of files in the cabinet (1-" + MaxInitSize + "): ");
			initSize = keyboardInput.nextInt();
			if (initSize > MaxInitSize || initSize <= 0)
				System.exit(0);
			System.out.print("Enter the initial file IDs in the cabinet (" + initSize + " different +ve integers): ");
			for (int i=0; i<initSize; i++)
				initCab[i] = keyboardInput.nextInt();				
			System.out.println();
			System.out.print("Enter the number of file requests (1=" + MaxReqSize + "): ");
			reqSize = keyboardInput.nextInt();
			if (reqSize > MaxReqSize || reqSize <= 0)
				System.exit(0);
			System.out.print("Enter the request file IDs (" + reqSize + " +ve integers): ");
			for (int i=0; i<reqSize; i++)
				request[i] = keyboardInput.nextInt();				
		}
		catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}

		System.out.println();
		System.out.println("Initial cabinet:");
		printArray(initCab, initSize);
		System.out.println("Request sequence:");
		printArray(request, reqSize);

		try {
			System.out.println();
			System.out.print("appendIfMiss");
			// create a list with the input data
			// call appendIfMiss()
			cab.emptyCab();
			for (int i=initSize-1; i>=0; i--) {
				cab.insertHead(new COMP108A2Node(initCab[i]));
			}
			output = cab.appendIfMiss(request, reqSize);
			output.printCab(false);
		}
		catch (Exception e) {
			errTraceMsg(e, "appendIfMiss");
		}

		try {
			System.out.println();
			System.out.print("moveToFront");
			// create a list with the input data
			// call moveToFront()
			cab.emptyCab();
			for (int i=initSize-1; i>=0; i--) {
				cab.insertHead(new COMP108A2Node(initCab[i]));
			}
			output = cab.moveToFront(request, reqSize);
			output.printCab(false);
		}
		catch (Exception e) {
			errTraceMsg(e, "moveToFront");
		}

		try {
			System.out.println();
			System.out.print("freqCount");
			// create a list with the input data
			// call freqCount()
			cab.emptyCab();
			for (int i=initSize-1; i>=0; i--) {
				cab.insertHead(new COMP108A2Node(initCab[i]));
			}
			output = cab.freqCount(request, reqSize);
			output.printCab(true);
		}
		catch (Exception e) {
			errTraceMsg(e, "freqCount");
		}

	
	}


	// Do NOT change this method!
	// print array[0]..array[size-1]
	static void printArray(int[] array, int size) {
		for (int i=0; i<size; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	static void errTraceMsg(Exception e, String errString) {
			System.err.println("Exception: " + errString);
			e.printStackTrace();
	}

}
