package gestioneUtenti;

import gestioneLogin.Credenziali;

public class Configuratore extends Utente {

	public Configuratore() {
		
	}
	/**
	 * @param credenziali
	 */
	public Configuratore(Credenziali credenziali) {
		super(credenziali);
	}

	@Override
	public void menu(String username) {
		(new ViewConfiguratore()).menu(username);
	}
	
	@Override
	public String toString() {
		return "Configuratore [credenziali=" + this.getCredenziali() + "]";
	}
}
