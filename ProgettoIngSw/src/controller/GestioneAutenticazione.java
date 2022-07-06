	package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Credenziali;
import application.Utente;
import utils.FileSystemOperations;
import utils.JsonIO;

public class GestioneAutenticazione {

	private static final String PATH_CREDENZIALI_DEFAULT = "resources/credenzialiDefault.json";
	protected static final String PATH_CREDENZIALI = "resources/credenziali.json";	
	
	private FileSystemOperations fs;
	
	public GestioneAutenticazione() {
		fs = new JsonIO();
	}
	//Login  e check appositi per default
	/**
	 * Precondizione: crdenziali != null
	 * 
	 * @param credenziali
	 * @return TRUE se le credenziali corrispondono a quelli di default
	 * @throws IOException 
	 */
	public boolean checkCredenzialiPrimoAccesso(String username, String password) throws IOException {
		Credenziali credenzialiDefault = (Credenziali) fs.leggiOggetto(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		
		if(credenzialiDefault.checkCredenzialiUguali(new Credenziali(username, password)))
			return true;
		else
			return false;	
	}
	
	/**
	 * Precondizione: crdenziali != null AND utente != null
	 * 
	 * Richiede all'utente di inserire username e password 
	 * @param utente
	 * @param username
	 * @param password
	 * @return TRUE se il login e' avvenuto con successo FALSE se ho avuto qualche problema (es passwrod errata)
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */	
	public boolean login(Utente utente, String username, String password) throws FileNotFoundException, IllegalArgumentException, IOException {		
		Credenziali credenziali = new Credenziali(username, password);
		
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
	public void effettuaRegistrazione(String username, String password, Utente utente) throws FileNotFoundException, IllegalArgumentException, IOException {
		aggiornaFileCredenziali(new Credenziali(username, password), utente);
	}
	
	/**
	 * 
	 * @param username
	 * @return TRUE se è possibile procedere con la registrazione utilizzando username, FALSE altrimenti
	 * @throws IOException 
	 */
	public boolean checkRegistrazione(String username) throws IOException {
		return (!checkUnique(username, PATH_CREDENZIALI) || checkDefault(username));
	}
		
	/**
	 * Precondizione: credenzaliDaControllare != null AND utente != null
	 * 
	 * Controlla se username e password esistono nel file .json
	 * @param utente
	 * @param path
	 * @param credenzaliDaControllare
	 * @return TRUE se le credenziali sono valide FALSE altrimenti
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws FileNotFoundException 
	 */
	private boolean checkCredenziali(Utente utente, String path, Credenziali credenzaliDaControllare) throws FileNotFoundException, IllegalArgumentException, IOException {
		Map<String, ArrayList<Credenziali>> elencoCredenziali = (HashMap<String, ArrayList<Credenziali>>) fs.leggiCredenzialiMap(path);
	
		List<Credenziali> listaCredenziali = elencoCredenziali.get(utente.getClass().getSimpleName());
		
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
		Map<String, ArrayList<Credenziali>> elencoCredenziali = (HashMap<String, ArrayList<Credenziali>>) fs.leggiCredenzialiMap(path);
		
		for (List<Credenziali> listaCredenziali : elencoCredenziali.values()) {
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
	 * @return TRUE se lo username passato e' uguale a quello utilizzato nelle credenziali di default
	 * @throws IOException 
	 */
	private boolean checkDefault(String username) throws IOException {	
		Credenziali credenzialiDefault = (Credenziali) fs.leggiOggetto(PATH_CREDENZIALI_DEFAULT, Credenziali.class);		
		String usernameDefault = credenzialiDefault.getUsername();
		
		if(username.equals(usernameDefault)) {
			return true;
		}
		return false;
	}
	
	public Credenziali getCredenzialiDefault() throws IOException {
		Credenziali credenzialiDefault = (Credenziali) fs.leggiOggetto(PATH_CREDENZIALI_DEFAULT, Credenziali.class);
		return credenzialiDefault;
	}
	
	public String getUsernameDefault() throws IOException {
		return this.getCredenzialiDefault().getUsername();
	}
	
	public String getPasswordDefault() throws IOException {
		return this.getCredenzialiDefault().getPassword();
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
		Map<String, ArrayList<Credenziali>> elencoCredenziali = (HashMap<String, ArrayList<Credenziali>>) fs.leggiCredenzialiMap(PATH_CREDENZIALI);
		
		ArrayList<Credenziali> listaCredenziali = elencoCredenziali.get(utente.getClass().getSimpleName());
		listaCredenziali.add(credenziali);
		elencoCredenziali.put(utente.getClass().getSimpleName(), listaCredenziali);

		fs.salvaOggetto(PATH_CREDENZIALI, elencoCredenziali);
	}
}
