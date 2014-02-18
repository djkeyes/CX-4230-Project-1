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
