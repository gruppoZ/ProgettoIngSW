package gestioneLogin;

import gestioneUtenti.Configuratore;

public class ViewAuthConfiguratore extends ViewAuth{

	private static final String MSG_GIVE_CREDENZIALI_DEFAULT = "Credenziali Default: \n\tUsername: %s \tPassword: %s\n";
	private static final String MSG_PRE_REGISTRAZIONE = "Le credenziali da utilizzare per il primo accesso sono:";
	private static final String TIPOLOGIA_UTENTE = "CONFIGURATORE\n";
	
	private Configuratore configuratore;
	
	public ViewAuthConfiguratore() {
		super();
		configuratore = new Configuratore();
	}

	@Override
	public void login() {
		System.out.printf(INTESTAZIONE_LOGIN, TIPOLOGIA_UTENTE);
		super.checkLogin(configuratore);
	}

	@Override
	public void registrati() {
		System.out.printf(INTESTAZIONE_REGISTRAZIONE, TIPOLOGIA_UTENTE);
		System.out.println(MSG_PRE_REGISTRAZIONE);
		
		showCredenzialiDefault();
		
		if(loginDefault()) { //primo accesso configuratore
			super.registrati(configuratore);
			//Una volta avvenuta la registrazione viene richiesto direttamente di fare il login
			login();
		}
	}
	
	private boolean loginDefault() {
		String username = chiediUsername(MSG_USERNAME_LOGIN);
		String password = chiediPassword(MSG_PASSWORD_LOGIN);
		
		Credenziali credenziali = new Credenziali(username, password);
		
		if(getGestoreAuth().checkCredenzialiPrimoAccesso(credenziali)) {
			System.out.println(MSG_LOGIN_EFFETTUATO);
			return true;
		} else {
			System.out.println(MSG_ERRORE_CREDENZIALI_DEFAULT);
			return false;	
		}
	}
	
	private void showCredenzialiDefault() {
		String usernameDefault = getGestoreAuth().getCredenzialiDefault().getUsername();
		String passwordDefault = getGestoreAuth().getCredenzialiDefault().getPassword();
		
		System.out.printf(MSG_GIVE_CREDENZIALI_DEFAULT, usernameDefault, passwordDefault);
	}
}
