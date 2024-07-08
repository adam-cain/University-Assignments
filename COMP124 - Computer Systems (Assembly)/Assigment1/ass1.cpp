#include <iostream>
#include <stdio.h>
#include <stdlib.h>
using namespace std;
  
// Driver Code
int main()
{
    int pos = 0;
    int zero = 0;
    int neg = 0;
    int loop;
    int inp;
    cout << "How many numbers: ";
    cin >> loop;

    for(int i = 0; i < loop; i++){
      cout << "Enter a number: ";
      cin >> inp;
      if(inp == 0){
        zero++;
      }
      else if(inp>0){
        pos++;
      }
      else
      {
        neg++;
      }
    }

    cout << "------------\n";
    cout << "Positive: ";
    cout << pos;
    cout << "\nNegative: ";
    cout << neg;
    cout << "\nZero: ";
    cout << zero;

    cin >> inp;
    return 0;
}