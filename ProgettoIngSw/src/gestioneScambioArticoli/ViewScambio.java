package gestioneScambioArticoli;

import java.sql.Date;
import java.time.LocalDate;

import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaAccoppiata;
import gestioneOfferte.OffertaSelezionata;
import gestioneOfferte.ViewArticolo;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.GestioneParametri;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewScambio {
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
	
	public ViewScambio() {
	}
	
	public ViewScambio(GestioneOfferta gestoreOfferta, GestioneFruitore gestoreFruitore) {
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
		 * Scegli un'offerta di tua proprietÓ
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

			gestoreBaratto.creaCollegamento(offertaA, offertaB);
			
			gestoreBaratto.creaBaratto(offertaA, offertaB, gestorePiazza.getScadenza());
			
			showBaratto(gestoreBaratto.getBaratto());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	private void showBaratto(Baratto baratto) {
		StringBuffer sb = new StringBuffer();
		ViewOfferte viewOfferta = new ViewOfferte();
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
