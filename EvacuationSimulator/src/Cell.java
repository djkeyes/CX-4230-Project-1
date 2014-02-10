/**
 * @author Joseph Mattingly
 *
 */
public class Cell {
	private int weight;
	
	/**
	 * Create a new cell in the CA grid.  The default weight is zero, or completely empty.
	 */
	public Cell(){
		weight = 0;
	}
	
	/**
	 * Create a new cell in the CA grid with a given weighting.
	 * @param weight The cell weighting
	 */
	public Cell(int weight){
		this.weight = weight;
	}
	
	/**
	 * Update the CA model weighting of this cell.
	 * @param weight The cell weighting
	 */
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	/**
	 * Get the weighting value of this cell as defined by the CA model.
	 * @return The cell weighting
	 */
	public int getWeight(){
		return weight;
	}
}