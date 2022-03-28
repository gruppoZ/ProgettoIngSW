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
	public void registrati() {
		//(new GestioneConfiguratore()).registrati(this);
	}

	@Override
	public void accedi() {
		//(new GestioneConfiguratore()).accedi(this);
	}

	@Override
	public void menu() {
		(new ViewConfiguratore()).menu();
	}
	
	@Override
	public String toString() {
		return "Configuratore [credenziali=" + this.getCredenziali() + "]";
	}
}
