package evacSim.core;

import evacSim.util.RNG;

/**
 * 
 * @author Joseph Mattingly
 * @author Daniel Keyes
 *
 */
public class Door extends Cell {
	RNG random = new RNG();
	
	@Override
	public void calcUpdate() {
		// TODO Doors should spawn people at adjacent available cells.
		
		// here's some default behavior that shouldn't work in general: just spawn to the right with Math.random()
		// 5% chance.
		if(random.next() > 0.95){
			// this doesn't do any bounds checking. it's just a simple example.
			getGrid().getCell(getRow(), getCol() + 1).setNextState(new Person());
		}
	}
}