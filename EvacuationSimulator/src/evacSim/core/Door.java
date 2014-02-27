package evacSim.core;

import java.util.LinkedList;
import java.util.List;

import evacSim.util.RNG;

/**
 * 
 * @author Joseph Mattingly
 * @author Daniel Keyes
 *
 */
public class Door extends Cell {
	
	/**
	 * returns a list of adjacent empty cells (which a person could walk into)
	 */
	private List<Cell> findAdjacentCells(){
		List<Cell> result = new LinkedList<Cell>();
		if(getGrid().inBounds(getRow(), getCol()-1)){
			Cell north = getGrid().getCell(getRow(), getCol()-1);
			if(north instanceof EmptyCell && north.getNextState() == null){
				result.add(north);
			}
		}
		if(getGrid().inBounds(getRow(), getCol()+1)){
			Cell south = getGrid().getCell(getRow(), getCol()+1);
			if(south instanceof EmptyCell && south.getNextState() == null){
				result.add(south);
			}
		}
		if(getGrid().inBounds(getRow()+1, getCol())){
			Cell east = getGrid().getCell(getRow()+1, getCol());
			if(east instanceof EmptyCell && east.getNextState() == null){
				result.add(east);
			}
		}
		if(getGrid().inBounds(getRow()-1, getCol())){
			Cell west = getGrid().getCell(getRow()-1, getCol());
			if(west instanceof EmptyCell && west.getNextState() == null){
				result.add(west);
			}
		}
		
		return result;
	}
	@Override
	public void calcUpdate() {
		
		// here's some default behavior that shouldn't work in general: just spawn to the right with Math.random()
		// 5% chance.
		RNG random = SimulationController.random;
		// TODO use something other than a 5% uniform distribution
		if(random.next() > 0.95){
			List<Cell> adjacent = findAdjacentCells();
			if(adjacent.size() > 0){
				Cell spawningCell = adjacent.get(random.nextI(0, adjacent.size()-1));
				// TODO make different people have different walking speeds
				spawningCell.setNextState(new Person());
			}
		}
	}
	@Override
	boolean isWalkable() {
		return false;
	}
}