package evacSim.util;

/**
 * Test of the RNG (random number generator)
 * 
 * @author Rushad Heerjee
 * @version 1.0
 */
public class RNGtest {
    public static void main(String[] args){
    	RNG rand = new RNG();
    	
    	int failCount = 0;
    	
    	for(int j = 0; j < 1000; j++){
	    	int[] bins = new int[1000];	//Create 1000 bins based of the sqrt(n) rule
	    	for(int i = 0; i < 1000000; i++){
	    		int sample = rand.nextI();
	    		int bin = (int)(sample / 2147483.647);
	    		bins[bin]++; //Categorize number frequency into bins
	    	}
	    	double chiSq = 0;
	    	for(int i = 0; i < 1000; i++)
	    		chiSq += Math.pow((bins[i] - 1000), 2) / 1000.0; //Calculate the Chi-Squared value
	    	String status;
	    	if(chiSq <= 1073.64)
	    		status = "Passes Chi-Square test"; //If it's optimal (<= 1073.64)
	    	else{
	    		status = "Fails Chi-Square test"; //If it's not optimal (> 1073.64)
	    		failCount++; //Increase the fail count
	    	}
	    	// We should avoid printing this if we want to finish in reasonable time...
	    	//System.out.println(chiSq + ":  " + status);
    	}
    	System.out.println(failCount / 1000.0 * 100 + "%"); //Print out the percentage of failure (should be below 5%)
    }
}
