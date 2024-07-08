// Coded by Prudence Wong 2021-12-30
//
// A class to store output
class COMP108A1Output {
	public int hitCount;
	public int missCount;
	public String hitPattern;
	public String cache;
	
	public COMP108A1Output() {
		hitCount = 0;
		missCount = 0;
		hitPattern = "";
		cache = "";
	}

	public void print() {
		System.out.println(hitPattern);
		System.out.println(hitCount + " h " + missCount + " m");
		System.out.println("Cache: " + cache);
	}	
}
