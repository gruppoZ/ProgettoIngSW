package gestioneLogin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import gestioneUtenti.Utente;
import main.JsonIO;

public class GestioneAutenticazione {

	private static final String PATH_CREDENZIALI_DEFAULT = "resources/credenzialiDefault.json";
	protected static final String PATH_CREDENZIALI = "resources/credenziali.json";	
	
	//Login  e check appositi per default
	/**
	 * Precondizione: crdenziali != null
	 * 
	 * @param credenziali
	 * @return TRUE se le credenziali corrispondono a quelli di default
	 * @throws IOException 
	 */
	protected boolean checkCredenzialiPrimoAccesso(Credenziali credenziali) throws IOException {
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		
		if(credenzialiDefault.checkCredenzialiUguali(credenziali))
			return true;
		else
			return false;	
	}
	
	/**
	 * Precondizione: crdenziali != null AND utente != null
	 * 
	 * Richiede all'utente di inserire username e password 
	 * @param path
	 * @param erroreUsername
	 * @return TRUE se il login e' avvenuto con successo FALSE se ho avuto qualche problema (es passwrod errata)
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	protected boolean login(Utente utente, Credenziali credenziali) throws FileNotFoundException, IllegalArgumentException, IOException {		
		if(checkCredenziali(utente, PATH_CREDENZIALI, credenziali)) {
			utente.setCredenziali(credenziali);
			return true;
		} else
			return false;
	}
	
	/**
	 * Precondizione: crdenziali != null AND utente != null
	 * 
	 * Veiene salvato l'utente con le relative credenziali
	 * @param credenziali
	 * @param utente
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	protected void effettuaRegistrazione(Credenziali credenziali, Utente utente) throws FileNotFoundException, IllegalArgumentException, IOException {
		aggiornaFileCredenziali(credenziali, utente);
	}
	
	/**
	 * 
	 * @param username
	 * @return TRUE se è possibile procedere con la registrazione utilizzando username, FALSE altrimenti
	 * @throws IOException 
	 */
	protected boolean checkRegistrazione(String username) throws IOException {
		return (!checkUnique(username, PATH_CREDENZIALI) || checkDefault(username));
	}
		
	/**
	 * Precondizione: credenzaliDaControllare != null AND utente != null
	 * 
	 * Controlla se username e password esistono nel file .json
	 * @param path
	 * @param username
	 * @param password
	 * @param erroreUsername
	 * @return TRUE se le credenziali sono valide FALSE altrimenti
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	private boolean checkCredenziali(Utente utente, String path, Credenziali credenzaliDaControllare) throws FileNotFoundException, IllegalArgumentException, IOException {
		HashMap<String, ArrayList<Credenziali>> elencoCredenziali = JsonIO.leggiCredenzialiHashMapDaJson(path);
	
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
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	private boolean checkUnique(String username, String path) throws FileNotFoundException, IllegalArgumentException, IOException {
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
	 * @throws IOException 
	 */
	private boolean checkDefault(String username) throws IOException {
		
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);		
		String usernameDefault = credenzialiDefault.getUsername();
		
		if(username.equals(usernameDefault)) {
			return true;
		}
		return false;
	}
	
	protected Credenziali getCredenzialiDefault() throws IOException {
		Credenziali credenzialiDefault = (Credenziali) JsonIO.leggiOggettoDaJson(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		return credenzialiDefault;
	}
	
	/**
	 * Precondizione: crdenziali != null AND utente != null
	 * 
	 * @param credenziali
	 * @param utente
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	private void aggiornaFileCredenziali(Credenziali credenziali, Utente utente) throws FileNotFoundException, IllegalArgumentException, IOException {
		HashMap<String, ArrayList<Credenziali>> elencoCredenziali = JsonIO.leggiCredenzialiHashMapDaJson(PATH_CREDENZIALI);
		
		ArrayList<Credenziali> listaCredenziali = elencoCredenziali.get(utente.getClass().getSimpleName());
		listaCredenziali.add(credenziali);
		elencoCredenziali.put(utente.getClass().getSimpleName(), listaCredenziali);

		JsonIO.salvaOggettoSuJson(PATH_CREDENZIALI, elencoCredenziali);
	}
}
