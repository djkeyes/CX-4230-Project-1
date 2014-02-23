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
        double x;
        int n;

        RNG testRand = new RNG();

        // Initialize the array
        for (int i = 0; i < 10; i++)
        {
            testArray[i] = 0;
        }

        // Test teh random number generator 1000000 times
        for (long i=0; i < 1000000; i++) 
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
        
        int[] testArray2 = new int[1000000];
        int max = 0;
        
        // Fill the array with random numbers
        for (int i = 0; i < 1000000; i++)
        {
            testArray2[i] = testRand.nextI();
            while (testArray2[i] > max)
            {
                max = testArray2[i];
            }
        }
                
        if(isRandom(testArray2, max) == true)
        {
            System.out.println("According to the chi-square test, the nextI() method produces random numbers");
        }
        else
        {
            System.out.println("According to the chi-square test, the nextI() method DOESN'T produce random numbers");
        }
    }
    
    public static boolean isRandom(int[] randomArray, int upperBound)
    {
        //This is valid if N is greater than about 10 * upperBound
        if (randomArray.length <= 10 * upperBound)
        {
            return false;
        }
        //Get frequency of randoms
        Map <Integer, Integer> freqMap = getFreq(randomArray);
 
        // Calculate chi-square
        double nSubR = (double)randomArray.length / upperBound;
        double chiSquare = 0;
 
        for (int v : freqMap.values())
        {
            double f = v - nSubR;
            chiSquare += f * f;
        }
        chiSquare /= nSubR;
        
        //For testing purposes
        System.out.println(Math.abs(chiSquare - upperBound) + " <= " + Math.sqrt(upperBound));
        
        //The statistic should be within 2(r)^1/2 of upperBound
        return Math.abs(chiSquare - upperBound) <= 2 * Math.sqrt(upperBound);

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
