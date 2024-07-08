#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    int num = 10;

    _asm {

        mov eax, num
        add eax, 12
        mov num, eax

    }

    return 0;

}
