package gestioneUtenti;

import gestioneCategorie.ManagerGerarchie;
import it.unibs.fp.mylib.MyMenu;

public class GestioneConfiguratore {

	private static final String TXT_TITOLO = "Benvenuto Configuratore";
	private static final String TXT_ERRORE = "ERRORE";
	private static final String MSG_CREA_GERARCHIA = "Crea gerarchia";
	private static final String MSG_LETTURA_GERARCHIE = "Visualizza tutte le gerarchie";
	private static final String [] TXT_VOCI = {
			MSG_CREA_GERARCHIA,
			MSG_LETTURA_GERARCHIE,
	};
	
	public static void menuConfiguratore() {
		ManagerGerarchie manager = new ManagerGerarchie();
		manager.leggiDaFileGerarchie();
		
		
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
		        boolean isGerarchiaCreata = manager.creaGerarchia();
		        
		        if(isGerarchiaCreata)
		        	manager.salvaGerarchie();
		       
				break;
			case 2:
				System.out.println(manager.toString());
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
	}
	
}
