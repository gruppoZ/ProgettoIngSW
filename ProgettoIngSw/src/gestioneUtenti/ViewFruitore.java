package gestioneUtenti;

import gestioneOfferte.ViewArticolo;
import it.unibs.fp.mylib.MyMenu;

public class ViewFruitore extends ViewUtente{

	private static final String MSG_NESSUNA_PIAZZA = "Nessuna piazza disponibile. Creane prima una.";
	private static final String MSG_ASSENSA_GERARCHIE = "NESSUNA GERARCHIA PRESENTE";
	
	//costanti per menu
	private static final String TXT_TITOLO = "Benvenuto Fruitore";
	
	private static final String MSG_VISUALIZZA_PIAZZA = "Visualizza Piazza";
	private static final String MSG_PUBBLICA_ARTICOLO = "Inserisci un articolo";
	private static final String [] TXT_VOCI = {
			MSG_VISUALIZZA_GERARCHIE,
			MSG_VISUALIZZA_PIAZZA,
			MSG_PUBBLICA_ARTICOLO
	};
	
	@Override
	public void menu() {
		GestioneFruitore gestoreFruitore = new GestioneFruitore();
		
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
				if(!gestoreFruitore.isGerarchieCreate())
					System.out.println(MSG_ASSENSA_GERARCHIE);
				else
					System.out.println(gestoreFruitore.getGerarchie());				
				break;
			case 2:
				if(!gestoreFruitore.isPiazzaCreata())
					System.out.println(MSG_NESSUNA_PIAZZA);
				else
					System.out.println(gestoreFruitore.getPiazza());
				break;
			case 3:
				ViewArticolo viewArticolo = new ViewArticolo();
				viewArticolo.aggiungiArticolo();
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
	}
}
