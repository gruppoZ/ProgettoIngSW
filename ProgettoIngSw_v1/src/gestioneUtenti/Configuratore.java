package gestioneUtenti;

import gestioneLogin.Credenziali;

public class Configuratore {
	
	private Credenziali credenziali;
	
	public Configuratore() {
	}
	
	public Configuratore(Credenziali credenziali) {
		this.credenziali = credenziali;
	}
	
	public Credenziali getCredenziali() {
		return credenziali;
	}
	
	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}
	
	@Override
	public String toString() {
		return "Configuratore [credenziali=" + credenziali + "]";
	}
}
