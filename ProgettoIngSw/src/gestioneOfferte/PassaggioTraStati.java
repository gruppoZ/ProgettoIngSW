package gestioneOfferte;

public class PassaggioTraStati {
	
	StatoOfferta oldState;
	StatoOfferta newState;
	
	public PassaggioTraStati(StatoOfferta oldState, StatoOfferta newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
}
