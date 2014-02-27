package evacSim.core;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Daniel Keyes
 *
 */
public class Person extends Cell {

	private final int walkingTime;
	private int count;
	private boolean onCrosswalk;

	public Person() {
		// default: walk at 1 cell per second
		this(1);
		onCrosswalk = false;
	}

	/**
	 * 
	 * @param walkingTime The amount of time it takes this Person to take one step.
	 */
	private Person(int walkingTime) {
		this.count = this.walkingTime = walkingTime;
	}

	@Override
	public void calcUpdate() {
		// Logic: Grid stores an array of distances to the goal. A person should consider the neighboring and current square (5 total)
		// and sort by distance, and the continue to the one that has the shortest distance. In the case of a tie, choose randomly.
		// Notably, if people are densely packed, this means there will always be a gap when one person goes north and no one has yet
		// filled the space. But that seems like a reasonable description of crowd dynamics.
		// Also, if two people want to claim a space, the upper right one will always get the first pick. That might bias the results.

		if (--count <= 0) {

			if(getRow() == 0){
				System.out.println(SimulationController.simTime + ":  A person made it out alive!");
			} else{ // (getRow() > 0)
				// if the cell above is walkable and not yet assigned
				int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
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
				for(int i=possibleLocations.size()-1; i >= 0; i--){
					Cell cur = possibleLocations.get(i);
					if(getGrid().pedestrianDistances[cur.getRow()][cur.getCol()] > minDistance){
						possibleLocations.remove(i);
					}
				}
				
				// now we are left only with optimal choices. pick one.
				int index = SimulationController.random.nextI(0, possibleLocations.size()-1);
				Cell nextCell = possibleLocations.get(index);
				Person nextState = new Person(walkingTime);
				nextState.onCrosswalk = nextCell instanceof Crosswalk || (nextCell==this && this.onCrosswalk);
				nextCell.setNextState(nextState);
			}

			if (getNextState() == null) {
				if (onCrosswalk) {
					setNextState(new Crosswalk());
				} else {
					setNextState(new EmptyCell());
				}
			}

		}

	}

	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}

	boolean isOnCrosswalk() {
		return onCrosswalk;
	}

}
