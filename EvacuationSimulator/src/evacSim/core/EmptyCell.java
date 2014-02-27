package evacSim.core;

public class EmptyCell extends Cell {

	@Override
	public void calcUpdate() {}

	@Override
	boolean isWalkable(Cell walker) {
		return true;
	}

}
