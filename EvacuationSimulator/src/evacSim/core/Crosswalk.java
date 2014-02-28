package evacSim.core;

public abstract class Crosswalk extends Cell {

	@Override
	public void calcUpdate() {
		// TODO maybe this should update a boolean?
	}

	@Override
	boolean isWalkable(Cell walker) {
		if(!(walker instanceof Person)){
			return false;
		}
		Person pWalker = (Person) walker;
		if(CrosswalkController.getInstance().isCrosswalkOpen()){
			return true;
		} else {
			//allow people already on the crosswalk to continue
			return pWalker.isOnCrosswalk();
		}
	}

}
