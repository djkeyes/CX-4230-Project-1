package evacSim.core;

/**
 * Core representation of cells in a Grid. Specific subclasses of Cell should implement their own CA behavior. In particular, they
 * should override calcUpdate(). If their state should change, they should specify the state for the next timestep using
 * setNextState(Cell).
 * 
 * Individual Cells should be lightweight in memory and generally immutable, and they should avoid storing reference to cells at
 * previous times. At every timestep, we create a new set of cells to use, and we throw away all the old ones, so we don't want
 * overwhelm the garbage collector or inadvertently use up all our memory. TODO add some cleverly placed 'final' statements to prevent
 * us from accidently doing those things
 * 
 * @author Joseph Mattingly
 * @author Daniel Keyes
 * 
 */
abstract public class Cell {

	private Cell nextState = null;
	private Grid myGrid;
	private int myRow, myCol;

	// package access - only Grid should call this when storing a cell
	void setContainingGrid(Grid grid, int myRow, int myCol) {
		myGrid = grid;
		this.myRow = myRow;
		this.myCol = myCol;
	}

	public final void update() {
		if (nextState != null) {
			// updates the grid with the new cell for this position
			// note that shortly after this method call, the garbage collector will throw us away. !
			myGrid.setCell(myRow, myCol, nextState);
		}
	}

	abstract public void calcUpdate();
	
	abstract public Cell copy();

	/**
	 * Returns whether the walker cell has permission to walk onto this cell. For example, a person might not be allowed to cross a
	 * closed crosswalk. However, if a person is already on the crosswalk, they might have permission to do so.
	 * 
	 * @param walker
	 *            the cell that would like to walk on this cell
	 * @return true if a person can walk on this cell
	 */
	abstract boolean isWalkable(Cell walker);

	protected final void setNextState(Cell nextState) {
		this.nextState = nextState;
	}

	protected final Cell getNextState() {
		return nextState;
	}

	protected final Grid getGrid() {
		return myGrid;
	}

	public final int getRow() {
		return myRow;
	}

	public final int getCol() {
		return myCol;
	}

}