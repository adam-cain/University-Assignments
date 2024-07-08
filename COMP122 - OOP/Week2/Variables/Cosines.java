public class Cosines {
    public static void main(String[] args) {
        double a = 3.0;
        double b = 5.0;
        double theta = (2 * Math.PI) / 3;
        double c = Math.sqrt((a*a) + (b*b) - 2 * a * b * Math.cos(theta));

        System.out.println(c);
    }    
}