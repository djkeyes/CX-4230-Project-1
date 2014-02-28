package evacSim.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A simple rectangular grid implementation.
 * 
 * It also implements Iterable so we can use it in extended for loops. :D
 * 
 * @author Joseph Mattingly
 * @author Daniel Keyes
 */
public class Grid implements Iterable<Cell> {

	// grids are row-major indexed. cells[i][j] refers to row i, column j.
	private Cell[][] cells;
	private int nRows, nCols;

	// distance from goal, for use by pedestrians
	int[][] pedestrianDistances;

	public Grid() {
		this(100, 100);
	}

	public Grid(int nRows, int nCols) {
		this.nRows = nRows;
		this.nCols = nCols;
		cells = new Cell[nRows][nCols];
	}

	public void setCell(int row, int col, Cell newCell) {
		cells[row][col] = newCell;
		newCell.setContainingGrid(this, row, col);
	}

	public Cell getCell(int row, int col) {
		return cells[row][col];
	}

	@Override
	public Iterator<Cell> iterator() {
		return new Iterator<Cell>() {
			private int nextRow = 0, nextCol = 0;

			@Override
			public boolean hasNext() {
				return nextRow < nRows;
			}

			@Override
			public Cell next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Cell result = cells[nextRow][nextCol];
				nextCol++;
				if (nextCol >= nCols) {
					nextCol = 0;
					nextRow++;
				}
				return result;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	/**
	 * Fills any null cells with a new EmptyCell
	 */
	public void initializeEmptyCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j] == null) {
					setCell(i, j, new EmptyCell());
				}
			}
		}
	}

	public int getRows() {
		return nRows;
	}

	public int getCols() {
		return nCols;
	}

	public boolean inBounds(int row, int col) {
		return row >= 0 && col >= 0 && row < nRows && col < nCols;
	}

	/**
	 * To initialize any datastructers needed for the simulation, after the grid has been specified
	 */
	void initialize() {
		if (pedestrianDistances != null) {
			return;
		}
		// Compute an array of distances for People to use to find a destination
		// This code is quick-n-dirty
		int[][] distance = new int[nRows][nCols];
		LinkedList<int[]> goals = new LinkedList<int[]>();
		// scan along top row and find target sidewalks
		for (int i = 0; i < nCols; i++) {
			Cell cur = getCell(0, i);
			if (cur instanceof EmptyCell) {
				goals.add(new int[] { 0, i });
			}
		}

		for (int[] arr : distance) {
			Arrays.fill(arr, -1);
		}
		for (int[] goal : goals) {
			distance[goal[0]][goal[1]] = 0;
		}

		int[] nextRow = { 1, 0, -1, 0, 1, 1, -1, -1 };
		int[] nextCol = { 0, 1, 0, -1, 1, -1, 1, -1 };
		LinkedList<int[]> queue = new LinkedList<int[]>();
		queue.addAll(goals);
		while (!queue.isEmpty()) {
			int[] cur = queue.removeFirst();
			for (int i = 0; i < nextRow.length; i++) {
				int[] next = new int[] { cur[0] + nextRow[i], cur[1] + nextCol[i] };
				if (inBounds(next[0], next[1])) {
					Cell cell = getCell(next[0], next[1]);
					if (cell instanceof EmptyCell || cell instanceof Crosswalk) {
						if (distance[next[0]][next[1]] == -1) {
							distance[next[0]][next[1]] = distance[cur[0]][cur[1]] + 1;
							queue.add(next);
						}
					}
				}
			}
		}

		pedestrianDistances = distance;
	}

	public Grid copyAndReassign() {
		Grid result = new Grid(this.nRows, this.nCols);
		result.pedestrianDistances = this.pedestrianDistances;
		for(int i=0; i < nRows; i++){
			for(int j=0; j < nCols; j++){
				result.cells[i][j] = cells[i][j];
				result.cells[i][j].setContainingGrid(result, i, j);
			}
		}
		return result;
	}

}