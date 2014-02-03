package evacSim.util;

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
	 * 
	 */
	public RNG(){
		// "Randomly" seed the generator.  This constructor will probably not be used.
		this((int)System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @param seed
	 */
	public RNG(int seed){
		this.seed = seed;
		a = 48271;
		m = (int)Math.pow(2, 31) - 1;
		q = 44488;
		r = 3399;
	}
	
	/**
	 * 
	 * @return A random number of the type double
	 */
	public double next(){
		return (double)nextI() / m;
	}
	
	/**
	 * 
	 * @return
	 */
	public int nextI(){
		int t = a * (seed % q) - r * (seed / q);
		if(t > 0) seed = t;
		else seed = t + m;
		
		return seed;
	}
}
