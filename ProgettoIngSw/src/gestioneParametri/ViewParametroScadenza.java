package gestioneParametri;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroScadenza extends ViewParametri{

	private static final int MIN_SCADENZA = 1;
	
	private static final String GIVE_SCADENZA_ATTUALE = "Scadenza attuale: %s giorni\n";
	private static final String ASK_SCADENZA = "Inserisci la scadenza: ";
	
	private static final String MSG_MODIFICA= "Modifica";
	
	private static final String [] TXT_VOCI_MODIFICA = {
			MSG_MODIFICA,
	};
	
	public ViewParametroScadenza(GestioneParametri gestoreParametri) {
		super(gestoreParametri);
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
				aggiungi();
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
	}

	@Override
	public void aggiungi() {
		showScadenza();
		System.out.println("Modifica...");
		
		int scadenza = inserisciScadenza();
	
		getGestoreParametri().modificaScadenza(scadenza);
	}

	@Override
	public void rimuovi() {
	}

	private int inserisciScadenza() {
		return InputDati.leggiInteroConMinimo(ASK_SCADENZA, MIN_SCADENZA);
	}
	
	private void showScadenza() {
		System.out.printf(GIVE_SCADENZA_ATTUALE, getGestoreParametri().getScadenza());
	}
}
