package gestioneLogin;

import gestioneUtenti.Utente;
import it.unibs.fp.mylib.InputDati;

public abstract class ViewAuth {

	protected static final String INTESTAZIONE_LOGIN = "Login %s";
	protected static final String INTESTAZIONE_REGISTRAZIONE = "Registrazione %s";
	private static final String MSG_RIPETI_LOGIN = "Vuoi rifare il login?";
	protected static final String MSG_ERRORE_CREDENZIALI_DEFAULT = "ATTENZIONE: CREDENZIALI ERRATE";
	private static final String MSG_USERNAME_INESISTENTE ="ATTENZIONE: USERNAME NON ESISTE";
	protected static final String MSG_USERNAME_ESISTENTE ="ATTENZIONE: USERNAME GIA' ESISTENTE";
	protected static final String MSG_PASSWORD_ERRATA ="ATTENZIONE: PASSWORD ERRATA";
	protected static final String MSG_LOGIN_EFFETTUATO = "Login avvenuto con successo";
	private static final String MSG_USERNAME ="Inserisci lo username desiderato:";
	private static final String MSG_PASSWORD ="Inserisci la password desiderata:";
	protected static final String MSG_USERNAME_LOGIN ="Inserisci lo username:";
	protected static final String MSG_PASSWORD_LOGIN ="Inserisci la password:";
	
	private GestioneAutenticazione gestoreAuth;
	
	public ViewAuth() {
		gestoreAuth = new GestioneAutenticazione();
	}
	
	public abstract void login();
	public abstract void registrati();
	
	public boolean login(Utente utente) {
		String username = chiediUsername(MSG_USERNAME_LOGIN);
		String password = chiediPassword(MSG_PASSWORD_LOGIN);
		
		if(this.gestoreAuth.login(utente, new Credenziali(username, password))) {
			System.out.println(MSG_LOGIN_EFFETTUATO);
			return true;
		}
		else {
			System.out.println(MSG_USERNAME_INESISTENTE);
			return false;
		}
	}
	
	public void registrati(Utente utente) {
		String username;
		String password;
		Credenziali credenziali;
				
		username = chiediUsername(MSG_USERNAME);
		
		while(this.gestoreAuth.checkRegistrazione(username)) {
			System.out.println(MSG_USERNAME_ESISTENTE);
			username = chiediUsername(MSG_USERNAME);
		}
		
		password = chiediPassword(MSG_PASSWORD);
		
		credenziali = new Credenziali(username, password);
		
		System.out.println("Registrazione avvenuta con successo");
		System.out.println("Lo username e': " + username + " la password e': " + password);

		this.gestoreAuth.effettuaRegistrazione(credenziali, utente);
		
		login(utente);
	}
	
	public void checkLogin(Utente utente) {
		boolean ritenta;
		do {
			if(login(utente)) {
				utente.menu();
				ritenta = false;
			} else {
				ritenta = InputDati.yesOrNo(MSG_RIPETI_LOGIN);
			}
		} while(ritenta);
	}
	
	public GestioneAutenticazione getGestoreAuth() {
		return gestoreAuth;
	}

	public void setGestoreAuth(GestioneAutenticazione gestoreAuth) {
		this.gestoreAuth = gestoreAuth;
	}
		
	protected static String chiediUsername(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}
	
	protected static String chiediPassword(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}
}
