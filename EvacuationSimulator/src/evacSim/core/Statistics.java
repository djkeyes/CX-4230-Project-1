package evacSim.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collect and report statistics from a simulation.
 * 
 * @author Joseph Mattingly
 * @author Daniel Keyes
 */
public class Statistics {

	// TODO: right now, statistics have to be raw Lists
	// we might want to make our own Data class to encapsulate things
	Map<String, List> stats = new HashMap<String, List>();
	
	/**
	 * Store statistics from the simulation under a user-defined name.
	 * 
	 * @param statisticName The name of the statistic
	 * @param data The timestep-index metric this name refers to
	 */
	void addStatistic(String statisticName, List data){
		stats.put(statisticName, data);
	}
	
	/**
	 * Returns a statistic of a pre-assigned name.
	 * 
	 * @param statisticName Name of the statistic, orginally assigned by the user
	 * @return The statistic requested by the user
	 */
	public List getStatistic(String statisticName){
		return stats.get(statisticName);
	}
}