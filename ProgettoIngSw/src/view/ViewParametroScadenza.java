package view;

import java.io.IOException;

import controller.GestioneParametri;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroScadenza extends ViewParametri {

	private static final String MSG_ERROR_MODIFICA_SCADENZA_FALLITA_NO_INTERAZIONE_CON_FILE = "Impossibile modificare la Scadenza. Fallita interazione con il file.";

	private static final int MIN_SCADENZA = 1;
	
	private static final String GIVE_SCADENZA_ATTUALE = "Scadenza attuale: %s giorni\n";
	private static final String ASK_SCADENZA = "\nInserisci la nuova scadenza: ";
	
	private static final String MSG_MODIFICA= "Modifica";
	
	private static final String [] TXT_VOCI_MODIFICA = {
			MSG_MODIFICA,
	};
	
	/**
	 * Precondizione: gestoreParametri != null
	 * 
	 * @param gestoreParametri
	 */
	public ViewParametroScadenza(GestioneParametri gestoreParametri) {
		super(gestoreParametri);
	}
	
	@Override
	public void menu() throws IOException {
		MyMenu menu = new MyMenu(TXT_TITOLO, TXT_VOCI_MODIFICA);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menu.scegli();
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
	public void aggiungi() throws IOException {
		showScadenza();
		
		int scadenza = inserisciScadenza();
	
		try {
			getGestoreParametri().modificaScadenza(scadenza);
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_MODIFICA_SCADENZA_FALLITA_NO_INTERAZIONE_CON_FILE);
		}
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
