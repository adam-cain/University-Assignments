#include <stdio.h>

int main() {
    // Initialization
    int a, b, n, remainder, quotient;
    // Inputs
    scanf("%d %d %d", &a, &b, &n);

    // Perform integer division to get the quotient and remainder
    quotient = a / b;
    remainder = a % b;

    // Loop n times, calculating the next decimal digit each time
    for (int i = 0; i < n; i++){
        // Multiply remainder by 10 to get the next decimal place
        remainder *= 10;

        // Calculate the next digit by integer division
        int digit = remainder / b;

        // Update remainder to be the new remainder
        remainder %= b;

        // Print the nth digit after the decimal point and exit the loop
        if (i == n - 1){
            printf("%d", digit);
            break;
        }
    }
    return 0;
}