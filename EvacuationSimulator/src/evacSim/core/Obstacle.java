package evacSim.core;

/**
 * An obstacle is a cell that a Person cannot walk through.  It forces the Person to divert its motion around the obstacle.
 * 
 * @author Daniel Keyes
 */
public class Obstacle extends Cell {

	@Override
	public void calcUpdate() {
		//TODO obstacles will always (i think?) stay obstacles, so this method can stay empty
	}

	/**
	 * A person cannot walk through an obstacle.
	 */
	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}

	@Override
	public Cell copy() {
		return new Obstacle();
	}
}
