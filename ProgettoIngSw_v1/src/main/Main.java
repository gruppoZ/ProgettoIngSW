package main;

import java.util.ArrayList;
import gestioneLogin.Credenziali;
import gestioneLogin.GestioneAutenticazione;
import it.unibs.fp.mylib.MyMenu;

public class Main {

	private static final String MSG_RIPETI_LOGIN = "Vuoi rifare il login?";
	private static final String TXT_TITOLO = "Benvenuto";
	private static final String TXT_ERRORE = "ERRORE";
	private static final String MSG_REGISTRATI = "Registrati";
	private static final String MSG_LOGIN = "Login";
	private static final String MSG_PRE_REGISTRAZIONE = "Le credenziali da utilizzare per il primo accesso sono:";
	private static final String PATH_CREDENZIALI_DEFAULT = "resources/credenzialiDefault.json";
	private static final String PATH_CREDENZIALI_CONFIG = "resources/credenzialiConfiguratori.json";
	private static final String MSG_ERRORE_CREDENZIALI_DEFAULT = "ATTENZIONE: CREDENZIALI ERRATE";
	private static final String MSG_USERNAME_INESISTENTE ="ATTENZIONE: USERNAME NON ESISTE";
	private static final String [] TXT_VOCI = {
			MSG_REGISTRATI,
			MSG_LOGIN,
	};
	
	public static void main(String[] args) {
		
		MyMenu menuAccessoConfiguratore = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuAccessoConfiguratore.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				System.out.println(MSG_PRE_REGISTRAZIONE);
				ArrayList<Credenziali> credenziali = (ArrayList<Credenziali>) JsonIO.leggiCredenzialiDaJson(PATH_CREDENZIALI_DEFAULT);
				System.out.println(credenziali);
				
				if(GestioneAutenticazione.login(PATH_CREDENZIALI_DEFAULT, MSG_ERRORE_CREDENZIALI_DEFAULT)) { //primo accesso configuratore
					GestioneAutenticazione.registrazione(PATH_CREDENZIALI_CONFIG, PATH_CREDENZIALI_DEFAULT);
					//Una volta avvenuta la registrazione viene richiesto direttamente di fare il login
					GestioneAutenticazione.gestioneLogin(PATH_CREDENZIALI_CONFIG, MSG_USERNAME_INESISTENTE, MSG_RIPETI_LOGIN);
				}
				
				break;
			case 2:
				GestioneAutenticazione.gestioneLogin(PATH_CREDENZIALI_CONFIG, MSG_USERNAME_INESISTENTE, MSG_RIPETI_LOGIN);
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
	
	}
}
