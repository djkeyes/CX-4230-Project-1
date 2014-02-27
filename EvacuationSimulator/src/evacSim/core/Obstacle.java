package evacSim.core;


public class Obstacle extends Cell {

	@Override
	public void calcUpdate() {
		//TODO obstacles will always (i think?) stay obstacles, so this method can stay empty
	}

	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}
}
