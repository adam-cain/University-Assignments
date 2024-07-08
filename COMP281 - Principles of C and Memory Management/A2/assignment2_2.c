#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// define constant values
#define MAX_LINE_LENGTH 100
#define MAX_WORD_LENGTH 20

// define custom data types
typedef struct
{
    char *word; // a pointer to a character array storing the word
    int count; // the frequency of the word in the input
} Word;

typedef struct
{
    Word *words; // a pointer to an array of Word structs
    int size; // the number of elements in the array
} WordList;

// function to add a word to the wordlist
void addWord(WordList *list, char *word)
{
    // search for the word in the word list
    for (int i = 0; i < list->size; i++)
    {
        if (strcmp(list->words[i].word, word) == 0)
        {
            // word already exists, increment count and return
            list->words[i].count++;
            return;
        }
    }

    // allocate more memory to fit an extra word
    list->words = realloc(list->words, (list->size + 1) * sizeof(Word));

    // add word to the list
    list->words[list->size].word = strdup(word);
    list->words[list->size].count = 1;
    list->size++;
}

// function to print the words and their frequencies in the wordlist
void printWords(const WordList *list)
{
    for (int i = 0; i < list->size; i++)
    {
        printf("%s => %d", list->words[i].word, list->words[i].count);
        if (i != list->size - 1)
        {
            printf("\n");
        }
    }
}

// function to compare two Word structs by their word strings
int compareWords(const void *a, const void *b)
{
    Word *wordA = (Word *)a;
    Word *wordB = (Word *)b;
    return strcmp(wordA->word, wordB->word);
}

// function to sort the words in the wordlist alphabetically
void sortWords(WordList *list)
{
    qsort(list->words, list->size, sizeof(Word), compareWords);
}

int main()
{
    // initialize an empty wordList
    WordList list = {NULL, 0};
    // initialize character arrays to store input line and individual words
    char line[MAX_LINE_LENGTH];
    char word[MAX_WORD_LENGTH] = "";

    // loop through input lines until end of file
    while (fgets(line, MAX_LINE_LENGTH, stdin) != NULL)
    {
        // loop through each character in the line
        for (int i = 0; i < strlen(line); i++)
        {
            // if the character is an uppercase letter, convert to lowercase
            if (line[i] >= 'A' && line[i] <= 'Z')
            {
                line[i] = line[i] + 32;
            }
            // if the character is a lowercase letter, add to the current word
            if (line[i] >= 'a' && line[i] <= 'z')
            {
                int len = strlen(word);
                *(word + len) = line[i];
                *(word + len + 1) = '\0';
            }
            // if the character is not a letter and the current word is not empty,
            // add the word to the wordlist and reset the word buffer
            else
            {
                if (strlen(word) != 0 && line[i] != '-' && line[i] != '\'')
                {
                    addWord(&list, word);
                    memset(word, 0, sizeof(word));
                }
            }
        }
    }

    // sort the wordlist and print the results
    sortWords(&list);
    printWords(&list);
    return 0;
}
