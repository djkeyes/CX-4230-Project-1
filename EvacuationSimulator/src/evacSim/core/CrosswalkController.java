package evacSim.core;

/**
 * Singleton class to handle and coordinate crosswalk mechanics over multiple sets of crosswalk tiles.
 * 
 * @author Daniel Keyes
 * 
 */
public class CrosswalkController {
	
	private CrosswalkController(){
		count = 0;
		period = 1;
		isCrosswalkOpen = false;
	}
	
	private static CrosswalkController controller;
	/**
	 * Create a new instance of the Crosswalk controller
	 */
	public static CrosswalkController getInstance(){
		if(controller == null){
			controller = new CrosswalkController();
		}
		return controller;
	}
	
	private int count;
	private int period;
	private boolean isCrosswalkOpen;
	
	public void setPeriod(int period){
		this.period = period;
	}
	
	public void step(){
		count++;
		if(count == period){
		//The crosswalk is not open so set the count to 0 and the boolean to false
			count = 0;
			isCrosswalkOpen = !isCrosswalkOpen;
		}
	}
	
	/**
	 * Determine if the crosswalk is open to cross
	 */
	public boolean isCrosswalkOpen(){
		return isCrosswalkOpen;
	}
	
	/**
	 * Resets the count to 0 and the crosswalk open boolean to false
	 */ 
	public void reset(){
		count = 0;
		isCrosswalkOpen = false;
	}
	
}
