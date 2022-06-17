package application;

import java.io.IOException;

import view.ViewFruitore;

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
	public void menu(String username) throws IOException {
		(new ViewFruitore()).menu(username);				
	}
}
