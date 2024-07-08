import java.lang.Math;

public class SimpleCircle {
    public static void main (String args[]) {

        double r = 2.5;
        
        // replace the following two lines
        double area = Math.PI * (r*r);
        double circumference = 2 * Math.PI * r;
  
        System.out.println(r);
        System.out.println(area);
        System.out.println(circumference);
     }
}