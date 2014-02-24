import java.util.*;

/**
 * Test of the RNG (random number generator)
 * 
 * @author Rushad Heerjee
 * @version 1.0
 */
public class Test
{
    public static void main(String[] args)
    {
        System.out.println("Test #1: Showing the distribution of numbers between 1-10");
        
        int[] testArray = new int[10];
        int[] testArray2 = new int[10000000];
        double x, y;
        int n, o;

        RNG testRand = new RNG();

        // Initialize the array
        for (int i = 0; i < 10; i++)
        {
            testArray[i] = 0;
        }

        // Test the random number generator 1000000 times
        for (long i=0; i < 10000000; i++) 
        {
            // generate a new random number between 0 and 9
            x = testRand.next() * 10.0;
            n = (int) x;
            //Keep count of the number that appears
            testArray[n]++;
        }

        for (int i = 0; i < 10; i++)
        {
            System.out.println(i + ": " + testArray[i]);
        }
        
        System.out.println(" ");
        
        System.out.println("Test #2: Showing the randomness using chi-square distribution");
        
        for(int i = 0; i < 10000000; i++) 
        {
            y = testRand.next() * 10;
            o = (int) y;
            testArray2[i] = o;
        }
        
        double ans = chiTest(testArray2, 10);
        if(ans <= (10000000/10))
        {
            System.out.println("The Chi-Square Value is: " + ans);
        }
        else
        {
            System.out.println("The Chi-Square Value isn't valid");
        }
    }
    
    public static double chiTest(int[] randomArray, int upperBound)
    { 
		Map<Integer,Integer> freqMap = getFreq(randomArray);
        
        // Calculate chi-square
        double nSubR = (double)randomArray.length / upperBound;
        double chiSquare = 0;
        double val = 0;
        double temp = 0;
 
        for (int v : freqMap.values())
        {
            double f = v - nSubR;
            val = f * f;
            temp = val / nSubR;
            chiSquare += temp;
        }
        
        //The statistic should be within 2(r)^1/2 of upperBound
        return Math.abs(chiSquare - upperBound);

    }
 
    /**
     * @param - nums  an array of integers
     * @return - a Map, key being the number and value its frequency
     */
    private static Map<Integer, Integer> getFreq(int[] nums)
    {
        Map<Integer, Integer> freqs = new HashMap<Integer, Integer>();
 
        for (int x : nums)
        {
            if (freqs.containsKey(x))
            {
                freqs.put(x, freqs.get(x) + 1);
            }
            else
            {
                freqs.put(x, 1);
            }   
        }
 
        return freqs;
    }
}
