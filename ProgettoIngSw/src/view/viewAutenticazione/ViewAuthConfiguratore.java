package view.viewAutenticazione;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Configuratore;
import application.Utente;
import view.ViewConfiguratore;

public class ViewAuthConfiguratore extends ViewAuth{

	private static final String MSG_ERROR_LETTURA_CREDENZIALI_DEFAULT = "*** ERRORE lettura credenziali Default da file ***";
	private static final String MSG_GIVE_CREDENZIALI_DEFAULT = "Credenziali Default: \n\tUsername: %s \tPassword: %s\n";
	private static final String MSG_PRE_REGISTRAZIONE = "Le credenziali da utilizzare per il primo accesso sono:";
	private static final String TIPOLOGIA_UTENTE = "CONFIGURATORE\n";
	
	private Utente configuratore;
	
	public ViewAuthConfiguratore() {
		super();
		configuratore = new Configuratore();
	}

	@Override
	public void login() throws FileNotFoundException, IOException {
		try {
			System.out.printf(INTESTAZIONE_LOGIN, TIPOLOGIA_UTENTE);
			super.checkLogin(configuratore);
			
			ViewConfiguratore view = new ViewConfiguratore();
			view.menu(configuratore.getUsername());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	@Override
	public void registrati() throws FileNotFoundException, IOException {
		System.out.printf(INTESTAZIONE_REGISTRAZIONE, TIPOLOGIA_UTENTE);
		System.out.println(MSG_PRE_REGISTRAZIONE);
		
		showCredenzialiDefault();
		
		if(loginDefault()) { //primo accesso configuratore
			super.registrati(configuratore);
			
			//Una volta avvenuta la registrazione viene richiesto direttamente di fare il login
			login();
		}
	}
	
	private boolean loginDefault() throws IOException {
		String username = chiediUsername(MSG_USERNAME_LOGIN);
		String password = chiediPassword(MSG_PASSWORD_LOGIN);
		
		try {
			if(getGestoreAuth().checkCredenzialiPrimoAccesso(username, password)) {
				System.out.println(MSG_LOGIN_EFFETTUATO);
				return true;
			} else {
				System.out.println(MSG_ERROR_CREDENZIALI_DEFAULT);
				return false;	
			}
			
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_LOGIN_FILE_NON_TROVATO);
		}
	}
	
	private void showCredenzialiDefault() throws IOException {
		String usernameDefault, passwordDefault;
		
		try {
			usernameDefault = getGestoreAuth().getUsernameDefault();
			passwordDefault = getGestoreAuth().getPasswordDefault();
			
			System.out.printf(MSG_GIVE_CREDENZIALI_DEFAULT, usernameDefault, passwordDefault);
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_LETTURA_CREDENZIALI_DEFAULT);
		}
	}
}
