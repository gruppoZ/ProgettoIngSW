package gestioneUtenti;

import gestioneOfferte.ViewArticolo;
import gestioneOfferte.ViewOfferte;
import it.unibs.fp.mylib.MyMenu;

public class ViewFruitore extends ViewUtente{

	private static final String MSG_NESSUNA_PIAZZA = "Nessuna piazza disponibile. Creane prima una.";
	private static final String MSG_ASSENZA_GERARCHIE = "NESSUNA GERARCHIA PRESENTE";
	
	//costanti per menu
	private static final String TXT_TITOLO = "Benvenuto Fruitore";
	
	private static final String MSG_VISUALIZZA_PIAZZA = "Visualizza Piazza";
	private static final String MSG_PUBBLICA_ARTICOLO = "Inserisci un articolo";
	private static final String MSG_OFFERTA_RITIRATA = "Ritira un offerta";
	private static final String MSG_OFFERTE_APERTE = "Visualizzare tutte le attuali Offerte aperte relative ad una categoria";
	private static final String MSG_OFFERTE_AUTORE = "Visualizzare tutte le tue Offerte aperte e ritirate ";

	private static final String [] TXT_VOCI = {
			MSG_VISUALIZZA_GERARCHIE,
			MSG_VISUALIZZA_PIAZZA,
			MSG_PUBBLICA_ARTICOLO,
			MSG_OFFERTA_RITIRATA,
			MSG_OFFERTE_APERTE,
			MSG_OFFERTE_AUTORE
	};
	
	@Override
	public void menu(String username) {
		GestioneFruitore gestoreFruitore = new GestioneFruitore(username);
		
		ViewOfferte viewOfferte;//per ora qua perchè tre case lo usano

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
					System.out.println(MSG_ASSENZA_GERARCHIE);
				else
					System.out.println(gestoreFruitore.getDescrizioneSinteticaGerarchie());				
				break;
			case 2:
				if(!gestoreFruitore.isPiazzaCreata())
					System.out.println(MSG_NESSUNA_PIAZZA);
				else
					System.out.println(gestoreFruitore.getPiazza());
				break;
			case 3:
				ViewArticolo viewArticolo = new ViewArticolo(gestoreFruitore);
				viewArticolo.aggiungiArticolo();
				break;
			//possibile raggrupare case 4,5,6 in un unico menu(?)
			case 4:
				viewOfferte = new ViewOfferte();
				viewOfferte.ritiraOfferta(gestoreFruitore);
				break;
			case 5:
				viewOfferte = new ViewOfferte();
				viewOfferte.showOfferteAperteByCategoria();
				break;
			case 6:
				viewOfferte = new ViewOfferte();
				viewOfferte.showOfferteByName(gestoreFruitore);
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
	}
}
