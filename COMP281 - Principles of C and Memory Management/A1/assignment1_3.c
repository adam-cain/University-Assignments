#include <stdio.h>

// Constant for max size of digits in arrays
#define MAX_DIGITS 99

// Calculate the length of a string
int stringLength(char *str){
    int i = 0;
    // Loop through the string until the end character '/0' is reached
    for (i; str[i] != '\0'; ++i);
    return i;
}

// Convert a character to an integer
int charToInt(char c) {
    return c - '0';
}

// Convert an integer to a character
int intToChar(int i){
    return i + '0';
}

// Increments an array at a certain index by the increment allowing for carrying numbers 
// during addtion
char* incrementArr(char* arr, int index, int increment){
    // Add the increment to the digit at the specified index
    int result = charToInt(arr[index]) + increment;

    // Set the digit at the specified index to the ones place of the result
    arr[index] = intToChar(result % 10);

    // If the result is greater than 9, it carries over to the next digit recursively
    if(result/10 > 0){
        // If the 0th digit in the array needs to carry durring addition the array suze 
        // needs to be expanded
        if(index == 0){ 
            // The array is shifted right one including the array stop character '/0'
            for (int k = stringLength(arr)+1; k >= 0; k--){      
                arr[k]=arr[k-1];
            }
            
            // The 0th index is set to one as the carry 
            arr[0] = '1';
            return arr;
        }
        else{
            // Recursivley increments the array
            return incrementArr(arr,index-1,1);
        }
    }
    // Return the array if there is no carry
    else{
        return arr;
    }
}

// Adds two arrays together, assumes i is longer than j or equal length
char* addArrays(char* i, char* j){
    int j_len = stringLength(j);

    // Finds the diffrence in size between i and j
    int diff = stringLength(i) - j_len;

    // Loops through the smaller array
    for (int x = j_len-1; x >= 0 ; x--)
    {
        // Increment array i at index x + diffrence in size by integar value j at index
        i = incrementArr(i,x+diff,charToInt(j[x]));
    }
    return i;
}

int main() {
    // Initialization
    char a[MAX_DIGITS], b[MAX_DIGITS];

    // Read input
    scanf("%s", a);
    scanf("%s", b);

    if (stringLength(a) < stringLength(b)){
        // Add b to a if b has more digits
        printf("%s",addArrays(b,a));
    }else{
        // Add a to b if a has more digits or has equal length
        printf("%s",addArrays(a,b));
    }
    return 0;
}