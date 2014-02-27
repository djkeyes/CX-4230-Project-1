package evacSim.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.Timer;

import evacSim.util.RNG;

public class SimulationController {

	private UpdateHandler myHandler = null;
	private Grid currentState;
	private Timer timer;
	private boolean useRealtime;

	private static final int DEFAULT_SEED = 1234;

	// global RNG instance
	// TODO: make this a singleton member variable or something that uses good programming practice
	public static final RNG random = new RNG(DEFAULT_SEED);

	public SimulationController(Grid startingGrid, int timestep, int crosswalkPeriod, boolean useRealtime) {
		currentState = startingGrid;

		CrosswalkController.getInstance().setPeriod(crosswalkPeriod);

		this.useRealtime = useRealtime;
		timer = new Timer(timestep, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSimulation();
			}
		});
	}

	public SimulationController(Grid smallGrid, int timestep) {
		this(smallGrid, timestep, 75, true);
	}

	public void setHandler(UpdateHandler handler) {
		myHandler = handler;
	}

	/**
	 * Creates a simple simulation containing a small grid, a door, and a few obstacles, initialized with some default parameters.
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
	 * This method creates the simulation we're actually interested in doing. It creates a world based around the Crum-Forster building
	 * and simulates a disaster immediately south of the building.
	 * 
	 * @return
	 */
	public static SimulationController createEvacSimulation() {
		Grid smallGrid = new Grid(300, 308);

		// Paving 5th St NW
		for (int c = 0; c <= 268; c++)
			for (int r = 17; r <= 36; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Armstead Pl
		for (int c = 41; c <= 268; c++)
			for (int r = 227; r <= 246; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Spring St
		for (int c = 17; c <= 40; c++)
			for (int r = 0; r <= 299; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving Peachtree St NW
		for (int c = 269; c <= 292; c++)
			for (int r = 0; r <= 299; r++)
				smallGrid.setCell(r, c, new Road());
		// Paving 5th St NE
		for (int c = 293; c <= 307; c++)
			for (int r = 125; r <= 144; r++)
				smallGrid.setCell(r, c, new Road());
		// Build the Management Building
		for (int c = 47; c <= 262; c++)
			for (int r = 43; r <= 220; r++)
				if (!(r <= 146 && c >= 192)) // Management courtyard
					smallGrid.setCell(r, c, new Obstacle());
		// Build the GT Hotel
		for (int c = 0; c <= 10; c++)
			for (int r = 43; r <= 299; r++)
				smallGrid.setCell(r, c, new Obstacle());
		// Build the Crum and Forster Building
		for (int c = 61; c <= 152; c++)
			for (int r = 252; r <= 299; r++)
				smallGrid.setCell(r, c, new Obstacle());
		// Various sundry buildings
		for (int c = 299; c <= 307; c++)
			for (int r = 151; r <= 299; r++)
				smallGrid.setCell(r, c, new Obstacle());
		for (int c = 299; c <= 307; c++)
			for (int r = 0; r <= 118; r++)
				smallGrid.setCell(r, c, new Obstacle());
		for (int c = 47; c <= 262; c++)
			for (int r = 0; r <= 10; r++)
				smallGrid.setCell(r, c, new Obstacle());
		for (int c = 0; c <= 10; c++)
			for (int r = 0; r <= 10; r++)
				smallGrid.setCell(r, c, new Obstacle());
		// Painting two crosswalks
		// TODO: use the correct location
		for (int c = 45; c <= 55; c++)
			for (int r = 17; r <= 36; r++)
				smallGrid.setCell(r, c, new Crosswalk());
		for (int c = 254; c <= 264; c++)
			for (int r = 17; r <= 36; r++)
				smallGrid.setCell(r, c, new Crosswalk());
		for (int c = 45; c <= 55; c++)
			for (int r = 227; r <= 246; r++)
				smallGrid.setCell(r, c, new EmptyCell());
		for (int c = 254; c <= 264; c++)
			for (int r = 227; r <= 246; r++)
				smallGrid.setCell(r, c, new EmptyCell());

		// TESTING
		smallGrid.setCell(252, 100, new Door());
		smallGrid.setCell(252, 101, new Door());
		smallGrid.setCell(282, 61, new Door());
		smallGrid.setCell(282, 152, new Door());

		smallGrid.initializeEmptyCells();

		int crosswalkInterval = 200;
		int timestep = 10;
		return new SimulationController(smallGrid, timestep, crosswalkInterval, true);
	}

	public void start() {
		currentState.initialize();

		if (useRealtime) {
			timer.start();
		} else {
			while (true) {
				updateSimulation();
			}
		}
	}

	public void updateSimulation() {
		CrosswalkController.getInstance().step();

		for (Cell c : currentState) {
			c.calcUpdate();
		}
		for (Cell c : currentState) {
			c.update();
		}

		if (myHandler != null)
			myHandler.onUpdate();
	}

	public Grid getGrid() {
		return currentState;
	}

}
