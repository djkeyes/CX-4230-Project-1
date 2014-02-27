package evacSim.core;

import java.util.LinkedList;
import java.util.List;

public class Person extends Cell {

	private int count;

	public Person() {
		this(10);
	}

	/**
	 * @param walkingTime
	 *            The amount of time it takes this Person to take one step.
	 */
	private Person(int walkingTime) {
		this.count = walkingTime;
	}

	@Override
	public void calcUpdate() {
		// Logic: People will attempt to move north. If the square above them is empty right now and has not already been claimed for
		// the next timestep, they will move there. If it's occupied, we randomly choose between going right, going left, or standing
		// still, taking into account which options are available.
		// Notably, if people are densely packed, this means there will always be a gap when one person goes north and no one has yet
		// filled the space. But that seems like a reasonable description of crowd dynamics.
		// Also, if two people want to claim a space, the upper right one will always get the first pick. That might bias the results.

		if (--count <= 0) {
			// check to make sure no one has already set our next state
			// walk north, until we fall off the screen

			// if the cell above us the top of the screen, don't do anything
			if (getRow() > 0) {
				Cell nextCell = null;
				// if the cell above is an EmptyCell and not yet assigned
				Cell north = getGrid().getCell(getRow() - 1, getCol());
				if (north instanceof EmptyCell && north.getNextState() == null) {
					nextCell = north;
				} else {
					List<Cell> availableLocations = new LinkedList<Cell>();
					// choose randomly between left, right, and wait
					if (getNextState() == null)
						availableLocations.add(this);
					if(getGrid().inBounds(getRow(), getCol()-1)){
						Cell left = getGrid().getCell(getRow(), getCol()-1);
						if (left instanceof EmptyCell && left.getNextState() == null)
							availableLocations.add(left);
					}
					if(getGrid().inBounds(getRow(), getCol()+1)){
						Cell right = getGrid().getCell(getRow(), getCol()+1);
						if (right  instanceof EmptyCell && right.getNextState() == null)
							availableLocations.add(right);
					}
					int index = SimulationController.random.nextI(0, availableLocations.size()-1);
					nextCell = availableLocations.get(index);
					
				}
				nextCell.setNextState(new Person(count));

				if (getNextState() == null)
					setNextState(new EmptyCell());
			}
			
		}

	}

	@Override
	boolean isWalkable() {
		return false;
	}

}
