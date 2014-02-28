package evacSim.visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import evacSim.core.Door;
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
		
		Grid bestGrid = SimulationController.createDefaultBuildingPlacement();
		bestGrid.initializeEmptyCells();

		// edges of building
		List<int[]> edgeCoords = new LinkedList<int[]>();
		for (int row = 252, col = 47; col <= 262; col++)
			edgeCoords.add(new int[] { row, col });
		for (int col = 47, row = 253; row <= 299; row++)
			edgeCoords.add(new int[] { row, col });
		for (int col = 262, row = 253; row <= 299; row++)
			edgeCoords.add(new int[] { row, col });

		int[] sizes = { 4, 2, 1, 1, 1, 1 };

		LinkedList<Integer> bestDoorIndices = new LinkedList<Integer>();
		int N_TRIALS = 1;
		for (int i = 0; i < sizes.length; i++) {
			System.out.println("size: " + sizes[i]);
			
			bestDoorIndices.add(-1);
			double bestMeanTime = Double.MAX_VALUE;
			for (int doorIndex = 0; doorIndex < edgeCoords.size() - sizes[i]; doorIndex++) {
				System.out.println("trying door index " + doorIndex);
				// run many times to get a good mean
				long sumTotalTime = 0;
				for (int j = 0; j < N_TRIALS; j++) {
					System.out.println("trial number " + j);
					// try placing grid in current position
					Grid curGrid = bestGrid.copyAndReassign();
					for (int k = doorIndex; k < doorIndex + sizes[i]; k++) {
						int[] indices = edgeCoords.get(k);
						curGrid.setCell(indices[0], indices[1], new Door());
					}
					
					SimulationController sim;
					sim = SimulationController.createEvacSimulation(false, curGrid);
//					Visualizer window = new Visualizer(sim);
//					window.show();
					sim.start();
					System.out.println();

					Statistics stats = sim.getStatistics();
					System.out.println(stats.getStatistic("People safe"));
					writer.write(stats.getStatistic("People safe") + "\n");
					sumTotalTime += stats.getStatistic("People safe").size();
				}
				double meanTime = (double) sumTotalTime / N_TRIALS;
				if (meanTime < bestMeanTime) {
					bestMeanTime = meanTime;
					bestDoorIndices.removeLast();
					bestDoorIndices.add(doorIndex);
				}
			}
			System.out.println("Best location for " + sizes[i] + "door: " + bestDoorIndices.getLast());
			System.out.println("Time: " + bestMeanTime);
			writer.write("Best location for " + sizes[i] + "door: " + bestDoorIndices.getLast() + "\n");
			// we now know the best index for the latest door
			bestGrid = bestGrid.copyAndReassign(); // reassign the old cells to this grid
			for (int j = bestDoorIndices.getLast(); j < bestDoorIndices.getLast() + sizes[i]; j++) {
				int[] indices = edgeCoords.get(j);
				bestGrid.setCell(indices[0], indices[1], new Door());
			}
			// TODO: this approach isn't great, because a two-tile door is sometimes split in half by a quadrouple door. instead just
			// make a visited list or something
			// remove that door and some surrounding tiles from the index
			int start = Math.max(0, bestDoorIndices.getLast() - 1);
			int end = Math.min(edgeCoords.size() - 1, bestDoorIndices.getLast() + sizes[i] + 1);
			// if we would orphan cells, just remove them too
			if (i < sizes.length) {
				int orphanSize = sizes[i + 1];
				if (start < orphanSize)
					start = 0;
				if (end >= edgeCoords.size() - orphanSize)
					end = edgeCoords.size() - 1;
			}
			for (int j = end - 1; j >= start; j--) {
				edgeCoords.remove(j);
			}

		}
	}
}
