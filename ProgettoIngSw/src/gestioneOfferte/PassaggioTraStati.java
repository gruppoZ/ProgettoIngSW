package gestioneOfferte;

public class PassaggioTraStati {
	
	StatoOfferta oldState;
	StatoOfferta newState;
	
	public PassaggioTraStati(StatoOfferta oldState, StatoOfferta newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	public StatoOfferta getOldState() {
		return oldState;
	}

	public void setOldState(StatoOfferta oldState) {
		this.oldState = oldState;
	}

	public StatoOfferta getNewState() {
		return newState;
	}

	public void setNewState(StatoOfferta newState) {
		this.newState = newState;
	}
}
