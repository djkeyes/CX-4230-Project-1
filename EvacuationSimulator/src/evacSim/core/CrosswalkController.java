package evacSim.core;

/**
 * Singleton class to handle and coordinate crosswalk mechanics over multiple sets of crosswalk tiles.
 * 
 */
public class CrosswalkController {
	
	private CrosswalkController(){
		count = 0;
		period = 1;
		isCrosswalkOpen = false;
	}
	
	private static CrosswalkController controller;
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
			count = 0;
			isCrosswalkOpen = !isCrosswalkOpen;
		}
	}
	public boolean isCrosswalkOpen(){
		return isCrosswalkOpen;
	}
	
	public void reset(){
		count = 0;
		isCrosswalkOpen = false;
	}
	
}
