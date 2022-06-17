package application;

public class PassaggioTraStati {
	
	String oldState;
	String newState;
	
	public PassaggioTraStati() {
		
	}
	
	public PassaggioTraStati(String oldState, String newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	public String getOldState() {
		return oldState;
	}

	public void setOldState(String oldState) {
		this.oldState = oldState;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}
}
