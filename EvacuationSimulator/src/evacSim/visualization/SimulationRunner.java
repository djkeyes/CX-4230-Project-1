package evacSim.visualization;

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
		for (int i = 0; i < 1; i++) {
			SimulationController sim = SimulationController.createEvacSimulation(false, true);
			sim.start();
			Statistics stats = sim.getStatistics();
			System.out.println(stats.getStatistic("People safe"));
			stats.getStatistic("Mean distance");
			stats.getStatistic("Door positions");
		}
	}
}
