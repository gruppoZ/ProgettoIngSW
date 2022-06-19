package view.viewAutenticazione;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Credenziali;
import application.Utente;
import controller.GestioneAutenticazione;
import it.unibs.fp.mylib.InputDati;

public abstract class ViewAuth {

	protected static final String INTESTAZIONE_LOGIN = "\nLogin %s";
	protected static final String INTESTAZIONE_REGISTRAZIONE = "Registrazione %s";
	protected static final String MSG_ERROR_CREDENZIALI_DEFAULT = "ATTENZIONE: CREDENZIALI ERRATE";
	protected static final String MSG_ERROR_LOGIN_FILE_NON_TROVATO = "Impossibile eseguire login. Fallita interazione con il file";
	private static final String MSG_ERROR_LETTURA_CREDENZIALI = "*** ERRORE letture credenziali da file ***";
	private static final String MSG_RIPETI_LOGIN = "Vuoi rifare il login?";
	protected static final String MSG_USERNAME_ESISTENTE = "ATTENZIONE: USERNAME GIA' ESISTENTE";
	protected static final String MSG_PASSWORD_ERRATA = "ATTENZIONE: PASSWORD ERRATA";
	protected static final String MSG_LOGIN_EFFETTUATO = "Login avvenuto con successo";
	private static final String MSG_USERNAME = "Inserisci lo username desiderato:";
	private static final String MSG_PASSWORD = "Inserisci la password desiderata:";
	protected static final String MSG_USERNAME_LOGIN = "Inserisci lo username:";
	protected static final String MSG_PASSWORD_LOGIN = "Inserisci la password:";
	protected static final String MSG_CREDENZIALI_ERRATE = "\nCredenziali errate!";
	protected static final String MSG_RESISTRAZIONE_SUCCESSO_GIVE_CREDENZIALI = "Registrazione avvenuta con successo!\nLo username e': %s la password e': %s \n";
	
	private GestioneAutenticazione gestoreAuth;
	
	public ViewAuth() {
		gestoreAuth = new GestioneAutenticazione();
	}
	
	public abstract void login() throws FileNotFoundException, IOException;
	public abstract void registrati() throws FileNotFoundException, IOException;
	
	/**
	 * Precondizione: utente != null
	 * @param utente
	 * @return
	 * @throws IOException 
	 */
	public boolean login(Utente utente) throws FileNotFoundException, IOException {
		String username = chiediUsername(MSG_USERNAME_LOGIN);
		String password = chiediPassword(MSG_PASSWORD_LOGIN);
		
		try {
			if(this.gestoreAuth.login(utente, new Credenziali(username, password))) {
				System.out.println(MSG_LOGIN_EFFETTUATO);
				return true;
			}
			else {
				System.out.println(MSG_CREDENZIALI_ERRATE);
				return false;
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(MSG_ERROR_LOGIN_FILE_NON_TROVATO);
		} catch (IllegalArgumentException | IOException e) {
			throw new IOException(MSG_ERROR_LOGIN_FILE_NON_TROVATO);
		}
	}
	
	/**
	 * Precondizione: utente != null
	 * Permette all'utente di registrarsi gestendo il caso in cui si utilizzi un username già in uso
	 * @param utente
	 * @throws IOException 
	 */
	public void registrati(Utente utente) throws IOException {
		String username;
		String password;
		Credenziali credenziali;
				
		username = chiediUsername(MSG_USERNAME);
		
		try {
			while(this.gestoreAuth.checkRegistrazione(username)) {
				System.out.println(MSG_USERNAME_ESISTENTE);
				username = chiediUsername(MSG_USERNAME);
			}
			
			password = chiediPassword(MSG_PASSWORD);
			
			credenziali = new Credenziali(username, password);
			
			System.out.printf(MSG_RESISTRAZIONE_SUCCESSO_GIVE_CREDENZIALI, username, password);

			this.gestoreAuth.effettuaRegistrazione(credenziali, utente);
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_LETTURA_CREDENZIALI);
		}
	}
	
	/**
	 * Precondizione: utente != null
	 * Verifica se l'utente si è loggatto correttaente, in tal caso mostra il relativo menu
	 * Altrimenti viene chiesto se si vuole ripetere il login
	 * @param utente
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void checkLogin(Utente utente) throws FileNotFoundException, IOException {
		boolean ritenta;
		do {
			if(login(utente)) {
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
