
// imports

public class Newton {
    public static void main(String[] args) {
        if (args.length == 2)
        {
            sqRoot(Double.parseDouble(args[0]), Double.parseDouble(args[1]), 0.0000001);
        }
        else if (args.length == 3)
        {
            sqRoot(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));;
        }
        else{
            System.out.println("Incorrect Number of Parameters\nUsage: java Newton number guess epsilon");
        }
    }

    public static void sqRoot(double n, double guess, double marginErr) {
        int itterations = 1;
        double diffrence = Math.abs(guess) - Math.abs(((n/guess)+guess)/2);
        while (Math.abs(diffrence) > marginErr){
            itterations ++;
            double temp = ((n/guess)+guess)/2;
            diffrence = Math.abs(guess) - Math.abs(temp);
            guess = temp;
        }
        System.out.println(itterations);
        System.out.println(guess);
    } 
}