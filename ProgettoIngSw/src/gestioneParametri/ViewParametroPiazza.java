package gestioneParametri;

import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroPiazza extends ViewParametri {

	private static final String ASK_CITTA = "Inserisci il nome della citta': ";
	private static final String ASK_SCADENZA = "Inserisci la scadenza: ";
	
	private static final String MSG_PIAZZA_INESISTENTE = "\nNessuna Piazza presente!\n";
	private static final String MSG_PIAZZA_GIA_PRESENTE = "\nPiazza già presente!\n";
	private static final String MSG_MODIFICA= "Modifica";
	
	private static final String [] TXT_VOCI_MODIFICA = {
			MSG_AGGIUNGI,
			MSG_MODIFICA,
	};
	
	private ViewParametri view;
	
	public void menuModificaPiazza() {		
		MyMenu menuModificaPiazza = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuModificaPiazza.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				view = new ViewParametroLuogo();
				view.menu();
				break;
			case 2:
				view = new ViewParametroGiorno();
				view.menu();
				break;
			case 3:
				view = new ViewParametroIntervalloOrario();
				view.menu();
				break;
			case 4:
//				gestoreParametri.showScadenza();
//				gestoreParametri.modificaScadenza(leggiScadenza());
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
	}
	
	@Override
	public void menu() {		
		MyMenu menuModificaPiazza = new MyMenu(TXT_TITOLO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuModificaPiazza.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				if(!getGestoreParametri().isPiazzaCreata())
					aggiungi();
				else {
					System.out.println(MSG_PIAZZA_GIA_PRESENTE);
					showPiazza();
				}
				break;
			case 2:
				if(getGestoreParametri().isPiazzaCreata())
					menuModificaPiazza();
				else
					System.out.println(MSG_PIAZZA_INESISTENTE);
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
	}
	
	@Override
	public void aggiungi() {
		String citta = inserisciCitta();
		
		view = new ViewParametroLuogo();
		view.aggiungi();
		//lista luoghi
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();
		
		//lista di giorni
		view = new ViewParametroGiorno();
		view.aggiungi();
		List<GiorniDellaSettimana> giorni = getGestoreParametri().getGiorni();
		//List<GiorniDellaSettimana> giorniOrdinati = ordinaListaGiorni(giorni); //ordinare la lista dei giorni presenti in piazza
		
		//listaIntervalli
		view = new ViewParametroIntervalloOrario();
		view.aggiungi();
		List<IntervalloOrario> intervalliOrari = getGestoreParametri().getIntervalli();
		
		int scadenza = inserisciScadenza();
		
		getGestoreParametri().creaPiazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
		
	}

	@Override
	public void rimuovi() {
	}

	private void showPiazza() {
		System.out.println(getGestoreParametri().getPiazza().toString());
	}
	
	private int inserisciScadenza() {
		return InputDati.leggiInteroConMinimo(ASK_SCADENZA, 0);
	}
	
	private String inserisciCitta() {
		return InputDati.leggiStringaNonVuota(ASK_CITTA);
	}
}