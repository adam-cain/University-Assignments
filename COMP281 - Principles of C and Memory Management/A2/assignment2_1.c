#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// define constant values
#define MAX_LINE_LENGTH 100

// function to output compressed character sequence
void output(char character, int count)
{
    if (character != 0)
    {
        if (count >= 3)
        {
            printf("%c%c%c%d*", character, character, character, count);
        }
        else
        {
            for (int i = 0; i < count; i++)
            {
                printf("%c", character);
            }
        }
    }
}

// function to compress input
void compress()
{
    int count = 1;
    char prevChar, currentChar;
    getchar(); // consume the newline character left in the input buffer
    while ((currentChar = getchar()) != EOF) // read characters until end of file
    {
        if (currentChar == '\n') // if newline character is encountered, output compressed character sequence
        {
            output(prevChar, count);
            count = 1; // reset count for new sequence
        }
        else if (prevChar == currentChar) // if current character is same as previous character, increment count
        {
            count++;
        }
        else // if current character is different from previous character, output compressed character sequence
        {
            output(prevChar, count);
            count = 1; // reset count for new sequence
        }
        prevChar = currentChar; // update previous character for next iteration
    }
    output(prevChar, count); // output compressed character sequence for last sequence in input
}

// function to print characters between two indices in a string
void printBetween(int startIndex, int endIndex, char *input)
{
    for (int i = startIndex; i < endIndex; i++)
    {
        printf("%c", input[i]);
    }
}

// function to convert a character to its integer value
int charToInt(char character){
    return character - '0';
}

// function to expand compressed input
void expand()
{
    char input[MAX_LINE_LENGTH];
    fgets(input, MAX_LINE_LENGTH, stdin);
    // continue reading lines from input until there are no more
    while (fgets(input, MAX_LINE_LENGTH, stdin))
    {
        int lastEncoded = 0;
        // loop through each character in the input array
        for (int i = 0; i < strlen(input); i++)
        {
            int index = i - 1;
            // check if the current character is '*' and the previous character is a number
            if (input[i] == '*' && (input[index] >= '0' && input[index] <= '9'))
            {
                // get the number of characters to repeat from the previous character
                int number = charToInt(input[index]);
                // if the previous previous character is also a number, get the tens digit
                if (index != 0 && (input[index - 1] >= '1' && input[index - 1] <= '9'))
                {
                    index--;
                    number += charToInt(input[index]) * 10;
                }
                // decrement index again to get the character to be repeated
                index--;
                // check if the previous 3 characters match
                if (index-2 >= 0 && input[index] == input[index - 1] && input[index - 1] == input[index - 2])
                {
                    // print the characters between the last encoded position and the repeated character
                    printBetween(lastEncoded, index - 2, input);
                    // print the repeated character the number of times specified
                    for (int z = 0; z < number; z++)
                    {
                        printf("%c", input[index]);
                    }
                    // update lastEncoded to the end of the repeated character
                    lastEncoded = i + 1;
                }
            }
            // if we have reached the end of the input array, print the characters from the last encoded position
            if (i == strlen(input) - 1)
            {
                printBetween(lastEncoded, strlen(input), input);
            }
        }
    }
}

int main()
{
    // read a character from input
    char option;
    scanf("%c", &option);
    // if option is 'C', compress the input
    if (option == 'C')
    {
        compress();
        printf("\n");
    }
    // if option is 'E', expand the input
    else if (option == 'E')
    {
        expand();
    }
    return 0;
}
