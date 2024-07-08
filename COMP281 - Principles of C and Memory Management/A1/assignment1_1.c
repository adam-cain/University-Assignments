#include <stdio.h>

int main() {
    // Constants
    const float PI = 3.14;

    // Initialization
    float sumOfAreas = 0.0;
    float sumOfCircumferences = 0.0;

    // Input
    int radiusLower, radiusUpper;
    scanf("%d %d", &radiusLower, &radiusUpper);

    // Loop through all circles from radiusLower to radiusUpper
    for (int r = radiusLower; r <= radiusUpper; r++){
        // Compute area and circumference
        float area = PI * r * r;
        float circumference = 2 * PI * r;
        
        // Update sums
        sumOfAreas += area;
        sumOfCircumferences += circumference;
    }

    // Output results
    printf("%.3f\n%.3f", sumOfAreas, sumOfCircumferences);
    // printf("Press ENTER key to Continue\n");  
    // getchar(); 
    return 0;
}