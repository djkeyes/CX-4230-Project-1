package evacSim.visualization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import evacSim.core.Grid;
import evacSim.core.SimulationController;
import evacSim.core.Statistics;

/**
 * This runs the simulation placing each door one at a time. First it places the 4 door optimally, then 2 door, then the 1 doors.
 * 
 * @author Daniel Keyes
 */
public class SimulationSearchRunner {
	public static void main(String[] args) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
		
		Grid curGrid = SimulationController.createDefaultBuildingPlacement();

		// edges of building
		List<int[]> edgeCoords = new LinkedList<int[]>();
		for (int row = 252, col = 47; col <= 262; col++)
			edgeCoords.add(new int[] { row, col });
		for (int col = 47, row = 253; row <= 299; row++)
			edgeCoords.add(new int[] { row, col });
		for (int col = 262, row = 253; row <= 299; row++)
			edgeCoords.add(new int[] { row, col });

		List<Integer> bestDoorIndices = new LinkedList<Integer>();
		int N_TRIALS = 20;
		double bestMeanTime = Double.MAX_VALUE;
		for (int doorIndex = 0; doorIndex < edgeCoords.size() - 4; doorIndex++) {
			// run many times to get a good mean
			long sumTotalTime = 0;
			for (int j = 0; j < N_TRIALS; j++) {
				SimulationController sim;
				sim = SimulationController.createEvacSimulation(false, curGrid);
				sim.start();

				Statistics stats = sim.getStatistics();
				writer.write(stats.getStatistic("People safe") + "\n");
				sumTotalTime += stats.getStatistic("People safe").size();
			}
			double meanTime = (double) sumTotalTime/N_TRIALS;
			if(meanTime < bestMeanTime){
				
			}
		}
	}
}
