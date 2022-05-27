package gestioneUtenti;

import java.io.IOException;

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
	public void menu(String username) throws IOException {
		(new ViewConfiguratore()).menu(username);
	}
}
