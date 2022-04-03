package gestioneLogin;

import java.util.ArrayList;
import java.util.HashMap;

import gestioneUtenti.Utente;
import main.JsonIO;

public class GestioneAutenticazione {

	private static final String PATH_CREDENZIALI_DEFAULT = "src/gestioneLogin/credenzialiDefault.json";
	protected static final String PATH_CREDENZIALI = "src/gestioneLogin/credenziali.json";	
	
	//Login  e check appositi per default
	/**
	 * 
	 * @param credenziali
	 * @return TRUE se le credenziali corrispondono a quelli di default
	 */
	protected boolean checkCredenzialiPrimoAccesso(Credenziali credenziali) {
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		
		if(credenzialiDefault.checkCredenzialiUguali(credenziali))
			return true;
		else
			return false;	
	}
	
	/**
	 * Richiede all'utente di inserire username e password 
	 * @param path
	 * @param erroreUsername
	 * @return TRUE se il login e' avvenuto con successo FALSE se ho avuto qualche problema (es passwrod errata)
	 */
	protected boolean login(Utente utente, Credenziali credenziali) {		
		if(checkCredenziali(utente, PATH_CREDENZIALI, credenziali)) {
			utente.setCredenziali(credenziali);
			return true;
		} else
			return false;
	}
	
	/**
	 * Veiene salvato l'utente con le relative credenziali
	 * @param credenziali
	 * @param utente
	 */
	protected void effettuaRegistrazione(Credenziali credenziali, Utente utente) {
		aggiornaFileCredenziali(credenziali, utente);
	}
	
	/**
	 * 
	 * @param username
	 * @return TRUE se è possibile procedere con la registrazione utilizzando username, FALSE altrimenti
	 */
	protected boolean checkRegistrazione(String username) {
		return (!checkUnique(username, PATH_CREDENZIALI) || checkDefault(username));
	}
		
	/**
	 * Controlla se username e password esistono nel file .json
	 * @param path
	 * @param username
	 * @param password
	 * @param erroreUsername
	 * @return TRUE se le credenziali sono valide FALSE altrimenti
	 */
	private boolean checkCredenziali(Utente utente, String path, Credenziali credenzaliDaControllare) {
		HashMap<String, ArrayList<Credenziali>> elencoCredenziali = JsonIO.leggiCredenzialiHashMapDaJson(path);
		//problema e' che creo objectMapper pure qua -> potrei risolvere creando un metodo leggiCredenzialiHashMapDaJson che 
		//fa le righe 105-107 e chiama il generic
		
		ArrayList<Credenziali> listaCredenziali = elencoCredenziali.get(utente.getClass().getSimpleName());
		for (Credenziali credenziali : listaCredenziali) {
			if(credenziali.checkCredenzialiUguali(credenzaliDaControllare))
				return true;
		}
		return false;
	}
	
	/**
	 * Controlla eventuali omonimie
	 * @param username
	 * @param path
	 * @return TRUE se lo username e' unico FALSE se esiste gia'
	 */
	private boolean checkUnique(String username, String path) {
		HashMap<String, ArrayList<Credenziali>> elencoCredenziali = JsonIO.leggiCredenzialiHashMapDaJson(path);
		
		for (ArrayList<Credenziali> listaCredenziali : elencoCredenziali.values()) {
			for (Credenziali credenziali : listaCredenziali) {
				if(credenziali.getUsername().equals(username)) {
					return false;
				}
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
	private boolean checkDefault(String username) {
		
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);		
		String usernameDefault = credenzialiDefault.getUsername();
		
		if(username.equals(usernameDefault)) {
			return true;
		}
		return false;
	}
	
	protected Credenziali getCredenzialiDefault() {
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		return credenzialiDefault;
	}
	
	private void aggiornaFileCredenziali(Credenziali credenziali, Utente utente) {
		HashMap<String, ArrayList<Credenziali>> elencoCredenziali = JsonIO.leggiCredenzialiHashMapDaJson(PATH_CREDENZIALI);
		
		ArrayList<Credenziali> listaCredenziali = elencoCredenziali.get(utente.getClass().getSimpleName());
		listaCredenziali.add(credenziali);
		elencoCredenziali.put(utente.getClass().getSimpleName(), listaCredenziali);

		JsonIO.salvaOggettoSuJson(PATH_CREDENZIALI, elencoCredenziali);
	}
}
