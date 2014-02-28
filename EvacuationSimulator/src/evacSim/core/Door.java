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
	
	static int numPeopleExited= 0;
	static final int MAX_BUILDING_OCCUPANCY = 1500;
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
		if(numPeopleExited >= MAX_BUILDING_OCCUPANCY){
			return;
		}
		
		// At peak hours in the student center, 154 people entered the door in 20 minutes = 1200 second = 3000 time steps
		// This means at any timestep, there's a 0.05133 chance of spawning a person. Call that 5%.
		RNG random = SimulationController.random;
		if(random.next() > 0.95){
			List<Cell> adjacent = findAdjacentCells();
			if(adjacent.size() > 0){
				Cell spawningCell = adjacent.get(random.nextI(0, adjacent.size()-1));
				// TODO make different people have different walking speeds
				spawningCell.setNextState(new Person());
				numPeopleExited++;
//				System.out.println(numPeopleExited);
				System.out.print(",");
			}
		}
	}
	@Override
	boolean isWalkable(Cell walker) {
		return false;
	}
}