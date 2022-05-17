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
	public void menu(String username) {
		(new ViewFruitore()).menu(username);				
	}
}
