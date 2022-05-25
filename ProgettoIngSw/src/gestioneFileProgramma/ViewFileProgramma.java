package gestioneFileProgramma;

import java.io.IOException;

import gestioneCategorie.GestioneGerarchie;
import gestioneParametri.GestioneParametri;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewFileProgramma {
	private static final String MSG_ASK_PATH_PIAZZA = "Inserisci un path valido per il file JSON dei Parametri della Piazza da importare: ";
	private static final String MSG_ASK_PATH_GERARCHIE = "Inserisci un path valido per il file JSON delle Gerarchie da importare: ";
	private static final String TXT_TITOLO = "Gestione di Sistema";
	private static final String TXT_ERRORE = "ERRORE";
	private static final String MSG_IMPORTA_GERARCHIE = "Importa Gerarchie";
	private static final String MSG_IMPORTA_PARAMETRI = "Importa Parametri";
	private static final String MSG_SUCCESS_IMPORT_COMPLETATO = "\nImport dati eseguito correttamente.";	
	private static final String MSG_ERRORE_IMPORT = "*** Import non eseguito. Verifica di avere specificato un url corretto per un file valido ***";
	
	private static final String [] TXT_VOCI = {
			MSG_IMPORTA_GERARCHIE,
			MSG_IMPORTA_PARAMETRI
	};
	
	public void menu() {	
		MyMenu menuConfiguratore = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuConfiguratore.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				importaGerarchie();
				break;
			case 2:
				importaPiazza();
				break;
			default:
				System.out.println(TXT_ERRORE);	
			}
		} while(!fine);
	}

	private void importaPiazza() {
		try {
			GestioneParametri gestoreParametri = new GestioneParametri();
			
			String path = InputDati.leggiStringaNonVuota(MSG_ASK_PATH_PIAZZA);
			
			gestoreParametri.importaParametri(path);
			
			System.out.println(MSG_SUCCESS_IMPORT_COMPLETATO);
			
		} catch (IOException e) {
			System.out.println(MSG_ERRORE_IMPORT);
		}
	}

	private void importaGerarchie() {
		try {
			GestioneGerarchie gestoreGerarchie = new GestioneGerarchie();
			
			String path = InputDati.leggiStringaNonVuota(MSG_ASK_PATH_GERARCHIE);
			
			gestoreGerarchie.importaGerarchie(path);
			
			System.out.println(MSG_SUCCESS_IMPORT_COMPLETATO);			
		} catch (IOException e) {
			System.out.println(MSG_ERRORE_IMPORT);
		}
	}
}
