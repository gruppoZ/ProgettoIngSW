package gestioneUtenti;

import gestioneCategorie.ViewGerarchia;
import gestioneParametri.ViewParametri;
import gestioneParametri.ViewParametroPiazza;
import it.unibs.fp.mylib.MyMenu;

public class ViewConfiguratore extends ViewUtente{
	
	private static final String TXT_TITOLO = "Benvenuto Configuratore";
	private static final String MSG_CREA_GERARCHIA = "Crea gerarchia";
	private static final String MSG_IMPOSTA_PARAMETRI = "Gestisci Piazza";
	
	private static final String [] TXT_VOCI = {
			MSG_CREA_GERARCHIA,
			MSG_VISUALIZZA_GERARCHIE,
			MSG_IMPOSTA_PARAMETRI,
	};
	
	@Override
	public void menu() {
		GestioneConfiguratore gestoreConfiguratore = new GestioneConfiguratore();		
		
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
				ViewGerarchia view = new ViewGerarchia();
				view.menu();
				break;
			case 2:
				System.out.println(gestoreConfiguratore.getGerarchie());
				break;
			case 3:
				ViewParametri viewPiazza = new ViewParametroPiazza();
				viewPiazza.menu();
				break;
			default:
				System.out.println(TXT_ERRORE);	
			}
		} while(!fine);
	}
	
}
