package evacSim.core;

import java.util.Iterator;
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
	
	public Grid(){
		this(100,100);
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
	public Cell getCell(int row, int col){
		return cells[row][col];
	}

	@Override
	public Iterator<Cell> iterator() {
		return new Iterator<Cell>(){
			private int nextRow=0, nextCol=0;

			@Override
			public boolean hasNext() {
				return nextRow < nRows;
			}

			@Override
			public Cell next() {
				if(!hasNext()){
					throw new NoSuchElementException();
				}
				Cell result = cells[nextRow][nextCol];
				nextCol++;
				if(nextCol >= nCols){
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
		for(int i=0; i < cells.length; i++){
			for(int j=0; j < cells[i].length; j++){
				if(cells[i][j] == null){
					setCell(i, j, new EmptyCell());
				}
			}
		}
	}
	
	public int getRows(){
		return nRows;
	}
	public int getCols(){
		return nCols;
	}
}