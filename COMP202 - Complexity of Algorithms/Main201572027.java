import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main201572027 {

    public static ArrayList ReadData(String pathname) {
        ArrayList strlist = new ArrayList();
        try {

            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            int j = 0;
            String line = "";
            while ((line = br.readLine()) != null) {
                strlist.add(line);
            }
            return strlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strlist;
    }

    public static ArrayList DataWash(ArrayList Datalist) {
        ArrayList AIS = new ArrayList();
        ArrayList IS = new ArrayList();
        for (int i = 0; i < Datalist.size(); i++) {
            String Tstr = (String) Datalist.get(i);
            if (Tstr.equals("A") == false) {
                IS.add(Integer.parseInt(Tstr));
            }
            if (Tstr.equals("A")) {
                AIS.add(IS.clone());
                IS.clear();
            }
        }
        return AIS;
    }

    // %%%%%%%%%%%%%%%%%%%%%%% Procedure SquareGrouping() that will contain your
    // code:

    public static int SquareGrouping(int[] A, int n, int k) {
        /* 
         * Description of Main Ideas:
         * 
         * My main idea was to start with designing the recursive function and
         * then translating it to a dynamic programming solution. The function uses a
         * loop to iterate through the array A from index 1 to n - k. Within the loop,
         * the function calculates the sum of the elements of a group of size i,
         * starting from the first element of A. It then recursively calls the same
         * function with the remaining elements of A and a reduced value of k. The
         * results of the recursive call are added to the sum of the group and squared.
         * 
            public static int squareGrouping(int[] A, int n, int k) {
                int best = Integer.MAX_VALUE;
                for (int i = 1; i < n - k + 1; i++) {
                    int groupSum = 0;
                    for (int j = 0; j < i; j++) {
                        groupSum += A[j];
                    }
                    int restSum = squareGrouping(Arrays.copyOfRange(A, i, n), n-i, k - 1);
                    int totalSum = groupSum * groupSum + restSum;
                    if (totalSum < best) {
                        best = totalSum;
                    }
                }
                return best;
            }
         * 
         * I then optimized the recursive function by adding two arrays. The first 
         * called partialSum that would replace the groupSum variable and inner most
         * for loop that calculated the sum of the numbers in the group every time. 
         * The second called memo which would replace the recursion in the function
         * and store the minimum value of the sum of squares for each subproblem.
         * 
         * The function will start by initializing partialSum and memo. Iterating 
         * through the array A and calculates the partialSum of the elements up to 
         * the current index. It also calculates and stores the sum of squares of 
         * each sum in the memo array.
         * 
         * Then using the nested for loops, iterates through the possible number of 
         * splits (from 1 to k) and the end index of each split (from the current 
         * split number to the end of the array). It then calculates the minimum sum
         * of squares for each subarray, using the values stored in the memo and
         * partial sum array and stores the new value in memo. Eventully finding
         * minumum sqaure grouping in memo[n][k]
         * 
         */

        /*
         * 
         * Running Time Analysis:
         * 
         * T(n) = (k - 1) * (n - k - 1) * (n - k - 1 + k + 1)
         * T(n) = (k - 1) * (n - k - 1) * n
         * T(n) = (k - 1) * n^2 -kn - n
         * T(n) = kn^2 − kn − n^2 −n
         * 
         * O(n) = O(k*n^2)
         * 
         * The firat loop in the algorthim wil run O(n) but would be negligle compared
         * to the nested loops. For the rest of nested loops that calculates the optimal
         * sums: The outer loop iterates from nu mSplits=2 to numSplits=k, which gives
         * O(k-1). The middle loop iterates from splitEnd=numSplits to splitEnd=n,
         * which gives O(n-numSplits) that is equal to O(n-k - 1) as numSplits can vary
         * from 1 to k. The inner loop iterates from splitIndex=splitEnd-1 to
         * splitIndex=numSplits-1, giving O(n - k -1 + k + 1)
         * 
         * The time complexity of the algrothim increases linearly with k and
         * quadratically with n
         * 
         */

        int[] partialSum = new int[n];
        int[][] memo = new int[n][k];

        partialSum[0] = A[0];
        memo[0][0] = partialSum[0] * partialSum[0];

        for (int i = 1; i < n; i++) {
            partialSum[i] = partialSum[i - 1] + A[i];
            memo[i][0] = partialSum[i] * partialSum[i];
        }

        for (int numSplits = 1; numSplits < k; numSplits++) {
            for (int splitEnd = numSplits; splitEnd < n; splitEnd++) {
                memo[splitEnd][numSplits] = Integer.MAX_VALUE;
                for (int splitIndex = splitEnd - 1; splitIndex >= numSplits - 1; splitIndex--) {
                    int currentSum = memo[splitIndex][numSplits - 1]
                            + (partialSum[splitEnd] - partialSum[splitIndex])
                            * (partialSum[splitEnd] - partialSum[splitIndex]);
                    memo[splitEnd][numSplits] = Math.min(memo[splitEnd][numSplits], currentSum);
                }
            }
        }

        return memo[n - 1][k - 1];
    } // end of procedure SquareGrouping

    // %%%%%%%%%%%%%%%%%%%%%%% End of your code

    public static int Computation(ArrayList Instance, int opt) {
        int NGounp = 0;
        int size = 0;
        int Correct = 0;
        size = Instance.size();

        int[] A = new int[size - opt];
        // NGounp = Integer.parseInt((String)Instance.get(0));
        NGounp = (Integer) Instance.get(0);
        for (int i = opt; i < size; i++) {
            A[i - opt] = (Integer) Instance.get(i);
        }
        int Size = A.length;
        if (NGounp > Size) {
            return (-1);
        } else {
            int R = SquareGrouping(A, Size, NGounp);
            return (R);
        }
    }

    public static String Test;

    public static void main(String[] args) {
        Test = "-opt2";
        int opt = 2;
        String pathname = "data2.txt";
        if (args.length > 0) {
            Test = args[0];
            if (Test.equals("-opt1")) {
                opt = 1;
                pathname = "data1.txt";
            }
        }
        ArrayList Datalist = new ArrayList();
        Datalist = ReadData(pathname);
        ArrayList AIS = DataWash(Datalist);

        int Nins = AIS.size();
        int NGounp = 0;
        int size = 0;
        if (Test.equals("-opt1")) {
            for (int t = 0; t < Nins; t++) {
                int Correct = 0;
                int Result = 0;
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
            }
        } else {
            int wrong_no = 0;
            int Correct = 0;
            int Result = 0;
            ArrayList Wrong = new ArrayList();
            for (int t = 0; t < Nins; t++) {
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                Correct = (Integer) Instance.get(1);
                if (Correct != Result) {
                    Wrong.add(t + 1);
                    // Wrong.add(Instance);
                    wrong_no = wrong_no + 1;
                }
            }
            if (Wrong.size() > 0) {
                System.out.println("Index of wrong instance(s):");
            }
            for (int j = 0; j < Wrong.size(); j++) {
                System.out.print(Wrong.get(j));
                System.out.print(",");

                /*
                 * ArrayList Instance = (ArrayList)Wrong.get(j);
                 * for (int k = 0; k < Instance.size(); k++){
                 * System.out.println(Instance.get(k));
                 * }
                 */
            }
            System.out.println("");
            System.out.println("Percentage of correct answers:");
            System.out.println(((double) (Nins - wrong_no) / (double) Nins) * 100);

        }

    }
}
