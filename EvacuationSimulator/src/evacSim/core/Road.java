package evacSim.core;

public class Road extends Cell {

	@Override
	public void calcUpdate() {
		// TODO roads act as obstacles, except sometimes when there's a pedestrian crosswalk
	}

	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}

}
