package gestioneLogin;

import gestioneUtenti.Fruitore;

public class ViewAuthFruitore extends ViewAuth {	
	
	private static final String TIPOLOGIA_UTENTE = "FRUITORE\n";
	
	private Fruitore fruitore;
	
	public ViewAuthFruitore() {
		super();
		fruitore = new Fruitore();
	}

	@Override
	public void login() {
		System.out.printf(INTESTAZIONE_LOGIN, TIPOLOGIA_UTENTE);
		super.checkLogin(fruitore);
	}

	@Override
	public void registrati() {
		System.out.printf(INTESTAZIONE_REGISTRAZIONE, TIPOLOGIA_UTENTE);
		
		super.registrati(fruitore);
		//Una volta avvenuta la registrazione viene richiesto direttamente di fare il login
		login();
	}
}
