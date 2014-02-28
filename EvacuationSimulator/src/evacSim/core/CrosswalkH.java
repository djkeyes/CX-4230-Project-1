package evacSim.core;

public class CrosswalkH extends Crosswalk {

	@Override
	boolean isWalkable(Cell walker) {
		if(!(walker instanceof Person)){
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

	@Override
	public Cell copy() {
		return new CrosswalkH();
	}
}
