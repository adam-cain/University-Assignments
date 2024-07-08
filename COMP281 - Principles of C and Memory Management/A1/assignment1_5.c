#include <stdio.h>

int main() {
    // Initialization
    int currentNumber;
    
    // Loop through and scan each decimal integer until it reaches the end of file
    while(scanf("%d", &currentNumber) != EOF){
        // Convert integer to character and print to line
        printf("%c", (char) currentNumber);
    } 
    return 0;
}