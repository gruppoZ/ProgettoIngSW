package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import gestioneLogin.ViewAuth;
import gestioneLogin.ViewAuthConfiguratore;
import gestioneLogin.ViewAuthFruitore;
import it.unibs.fp.mylib.MyMenu;

public class SchermataPrincipale {
	//menu configuratore
	private static final String TXT_ERRORE = "ERRORE";
	private static final String TXT_TITOLO = "Benvenuto";
	private static final String MSG_REGISTRATI = "Registrati";
	private static final String MSG_LOGIN = "Login";
	private static final String [] TXT_VOCI = {
			MSG_REGISTRATI,
			MSG_LOGIN,
	};
	
	//menu scelta utente
	private static final String TXT_TITOLO_UTENTE = "Benvenuto";
	private static final String TXT_ERRORE_UTENTE = "ERRORE";
	private static final String MSG_FRUITORE ="Area fruitori";
	private static final String MSG_CONFIGURATORE ="Area configuratori";
	private static final String [] TXT_VOCI_UTENTE = {
			MSG_FRUITORE,
			MSG_CONFIGURATORE,
	};
	
	public void init() throws FileNotFoundException, IOException {
		MyMenu menuSceltaUtente = new MyMenu(TXT_TITOLO_UTENTE, TXT_VOCI_UTENTE);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuSceltaUtente.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				menuAccesso(new ViewAuthFruitore());
				break;
			case 2:
				menuAccesso(new ViewAuthConfiguratore());
				break;
			default:
				System.out.println(TXT_ERRORE_UTENTE);				
			}
		} while(!fine);
	}
	
	private void menuAccesso(ViewAuth auth) throws FileNotFoundException, IOException {
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
				auth.registrati();
				break;
			case 2:
				auth.login();
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
	}
}
