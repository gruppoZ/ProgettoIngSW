package gestioneUtenti;

import gestioneCategorie.ViewGerarchia;
import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.ViewParametroPiazza;
import it.unibs.fp.mylib.MyMenu;

public class ViewFruitore extends ViewUtente{

	private static final String MSG_NESSUNA_PIAZZA = "Nessuna piazza disponibile. Creane prima una.";
	
	//costanti per menu
	private static final String TXT_TITOLO = "Benvenuto Fruitore";
	private static final String MSG_VISUALIZZA_PIAZZA = "Visualizza Piazza";
	private static final String MSG_GESTISCI_OFFERTE = "Gestisci Offerte";
	
	private static final String [] TXT_VOCI = {
			MSG_VISUALIZZA_GERARCHIE,
			MSG_VISUALIZZA_PIAZZA,
			MSG_GESTISCI_OFFERTE
	};
	
	@Override
	public void menu(String username) {
		GestioneFruitore gestoreFruitore = new GestioneFruitore(username);
		GestioneOfferta gestoreOfferta = new GestioneOfferta();
		
		MyMenu menuFruitore = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuFruitore.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				ViewGerarchia viewGerarchia = new ViewGerarchia();
				
				if(!gestoreFruitore.isGerarchieCreate())
					System.out.println(MSG_ASSENZA_GERARCHIE);
				else {
					gestoreFruitore.getGerarchie().forEach((k,v) -> {
						viewGerarchia.showGerarchiaSintetica(v);
					});
				}		
				break;
			case 2:
				if(!gestoreFruitore.isPiazzaCreata())
					System.out.println(MSG_NESSUNA_PIAZZA);
				else {
					ViewParametroPiazza viewPiazza = new ViewParametroPiazza();
					viewPiazza.showPiazza();
				}
				break;
			case 3:
				ViewOfferte viewOfferte = new ViewOfferte(gestoreFruitore, gestoreOfferta);
				viewOfferte.menu();
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
	}
}
