package application;

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
	
	public String getUsername() {
		return this.credenziali.getUsername();
	}
	
	public String getPassword() {
		return this.credenziali.getPassword();
	}
}
