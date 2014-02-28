package evacSim.core;
/**
 * Determines the attributes to cross a vertically facing crosswalk
 * 
 * @author Daniel Keyes
 * 
 */ 
public class CrosswalkV extends Crosswalk {

	public CrosswalkV() {
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isWalkable(Cell walker) {
		if(!(walker instanceof Person)){
			//If the walker is not a person
			return false;
		}
		Person pWalker = (Person) walker;
		if(CrosswalkController.getInstance().isCrosswalkOpen()){ // H crosswalks not open
			return true;
		} else {
			//allow people already on the crosswalk to continue
			return pWalker.isOnCrosswalk();
		}
	}
}
