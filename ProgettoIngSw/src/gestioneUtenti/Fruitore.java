package gestioneUtenti;

import gestioneLogin.Credenziali;

public class Fruitore extends Utente {

	public Fruitore() {
		
	}

	/**
	 * @param credenziali
	 */
	public Fruitore(Credenziali credenziali) {
		super(credenziali);
	}
	
	@Override
	public void registrati() {
		//(new GestioneFruitore()).registrati(this);
	}

	@Override
	public void accedi() {
		//(new GestioneFruitore()).accedi(this);		
	}

	@Override
	public void menu(String username) {
		(new ViewFruitore()).menu(username);				
	}
	
	@Override
	public String toString() {
		return "Fruitore [credenziali=" + this.getCredenziali() + "]";
	}
}
