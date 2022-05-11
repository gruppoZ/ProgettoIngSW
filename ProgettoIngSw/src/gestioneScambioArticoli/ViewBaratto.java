package gestioneScambioArticoli;

import java.time.LocalDate;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.GestioneParametri;
import gestioneParametri.GiorniDellaSettimana;
import gestioneParametri.ViewParametroGiorno;
import gestioneParametri.ViewParametroLuogo;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewBaratto {
	//costanti per menu
	protected static final String TXT_ERRORE = "ERRORE";
	private static final String TXT_TITOLO = "Scambio Articoli";
	
	private static final String MSG_SCEGLI_OFFERTE_APERTE = "Scambia Articolo";
	private static final String MSG_MESSAGGI_RICEVUTI = "Visualizza messaggi ricevuti";
	
	private static final String [] TXT_VOCI = {
			MSG_SCEGLI_OFFERTE_APERTE,
			MSG_MESSAGGI_RICEVUTI
	};
	
	private GestioneBaratto gestoreBaratto;
	
	private GestioneOfferta gestoreOfferta;
	private GestioneFruitore gestoreFruitore;
	private GestioneParametri gestorePiazza;
	
	public ViewBaratto() {
	}
	
	public ViewBaratto(GestioneOfferta gestoreOfferta, GestioneFruitore gestoreFruitore) {
		this.gestoreOfferta = gestoreOfferta;
		this.gestoreFruitore = gestoreFruitore;
		this.gestoreBaratto = new GestioneBaratto();
		this.gestorePiazza = new GestioneParametri();
	}
	
	public void menu() {	
		MyMenu viewScambio = new MyMenu(TXT_TITOLO, TXT_VOCI);
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = viewScambio.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				scambiaArticolo();
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
	}
	
	private void scambiaArticolo() {
		/**
		 * Scegli un'offerta di tua proprietà
		 * visualizza tutte le offerte aperte appartenenti alla stessa categoria di un'altro fruitore
		 * Scegli offerta desiderata
		 * Conferma
		 * 	-> Cambia stato offerta fruitoreA in Offerta ACCOPPIATA
		 * 	-> Cambia stato offerta fruitoreB in offerta SELEZIONATA
		 * 	-> Collega le 2 offerte
		 * 
		 */
		ViewOfferte viewOfferta = new ViewOfferte(gestoreFruitore);
		Offerta offertaA = new Offerta(), offertaB = new Offerta();
		try {
			System.out.println("\nSeleziona una delle tue offerte: ");
			offertaA = viewOfferta.getOffertaById(gestoreOfferta.getOfferteAperteByUtente(gestoreFruitore.getUsername()));
			
			System.out.println("---------------------------------");
			System.out.println("\nSeleziona un'offerta tra le offerte degli altri fruitori: ");
			
			offertaB = viewOfferta.getOffertaById(gestoreOfferta.getOfferteAperteByCategoriaNonDiPoprietaDiUsername(offertaA.getArticolo().getFoglia(), offertaA.getUsername()));

			gestoreBaratto.creaCollegamento(gestoreOfferta, offertaA, offertaB);
			
			gestoreBaratto.creaBaratto(offertaA, offertaB, gestorePiazza.getScadenza());
			
			//TODO: da finire manca la scelta dell'ora. Appuntamento non va creato qui!
			Appuntamento appuntamento = creaAppuntamento();
			gestoreBaratto.getBaratto().setAppuntamento(appuntamento);
			
			showBaratto(gestoreBaratto.getBaratto());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	private Appuntamento creaAppuntamento() {
		ViewParametroLuogo viewParametroLuogo = new ViewParametroLuogo(gestorePiazza);
		
		String luogo = viewParametroLuogo.scegliLuogo();
		
		System.out.println("Giorni: " + gestorePiazza.getGiorni()); 
		String dateTesto = InputDati.leggiStringaNonVuota("Inserisci data nel formato d/m/yyyy:");
		LocalDate date = gestorePiazza.dateInput(dateTesto);
		
		while(!gestorePiazza.checkValiditaGiornoSettimanaPiazzaFromLocalDate(date)) {
			//TODO: da fare refactor
			System.out.println("Giorni: " + gestorePiazza.getGiorni()); 
			
			dateTesto = InputDati.leggiStringaNonVuota("Giorno non accettato. Ricorda"
					+ "di scegliere una data in un giorno della settimana fra quelli disponibili per gli appuntamenti."
					+ "\n\nInserisci data nel formato d/m/yyyy:");
			date = gestorePiazza.dateInput(dateTesto);
		}
		
		/**TODO:
		 * Data è LocalDate => bisogna gestire il fatto che la data scelta abbia come giorno della settimana uno valido
		 * Es. 11/05/2022 -> Mercoledi -> Mercoledì è uno dei giorni della settimana prefissato? Se si, ok.
		 * Altrimenti giorno non valido -> Scegli un'altro giorno
		 * ----------------
		 * Per Orario invece:
		 * “Intervalli orari”: 17.00-19.30
		 * "Si noti che l’intervallo orario sopra esemplificato implica che gli appuntamenti possano
		 *	essere fissati (solo) alle ore 17.00, 17.30, 18.00, 18.30, 19.00 e 19.30."
		 */
		Appuntamento appuntamento = new Appuntamento(luogo, date, null);
		
		return appuntamento;
	}
	
	private void showBaratto(Baratto baratto) {
		StringBuffer sb = new StringBuffer();
		ViewOfferte viewOfferta = new ViewOfferte();
		
		sb.append("**************************************\n");
		sb.append("Baratto:\n"
				+ "->Scadenza: " + baratto.getScadenza() + "\n"
				+ "-> Appuntamento\n"
				+ "\t Luogo: " + baratto.getAppuntamento().getLuogo() + "\n"
				+ "\t Data: " + baratto.getAppuntamento().getData() + "\n"
				+ "\t Ora: " + baratto.getAppuntamento().getOra() + "\n\n"
 				);
		
		System.out.print(sb.toString());
		
		Offerta offertaA = baratto.getOffertaA();
		Offerta offertaB = baratto.getOffertaB();
		viewOfferta.showOfferta(offertaA);
		viewOfferta.showOfferta(offertaB);
	}
}
