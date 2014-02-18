package evacSim.core;

/**
 * 
 * @author Joseph Mattingly
 *
 */
public class Door extends Cell {

	@Override
	public void calcUpdate() {
		// TODO Doors should spawn people at adjacent available cells.
		
		// here's some default behavior that shouldn't work in general: just spawn to the right with Math.random()
		// 5% chance.
		if(Math.random() > 0.95){
			// this doesn't do any bounds checking. it's just a simple example.
			getGrid().getCell(getRow(), getCol() + 1).setNextState(new Person());
		}
	}
}