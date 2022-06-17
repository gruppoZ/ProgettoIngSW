package application;

import java.io.IOException;

public abstract class Utente {

	private Credenziali credenziali;

	public Utente() {
	}
	
	/**
	 * @param credenziali
	 */
	public Utente(Credenziali credenziali) {
		super();
		this.credenziali = credenziali;
	}
	
	public Credenziali getCredenziali() {
		return credenziali;
	}

	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}	
	
	public abstract void menu(String username) throws IOException;
}
