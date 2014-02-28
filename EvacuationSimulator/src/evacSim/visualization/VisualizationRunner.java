package evacSim.visualization;

import evacSim.core.SimulationController;

/**
 * This runs the simulation with the visulization. It may take time to run since it has to wait for the timer to
 * tick in real time
 * 
 * @author Daniel Keyes
 */
public class VisualizationRunner {

	
	public static void main(String[] args){
		SimulationController sim = SimulationController.createEvacSimulation(true, false);
		Visualizer window = new Visualizer(sim);
		window.show();
		sim.start();
	}
}
