//
// Coded by Prudence Wong 2021-12-29
//
// NOTE: You are allowed to add additional methods if you need.
//
// Name: Adam Cain
// Student ID: 201572027
//
// Time Complexity and explanation: You can use the following variables for easier reference.
// n denotes the number of requests, p denotes the size of the cache
// n and p can be different and there is no assumption which one is larger

// n = request size		p = cache size

// noEvict():
// O(n^p) - This is due to a nested for loop where the first loop, loops through the request, 
// and the second loop, loops through the cache until it finds the request or not, allowing the
// algorithim to scale in time proportionally to both n and p.

// evictFIFO():
// O(n^p) - This is no diffrent to the noEvict() alogrithim as the only diffeence between the
// two is that the FIFO has an extra if statement that'll remove the first in the cache

// evictLFU():
// O(p+n^2p) - Breaking it down the O(p) alone is due to a for loop at the start that fills an array
// with 1. This is then added to n^2p which is due to a loop of n iterating through requests
// with two nested loops inside of 2p, one which iterates through the caches to find a match and another
// to find the least frequently used cached element.

// evictLRU():
// O(p+n^((p^p^p)+p)) - Breaking it down the O(p) alone is due to a for loop at the start that fills an array
// from 0 to cSize n+1. This is then added to n^((p^p^p)+p) which is due to a loop of n iterating through requests.
// With a triple nested loop of the size of the cache, the first iterates through the cache to find if the 
// request matches. If it does match another loop iterates through an array of the index of the least recentlaly 
// used elements in the cache. Once the least recently used is found another loop rearranges that array into the correct
// order. Outside of this loop but inside the loop of the cache n there is another loop which in the case the request is 
// not in the cache correctly reorders and inserts the new element into the cache.

class COMP108A1Paging {
	// no eviction
	// Aim: 
	// do not evict any page
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108A1Output
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108A1Output noEvict(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108A1Output output = new COMP108A1Output();
		char[] hitPatternArr = new char[rSize];

		for(int r = 0; r < rSize; r++){
			hitPatternArr[r] = 'm';
			for(int c = 0; c < cSize; c++){
				if(rArray[r] == cArray[c]){
					hitPatternArr[r] = 'h';
					output.hitCount++;
					break;
				}
			}
		}

		output.hitPattern = new String(hitPatternArr);
		output.missCount = rSize - output.hitCount;
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// evict FIFO
	// Aim: 
	// if a request is not in cache, evict the page present in cache for longest time
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108A1Output
	// Input: 
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108A1Output evictFIFO(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108A1Output output = new COMP108A1Output();
		boolean inCache = false;
		int fifoIndex = 0;
		char[] hitPatternArr = new char[rSize];

		for(int r = 0; r < rSize; r++){
			hitPatternArr[r] = 'm';
			inCache = false;
			for(int c = 0; c < cSize; c++){
				if(rArray[r] == cArray[c]){
					hitPatternArr[r] = 'h';
					output.hitCount++;
					inCache = true;
					break;
				}
			}
			if(!inCache){
				cArray[fifoIndex] = rArray[r];
				fifoIndex++;
			}
		}

		output.hitPattern = new String(hitPatternArr);
		output.missCount = rSize - output.hitCount;
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// evict LFU
	// Aim:
	// if a request is not in cache, evict the page that is least freqently used since in the cache
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108A1Output
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108A1Output evictLFU(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108A1Output output = new COMP108A1Output();
		boolean inCache = false;
		char[] hitPatternArr = new char[rSize];
		int[] accesFreq = new int[cSize];
		// O(p)
		for(int i = 0; i< cSize; i++){
			accesFreq[i] = 1;
		}

		// O(n^2p)
		for(int r = 0; r < rSize; r++){		
			hitPatternArr[r] = 'm';
			inCache = false;
			for(int c = 0; c < cSize; c++){
				if(rArray[r] == cArray[c]){
					hitPatternArr[r] = 'h';
					accesFreq[c]++;
					output.hitCount++;
					inCache = true;
					break;
				}
			}
			if(!inCache){
				int lowestCount = accesFreq[0];
				int index = 0;
				for (int i = 1; i < cSize; i++) {
					if(accesFreq[i] < lowestCount){
						lowestCount = accesFreq[i];
						index = i;
					}
				}
				cArray[index] = rArray[r];
				accesFreq[index] = 1;
			}
		}

		output.hitPattern = new String(hitPatternArr);
		output.missCount = rSize - output.hitCount;
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// evict LRU
	// Aim:
	// if a request is not in cache, evict the page that hasn't been used for 
	// the longest amount of time
	// count number of hit and number of miss, and find the hit-miss pattern;
	// return an object COMP108A1Output
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108A1Output evictLRU(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108A1Output output = new COMP108A1Output();
		boolean inCache;
		char[] hitPatternArr = new char[rSize];
		int[] LRUIndex = new int[cSize];
		//O(p)
		for (int i = 0; i < cSize; i++) {
			LRUIndex[i] = i;
		}
		//O(n^(3p+p))
		for (int r = 0; r < rSize; r++) {
			hitPatternArr[r] = 'm';
			inCache= false;
			for (int c = 0; c < cSize; c++) {
				if(cArray[c] == rArray[r]){
					hitPatternArr[r] = 'h';
					output.hitCount++;
					inCache = true;
					for (int i = 0; i < cSize; i++) {
						if(LRUIndex[i] == c){
							int temp = LRUIndex[i];
							for (int j = i; j < cSize-1; j++) {
								LRUIndex[j] = LRUIndex[j+1];
							}
							LRUIndex[cSize-1] = temp;
							break;
						}
					}
					break;
				}
			}
			if(!inCache){
				cArray[LRUIndex[0]] = rArray[r];
				int temp = LRUIndex[0];
				for (int i = 0; i < cSize-1; i++) {
					LRUIndex[i] = LRUIndex[i+1];
				}
				LRUIndex[cSize-1] = temp;
			}
		}

		output.hitPattern = new String(hitPatternArr);
		output.missCount = rSize - output.hitCount;
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// DO NOT change this method
	// this will turn the cache into a String
	// Only to be used for output, do not use it to manipulate the cache
	static String arrayToString(int[] array, int size) {
		String outString="";
		
		for (int i=0; i<size; i++) {
			outString += array[i];
			outString += ",";
		}
		return outString;
	}

}

