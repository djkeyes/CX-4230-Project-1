package evacSim.visualization;

import evacSim.core.SimulationController;

public class VisualizationRunner {

	
	public static void main(String[] args){
		SimulationController sim = SimulationController.createSimpleSimulation();
		Visualizer window = new Visualizer(sim);
		window.show();
		sim.start();
	}
}
