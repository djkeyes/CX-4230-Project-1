package evacSim.core;

public class EmptyCell extends Cell {

	@Override
	public void calcUpdate() {}

	@Override
	boolean isWalkable() {
		return true;
	}

}
