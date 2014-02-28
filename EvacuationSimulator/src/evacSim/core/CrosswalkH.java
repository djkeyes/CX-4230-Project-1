package evacSim.core;
/**
 * Determines the attributes to cross a horizontally facing crosswalk
 * 
 * @author Daniel Keyes
 * 
 */ 
public class CrosswalkH extends Crosswalk {

	public CrosswalkH() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Determine if the horizontal crosswalk is walkable 
	 */
	@Override
	boolean isWalkable(Cell walker) {
		if(!(walker instanceof Person)){
			//If the walker is not a person
			return false;
		}
		Person pWalker = (Person) walker;
		if(!CrosswalkController.getInstance().isCrosswalkOpen()){ // V crosswalks not open
			return true;
		} else {
			//allow people already on the crosswalk to continue
			return pWalker.isOnCrosswalk();
		}
	}
}
