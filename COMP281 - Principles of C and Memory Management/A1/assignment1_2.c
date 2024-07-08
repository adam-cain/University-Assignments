#include <stdio.h>

// Constants
// Buffer size for char array to make string
#define BUFFER_SIZE 256

int main() {
    // Initialization
    char str[BUFFER_SIZE];
    int englishCount=0, digitCount=0, spaceCount=0, otherCount=0;

    // Input
    // Using fgets to include whitespaces when reading
    fgets(str, sizeof(str), stdin);
    
    // Loop through each character in str until reaches null character
    for(int i = 0; str[i]!='\0'; i++){
        // Check if character is upper or lower case character
        if((str[i]>='a' && str[i]<='z') || (str[i]>='A' && str[i]<='Z')){
            englishCount++;
        }
        // Check if character is a digit
        else if(str[i]>='0' && str[i]<='9'){
            digitCount++;
        }
        // Check if character is an empty space
        else if(str[i]==' '){
            spaceCount++;
        }
        else{
            otherCount++;
        }
    }

    // Output results
    printf("%d %d %d %d", englishCount, digitCount, spaceCount, otherCount);
    
    //printf("Press ENTER key to Continue\n");  
    //getchar(); 
    return 0;
}