package evacSim.visualization;

import evacSim.core.Grid;
import evacSim.core.SimulationController;
import evacSim.core.Statistics;

/**
 * This runs the simulation without visualization. You will find that this runs much faster than the visualization, because it doesn't
 * have to wait for the timer to tick in real time.
 * 
 * @author Daniel Keyes
 */
public class SimulationRunner {
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			// rerun the same simulation 20 times on the same grid
			Grid curGrid = null;
			for(int j=0; j < 20; j++){
				SimulationController sim;
				if(curGrid == null){
					sim = SimulationController.createEvacSimulation(false, true);
					curGrid = sim.getGrid();
				} else {
					sim = SimulationController.createEvacSimulation(false, curGrid);
				}
				sim.start();
				
				Statistics stats = sim.getStatistics();
				System.out.println(stats.getStatistic("People safe"));
				stats.getStatistic("Mean distance");
				stats.getStatistic("Door positions");
			}
		}
	}
}
