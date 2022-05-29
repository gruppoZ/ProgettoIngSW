package gestioneLogin;

import java.util.ArrayList;

import gestioneUtenti.GestioneConfiguratore;
import it.unibs.fp.mylib.InputDati;
import main.JsonIO;

//Nelle prossime versioni diventera' GestioneUtente dato che avremo altri tipi di utenti
public class GestioneAutenticazione {

	private static final String MSG_LOGIN_EFFETTUATO = "Login avvenuto con successo";
	private static final String MSG_USERNAME ="Inserisci lo username desiderato:";
	private static final String MSG_USERNAME_LOGIN ="Inserisci lo username:";
	private static final String MSG_PASSWORD ="Inserisci la password desiderata:";
	private static final String MSG_PASSWORD_LOGIN ="Inserisci la password:";
	private static final String MSG_USERNAME_ESISTENTE ="ATTENZIONE: USERNAME GIA' ESISTENTE";
	private static final String MSG_USERNAME_DEFAULT ="ATTENZIONE: USERNAME NON UTILIZZABILE";
	private static final String MSG_PASSWORD_ERRATA ="ATTENZIONE: PASSWORD ERRATA";
	
	/**
	 * Richiede all'utente di inserire username e password 
	 * @param path
	 * @param erroreUsername
	 * @return TRUE se il login e' avvenuto con successo FALSE se ho avuto qualche problema (es passwrod errata)
	 */
	public static boolean login(String path, String erroreUsername) {
		String username = InputDati.leggiStringaNonVuota(MSG_USERNAME_LOGIN);
		String password = InputDati.leggiStringaNonVuota(MSG_PASSWORD_LOGIN);
		
		if(checkCredenziali(path, username, password, erroreUsername)) {
			System.out.println(MSG_LOGIN_EFFETTUATO);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Controlla se username e password esistono nel file .json
	 * @param path
	 * @param username
	 * @param password
	 * @param erroreUsername
	 * @return TRUE se le credenziali sono valide FALSE altrimenti
	 */
	private static boolean checkCredenziali(String path, String username, String password, String erroreUsername) {
		ArrayList<Credenziali> listaCredenziali = JsonIO.leggiCredenzialiDaJson(path);
		for (Credenziali credenziali : listaCredenziali) {
			if(credenziali.getUsername().equals(username)) {
				if(credenziali.getPassword().equals(password)) {
					return true;
				} else {
					System.out.println(MSG_PASSWORD_ERRATA);
					return false;
				}
			}
		}
		
		System.out.println(erroreUsername);
		return false;
	}
	
	/**
	 * Richiede di inserire username e password, dopodiche' inserisce le credenziali del nuovo utente nel file .json
	 * @param path
	 */
	public static void registrazione(String pathConfiguratori, String pathDefault) {
		String username;
		
		System.out.println("\nRegistrazione ");
		
		do {
			username = InputDati.leggiStringaNonVuota(MSG_USERNAME);
		} while(!checkUnique(username, pathConfiguratori) || checkDefault(username, pathDefault));	
		
		String password = InputDati.leggiStringaNonVuota(MSG_PASSWORD);
		
		System.out.println("Registrazione avvenuta con successo");
		System.out.println("Lo username e': " + username + " la psw e': " + password);
		
		ArrayList<Credenziali> listaCredenziali = JsonIO.leggiCredenzialiDaJson(pathConfiguratori);
		listaCredenziali.add(new Credenziali(username, password));
		
		JsonIO.salvaListSuJson(pathConfiguratori, listaCredenziali);
	}
	
	/**
	 * Controlla eventuali omonimie
	 * @param username
	 * @param path
	 * @return TRUE se lo username e' unico FALSE se esiste gia'
	 */
	private static boolean checkUnique(String username, String path) {
		ArrayList<Credenziali> listaCredenziali = JsonIO.leggiCredenzialiDaJson(path);
		
		for (Credenziali credenziali : listaCredenziali) {
			if(credenziali.getUsername().equals(username)) {
				System.out.println(MSG_USERNAME_ESISTENTE);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param username
	 * @param pathDefault
	 * @return TRUE se lo username passato e' uguale a quello utilizzato nelle credenziali di default
	 */
	private static boolean checkDefault(String username, String pathDefault) {
		
		ArrayList<Credenziali> credenzialiDefault = JsonIO.leggiCredenzialiDaJson(pathDefault);
		String usernameDefault = credenzialiDefault.get(0).getUsername();
		
		if(username.equals(usernameDefault)) {
			System.out.println(MSG_USERNAME_DEFAULT);
			return true;
		}
		return false;
	}
	
	public static void gestioneLogin(String path, String msgErrore, String msgRipetiLogin) {
		boolean ritenta;
		
		System.out.println("\nLogin");
		do {
			if(login(path, msgErrore)) {
				GestioneConfiguratore.menuConfiguratore();
				ritenta = false;
			} else {
				ritenta = InputDati.yesOrNo(msgRipetiLogin);
			}
		} while(ritenta);
	}
	
}
