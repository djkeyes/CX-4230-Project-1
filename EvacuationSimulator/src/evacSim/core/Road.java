package evacSim.core;

/**
 * Create a cell object for roads.  This works the same as any other obstacle, but is easier to manage conceptually and visually in
 * the actual simulation.
 * 
 * @author Daniel Keyes
 */
public class Road extends Cell {
	@Override
	public void calcUpdate() {
		// TODO roads act as obstacles, except sometimes when there's a pedestrian crosswalk
	}

	/**
	 * Roads cannot be walked on by Persons.
	 */
	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}

	@Override
	public Cell copy() {
		return new Road();
	}

}
