package evacSim.util;

/**
 * 
 * @author Joseph Mattingly
 *
 */
public class RNG {
	/**
	 * Random Number Generator
	 */
	
	private int seed;
	private int a;
	private int m;
	private int q;
	private int r;
	
	/**
	 * Create a new instance of a random number generator with a random seed.
	 */
	public RNG(){
		// "Randomly" seed the generator.  This constructor will probably not be used.
		this((int)System.currentTimeMillis());
	}
	
	/**
	 * Create a new instance of a random number generator with a specified seed.
	 * @param seed The seed for the random number generator can be anything within the set of positive integers.
	 */
	public RNG(int seed){
		this.seed = seed;
		// Use the values for Lehmer's algorithm suggested by Lemmis and Park
		a = 48271;
		m = 2147483647; // A prime number equal to 2^31 - 1
		q = 44488;
		r = 3399;
	}
	
	/**
	 * Generate a random number of type double having values between 0 and 1, exclusive.
	 * @return A random number of the type double
	 */
	public double next(){
		return (double)nextI() / m;
	}
	
	/**
	 * Generate a random number of type integer having values between 0 and 2^31, exclusive.
	 * @return A random number of the type integer
	 */
	public int nextI(){
		int t = a * (seed % q) - r * (seed / q);
		if(t > 0) seed = t;
		else seed = t + m;
		
		return seed;
	}
	
	/**
	 * Generate a random number of type integer having values between min and max, inclusive
	 * @param min The minimum value of the range over which random numbers are desired
	 * @param max The maximum value of the range over which random numbers are desired
	 * @return A random number of the type integer 
	 */
	public int nextI(int min, int max){
		return min + (int)Math.floor(next() * (max + 1 - min));
	}
}