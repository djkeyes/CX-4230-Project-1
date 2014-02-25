package evacSim.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SimulationController {

	private UpdateHandler myHandler = null;
	private Grid currentState;
	private Timer timer;

	public SimulationController(Grid startingGrid, int timestep) {
		currentState = startingGrid;

		timer = new Timer(timestep, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSimulation();
			}
		});
	}

	public void setHandler(UpdateHandler handler) {
		myHandler = handler;
	}

	/**
	 * Creates a simple simulation containing a small grid, a door, and a few
	 * obstacles, initialized with some default parameters.
	 * 
	 * @return
	 */
	public static SimulationController createSimpleSimulation() {
		Grid smallGrid = new Grid(5, 5);

		// A 2x3 block of obstacles in the corner, with a door in the center
		smallGrid.setCell(2, 0, new Obstacle());
		smallGrid.setCell(3, 0, new Obstacle());
		smallGrid.setCell(4, 0, new Obstacle());
		smallGrid.setCell(2, 1, new Obstacle());
		smallGrid.setCell(3, 1, new Door());
		smallGrid.setCell(4, 1, new Obstacle());
		
		smallGrid.initializeEmptyCells();

		int timestep = 100;
		return new SimulationController(smallGrid, timestep);
	}
	
	/**
	 * This method creates the simulation we're actually interested in doing.  It creates a world based around the Crum-Forster
	 * building and simulates a disaster immediately south of the building.
	 * 
	 * @return
	 */
	public static SimulationController createEvacSimulation() {
		Grid smallGrid = new Grid(300, 308);

		//smallGrid.setCell(200, 300, new Door());
		
		// Paving 5th St
		for(int c = 0; c <= 268; c++)
			for(int r = 17; r <=36; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Armistead
		for(int c = 41; c <= 268; c++)
			for(int r = 227; r <=246; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Spring St
		for(int c = 17; c <= 40; c++)
			for(int r = 0; r <= 299; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Peachtree St NW
		for(int c = 269; c <= 292; c++)
			for(int r = 0; r <= 299; r++)
				smallGrid.setCell(r, c, new Road());
		// Build the Management Building
		for(int c = 47; c <= 262; c++)
			for(int r = 43; r <= 220; r++)
				if(!(r <= 146 && c >= 192)) // Management courtyard
					smallGrid.setCell(r, c, new Obstacle());
		
		smallGrid.initializeEmptyCells();

		int timestep = 100;
		return new SimulationController(smallGrid, timestep);
	}

	public void start() {
		timer.start();
	}
	
	public void updateSimulation(){
		for(Cell c : currentState){
			c.calcUpdate();
		}
		for(Cell c : currentState){
			c.update();
		}
		
		if(myHandler != null)
			myHandler.onUpdate();
	}
	
	public Grid getGrid(){
		return currentState;
	}

}
