package evacSim.core;

public class CrosswalkV extends Crosswalk {

	public CrosswalkV() {
		// TODO Auto-generated constructor stub
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
