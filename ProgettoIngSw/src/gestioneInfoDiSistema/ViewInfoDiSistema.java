package gestioneInfoDiSistema;

import gestioneCategorie.GestioneGerarchie;
import gestioneParametri.GestioneParametri;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewInfoDiSistema {
	private static final String TXT_TITOLO = "Gestione di Sistema";
	private static final String TXT_ERRORE = "ERRORE";
	private static final String MSG_IMPORTA_GERARCHIE = "Importa Gerarchie";
	private static final String MSG_IMPORTA_PARAMETRI = "Importa Parametri";
	
	private static final String [] TXT_VOCI = {
			MSG_IMPORTA_GERARCHIE,
			MSG_IMPORTA_PARAMETRI
	};
	
	public void menu() {	
		String path;
		
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
				GestioneGerarchie gestoreGerarchie = new GestioneGerarchie();
				path = askPathGerarchie();
				
				gestoreGerarchie.importaGerarchie(path);
				break;
			case 2:
				GestioneParametri gestoreParametri = new GestioneParametri();
				path = askPathParametri();
				
				gestoreParametri.importaParametri(path);
				break;
			default:
				System.out.println(TXT_ERRORE);	
			}
		} while(!fine);
	}
	
	private String askPathGerarchie() {	
		String path;
		path = InputDati.leggiStringaNonVuota("Inserisci un path valido per il file JSON delle Gerarchie da importare: ");
		
		return path;
	}
	
	private String askPathParametri() {	
		String path;
		path = InputDati.leggiStringaNonVuota("Inserisci un path valido per il file JSON dei Parametri della Piazza da importare: ");
		
		return path;
	}
}
