//201572027
//Student ID
#include <stdio.h>
#include <stdlib.h>


int main(void)
{
    //messages
    char inpMsgOne[] = "How many numbers: ";
    char inpMsgTwo[] = "Enter a number: ";
    char dividerMsg[] = "---------\n";
    char posMsg[] = "Positive: %d\n";
    char negMsg[] = "Negative: %d\n";
    char zeroMsg[] = "Zero:     %d\n";

    //input
    char fmt[] = "%d";
    int input = 0;

    //values
    int posNum = 0;
    int negNum = 0;
    int zeroNum = 0;

    _asm {
        //i/o
        lea eax, inpMsgOne // put address of string into eax
        call print // call to function on line
        call getInput // call to function on line

        mov ecx, input // set up loop counter
        floop : // for loop flag
            push ecx //push loop counter to stack

            //i/o
            lea eax, inpMsgTwo // put addr of string into eax
            call print
            call getInput 

            //if: determine if number is pos, neg or zero
            cmp input, 0
            je ifZero // jump to flag ifZero if input is equal to zero
            jl ifNeg // jump to flag ifNeg if input is less than zero

            //else. ie if positive
            add posNum, 1 // posNum++
            jmp endIf // jump to end of if

            //If zero
            ifZero:
                add zeroNum, 1//zeroNum++
                jmp endif
            //if negative
            ifNeg:
                add negNum, 1//negNum++
            endIf:

            pop ecx // pop loop counter from stack
            loop floop
        
        lea eax, dividerMsg
        call print

        push posNum // push posNum to stack
        lea eax, posMsg // load address of posNum into eax
        push eax // push eax to stack
        call printf // call the library function
        add esp, 8 // clear the stack as the size of posNum is not known

        push negNum
        lea eax, negMsg
        push eax
        call printf
        add esp, 8


        push zeroNum
        lea eax, zeroMsg
        push eax
        call printf
        add esp, 8
        
        hlt // halt program
        
    print:
        push eax // stack the parameter
        call printf // call library function
        pop eax // remove parameter
        ret

    getInput:
        lea eax, input // put addr of integar into eax
        push eax // stack the parameter
        lea eax, fmt // put addr of string into eax
        push eax //stack the parameter
        call scanf // call library function
        add esp, 8 // clear the stack
        ret
    };


    return 0;

}
