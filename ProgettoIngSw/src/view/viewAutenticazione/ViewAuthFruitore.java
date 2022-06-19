package view.viewAutenticazione;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Fruitore;
import view.ViewFruitore;

public class ViewAuthFruitore extends ViewAuth {	
	
	private static final String TIPOLOGIA_UTENTE = "FRUITORE\n";
	
	private Fruitore fruitore;
	
	public ViewAuthFruitore() {
		super();
		fruitore = new Fruitore();
	}

	@Override
	public void login() throws FileNotFoundException, IOException {
		System.out.printf(INTESTAZIONE_LOGIN, TIPOLOGIA_UTENTE);
		super.checkLogin(fruitore);
		
		ViewFruitore view = new ViewFruitore();
		view.menu(fruitore.getCredenziali().getUsername());
	}

	@Override
	public void registrati() throws FileNotFoundException, IOException {
		System.out.printf(INTESTAZIONE_REGISTRAZIONE, TIPOLOGIA_UTENTE);
		
		super.registrati(fruitore);
		//Una volta avvenuta la registrazione viene richiesto direttamente di fare il login
		login();
	}
}
