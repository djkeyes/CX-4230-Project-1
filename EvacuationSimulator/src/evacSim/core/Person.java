package evacSim.core;

public class Person extends Cell {

	private int count;
	public Person(){
		this(10);
	}
	private Person(int count){
		this.count = count;
	}
	@Override
	public void calcUpdate() {
		// TODO People try to move north, or something.
		
		
		// here's a simple/buggy implementation that probably won't work in general
		if(--count == 0){
			// check to make sure no one has already set our next state
			if(getNextState() == null)
				setNextState(new EmptyCell());
			// walk north, until we fall off the screen
			if(getRow() > 0)
				getGrid().getCell(getRow()-1, getCol()).setNextState(new Person());
		}
		
	}

}
