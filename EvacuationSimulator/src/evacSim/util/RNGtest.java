
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
        int[] testArray = new int[10];
        double x;
        int n;

        RNG testRand = new RNG();

        // Initialize the array 
        for (int i = 0; i < 10; i++)
        {
            testArray[i] = 0;
        }

        // Test the random number generator a whole lot
        for (long i=0; i < 1000000; i++) 
        {
            // generate a new random number between 0 and 9
            x = testRand.next() * 10.0;
            n = (int) x;
            //count the digits in the random number
            testArray[n]++;
        }

        // Print the results
        for (int i = 0; i < 10; i++)
        {
            System.out.println(i + ": " + testArray[i]);
        }
    }
}
