package evacSim.core;
/**
 * Detemines if the crosswalk is walkable (ie. pedestrians can walk across without fear of oncoming traffic)
 * 
 * @author Daniel Keyes
 */
public abstract class Crosswalk extends Cell {

	@Override
	public void calcUpdate() {
		// TODO maybe this should update a boolean?
	}

	@Override
	boolean isWalkable(Cell walker) {
		if(!(walker instanceof Person)){
			//If the walker is NOT a person, return false since there is an error
			return false;
		}
		Person pWalker = (Person) walker;
		if(CrosswalkController.getInstance().isCrosswalkOpen()){
			//If the crosswalk is open for people to cross
			return true;
		} else {
			//allow people already on the crosswalk to continue
			return pWalker.isOnCrosswalk();
		}
	}

}
