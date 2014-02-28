package evacSim.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Persons are the key players in the evacuation simulation.  Their motion is critical to determining how to place doors on a
 * building.
 * 
 * @author Daniel Keyes
 * @author Joseph Mattingly
 */
public class Person extends Cell {

	private final int walkingTime;
	private int count;
	private boolean onCrosswalkV;
	private boolean onCrosswalkH;
	

	static int numPeopleSafe= 0;
	static List<Integer> peopleSafeOverTime = new LinkedList<Integer>();

	/**
	 * Not to be confused with a public person.  Most of the people in this simulation are hard-working private citizens like
	 * you and me. But actually, this just creates a generic person who can walk at a predefined 1.5 m/s.
	 */
	public Person() {
		// default: walk at 1 cell per second
		this(1);
		onCrosswalkV = false;
		onCrosswalkH = false;
	}

	/**
	 * Create a person with a certain walking speed.
	 * 
	 * @param walkingTime The amount of time it takes this Person to take one step.
	 */
	private Person(int walkingTime) {
		this.count = this.walkingTime = walkingTime;
	}

	/**
	 * This method contains all the transition rules for how people move.
	 */
	@Override
	public void calcUpdate() {
		// Logic: Grid stores an array of distances to the goal. A person should consider the neighboring and current square (5 total)
		// and sort by distance, and the continue to the one that has the shortest distance. In the case of a tie, choose randomly.
		// Notably, if people are densely packed, this means there will always be a gap when one person goes north and no one has yet
		// filled the space. But that seems like a reasonable description of crowd dynamics.
		// Also, if two people want to claim a space, the upper right one will always get the first pick. That might bias the results.

		if (--count <= 0) {

			if (getRow() == 0) {
				numPeopleSafe++;
//				System.out.println("A person made it out alive!");
				System.out.print(".");
			} else { // (getRow() > 0)
				// if the cell above is walkable and not yet assigned
				int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 } };
				List<Cell> possibleLocations = new LinkedList<Cell>();
				possibleLocations.add(this);
				int curDistance = getGrid().pedestrianDistances[getRow()][getCol()];
				int minDistance = curDistance;
				for (int i = 0; i < directions.length; i++) {
					int[] trans = directions[i];
					int nextRow = getRow() + trans[0];
					int nextCol = getCol() + trans[1];
					if (getGrid().inBounds(nextRow, nextCol)) {
						Cell adj = getGrid().getCell(nextRow, nextCol);
						if (adj.isWalkable(this) && adj.getNextState() == null) {
							int adjDistance = getGrid().pedestrianDistances[nextRow][nextCol];
							// if this cell no worse than the current one, consider going there
							if (adjDistance <= curDistance) {
								possibleLocations.add(adj);
								minDistance = Math.min(minDistance, adjDistance);
							}
						}
					}
				}

				// of all the next possible locations, choose the best one
				// if there are ties, choose randomly between them

				// first, remove sub-optimal options
				// TODO: maybe we should randomly choose among the sub-optimal options? the current behavior looks pretty forced.
				for (int i = possibleLocations.size() - 1; i >= 0; i--) {
					Cell cur = possibleLocations.get(i);
					if (getGrid().pedestrianDistances[cur.getRow()][cur.getCol()] > minDistance) {
						possibleLocations.remove(i);
					}
				}

				// now we are left only with optimal choices. pick one.
				int index = SimulationController.random.nextI(0, possibleLocations.size() - 1);
				Cell nextCell = possibleLocations.get(index);
				Person nextState = new Person(walkingTime);
				nextState.onCrosswalkV = nextCell instanceof CrosswalkV || (nextCell == this && this.onCrosswalkV);
				nextState.onCrosswalkH = nextCell instanceof CrosswalkH || (nextCell == this && this.onCrosswalkH);
				nextCell.setNextState(nextState);
			}

			if (getNextState() == null) {
				if (onCrosswalkV) {
					setNextState(new CrosswalkV());
				} else if (onCrosswalkH) {
					setNextState(new CrosswalkH());
				} else {
					setNextState(new EmptyCell());
				}
			}

		}

	}

	/**
	 * Prevent people from walking into other people.  After all, that's just plain rude.
	 */
	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}

	/**
	 * Determine if the Person is currently in a crosswalk.  This is useful so people don't get stranded in oncoming traffic.
	 * 
	 * @return True if the Person is in the middle of any type of crosswalk.  False otherwise.
	 */
	boolean isOnCrosswalk() {
		return onCrosswalkV || onCrosswalkH;
	}
	
	/**
	 * Determine when all people in the simulation have reached safety.  It's good when this is the case.
	 * 
	 * @return True when all Persons have successfully evacuated.  False otherwise.
	 */
	public static boolean isEveryoneSafe(){
		return numPeopleSafe == Door.MAX_BUILDING_OCCUPANCY;
	}

	@Override
	public Cell copy() {
		throw new UnsupportedOperationException();
	}

}
