#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    int signed count1 = -10;
    int count2 = 5;
    int num = 0;

    _asm {

        mov eax, count1   // Equivalent to: 
        add eax, count2   // num = count1 + count2 - 10; 
        sub eax, 10
        mov num, eax

    }

    return 0;

}
