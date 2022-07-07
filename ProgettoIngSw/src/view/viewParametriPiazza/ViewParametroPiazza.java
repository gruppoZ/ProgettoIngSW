package view.viewParametriPiazza;

import java.io.IOException;
import java.util.List;

import application.parametriPiazza.GiorniDellaSettimana;
import application.parametriPiazza.IntervalloOrario;
import application.parametriPiazza.Piazza;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewParametroPiazza extends ViewParametri {
	private static final String MSG_ERROR_SALVATAGGIO_PIAZZA_FALLITA_NO_INTERAZIONE_CON_FILE = "Impossibile salvare Piazza. Fallita interazione con il file.";
	private static final String MSG_ERROR_LETTURA_PARAMETRI_FILE = "*** ERRORE lettura parametri da file ***";
	private static final String MSG_ERROR_CREAZIONE_PIAZZA = "*** ERRORE creazione della piazza ***";
	private static final String MSG_PIAZZA_CREATA_CON_SUCCESSO = "*** Piazza creata con successo ***";	
	private static final String MSG_ASK_CITTA = "Inserisci il nome della citta': ";
	private static final String MSG_PIAZZA_INESISTENTE = "\nNessuna Piazza presente!\n";
	private static final String MSG_PIAZZA_GIA_PRESENTE = "\nPiazza già presente!\n";
	private static final String MSG_MODIFICA= "Modifica";
	
	private static final String [] TXT_VOCI_MODIFICA = {
			MSG_AGGIUNGI,
			MSG_MODIFICA,
	};
	
	private ViewParametri view;
	
	public ViewParametroPiazza() throws IOException {
		super();
	}
	
	public void menuModificaPiazza() throws IOException {		
		MyMenu menu = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menu.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				view = new ViewParametroLuogo(getGestoreParametri());
				view.menu();
				break;
			case 2:
				view = new ViewParametroGiorno(getGestoreParametri());
				view.menu();
				break;
			case 3:
				view = new ViewParametroIntervalloOrario(getGestoreParametri());
				view.menu();
				break;
			case 4:
				view = new ViewParametroScadenza(getGestoreParametri());
				view.menu();
				break;
			default:
				System.out.println(TXT_ERRORE);
				
			}
		} while(!fine);
		
	}
	
	@Override
	public void menu() throws IOException {		
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
				if(!getGestoreParametri().isPiazzaCreata()) {
					aggiungi();
					fine = true;
				} else {
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
	public void aggiungi() throws IOException {
		String citta = inserisciCitta();
		getGestoreParametri().setCitta(citta);
		
		view = new ViewParametroLuogo(getGestoreParametri());
		view.aggiungi();
		//lista luoghi
		List<String> listaLuoghi = getGestoreParametri().getLuoghi();
		
		//lista di giorni
		view = new ViewParametroGiorno(getGestoreParametri());
		view.aggiungi();
		List<GiorniDellaSettimana> giorni = getGestoreParametri().getGiorni();
		
		//listaIntervalli
		view = new ViewParametroIntervalloOrario(getGestoreParametri());
		view.aggiungi();
		List<IntervalloOrario> intervalliOrari = getGestoreParametri().getIntervalli();
		
		view = new ViewParametroScadenza(getGestoreParametri());
		view.aggiungi();
		int scadenza = getGestoreParametri().getScadenza();
		
		try {
			getGestoreParametri().creaPiazza(citta, listaLuoghi, giorni, intervalliOrari, scadenza);
			
			System.out.println(MSG_PIAZZA_CREATA_CON_SUCCESSO);
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_SALVATAGGIO_PIAZZA_FALLITA_NO_INTERAZIONE_CON_FILE);
		} catch (NullPointerException e) {
			throw new IOException(MSG_ERROR_CREAZIONE_PIAZZA);
		}		
	}

	@Override
	public void rimuovi() {
	}

	public void showPiazza() throws IOException {
		Piazza piazza;
		try {
			piazza = getGestoreParametri().getPiazza();
			ViewParametroLuogo viewParametroLuogo = new ViewParametroLuogo(getGestoreParametri());
			ViewParametroGiorno viewParametroGiorno = new ViewParametroGiorno(getGestoreParametri());
			ViewParametroIntervalloOrario viewParamtroIntervalli = new ViewParametroIntervalloOrario(getGestoreParametri());
			
			System.out.println("Piazza " + piazza.getCitta());
			viewParametroLuogo.showLuoghi();
			viewParametroGiorno.showGiorniPresenti();
			viewParamtroIntervalli.showIntervalli();
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_LETTURA_PARAMETRI_FILE);
		}
		
	}
		
	private String inserisciCitta() {
		return InputDati.leggiStringaNonVuota(MSG_ASK_CITTA);
	}
}