package evacSim.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collect statistics from a simulation.
 * 
 * @author Joseph Mattingly
 *
 */
public class Statistics {

	// TODO: right now, statistics have to be raw Lists
	// we might want to make our own Data class to encapsulate things
	Map<String, List> stats = new HashMap<String, List>();
	
	/**
	 * @param statisticName the name of the statistic
	 * @param data the timestep-index metric this name refers to
	 */
	void addStatistic(String statisticName, List data){
		stats.put(statisticName, data);
	}
	
	public List getStatistic(String statisticName){
		return stats.get(statisticName);
	}
}