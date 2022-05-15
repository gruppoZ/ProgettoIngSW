package gestioneScambioArticoli;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.OffertaInScambio;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.GestioneParametri;
import gestioneParametri.ViewParametroGiorno;
import gestioneParametri.ViewParametroIntervalloOrario;
import gestioneParametri.ViewParametroLuogo;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewBaratto {
	private static final String MSG_FORMATO_GIORNO_NON_VALIDO = "Formato giorno inserito non valido!";
	private static final String MSG_SUCCESS_BARATTO_CONCLUSO = "*** Baratto concluso correttamente ***";
	private static final String MSG_SUCCESS_DATI_INSERITI = "*** Dati inseriti correttamente ***";
	private static final String MSG_ASK_ACCETTARE_APPUNTAMENTO = "Vuoi accettare l'appuntamento?";
	private static final String MSG_APPUNTAMENTO_INSERITO_UGUALE_REINSERISCI = "Hai inserito un appuntamento uguale a quello proposto dall'altro fruitore.\n"
			+ "Reinserire l'appuntamento.";
	private static final String MSG_INSERISCI_DATA_D_M_YYYY = "Inserisci data nel formato d/m/yyyy:";
	private static final String MSG_GIORNO_SCELTO_NON_VALIDO = "Giorno non accettato. Ricorda "
			+ "di scegliere una data in un giorno della settimana fra quelli disponibili per gli appuntamenti."
			+ "\n\nInserisci data nel formato d/m/yyyy:";
	private static final String MSG_ATTENDI_RISPOSTA_ALTRO_FRUITORE = "\nDevi attendere una risposta dall'altro fruitore!";
	private static final String MSG_OFFERTE_IN_SCAMBIO_ASSENTI = "Non hai offerte in scambio";
	private static final String MSG_SCEGLI_TUA_OFFERTA_IN_SCAMBIO = "\nSeleziona una delle tue offerte in scambio: ";
	private static final String MSG_OFFERTE_SELEZIONATE_ASSENTI = "Non hai offerte selezionate";
	private static final String MSG_WARNING_FISSARE_APPUNTAMENTO_ENTRO_SCADENZA = "Attenzione! Se non viene fissato un apputnamento entro: %s il baratto verra' cancellato!\n";
	private static final String MSG_ASK_FISSARE_APPUNTAMENTO = "Vuoi fissare un appuntamento?";
	private static final String MSG_SCEGLI_TUA_OFFERTA_SELEZIONATA = "\nSeleziona una delle tue offerte selezionate: ";
	private static final String MSG_SCEGLI_OFFERTA_ALTRO_FRUITORE = "\nSeleziona un'offerta tra le offerte degli altri fruitori: ";
	private static final String MSG_SCEGLI_TUA_OFFERTA = "\nSeleziona una delle tue offerte: ";
	//costanti per menu
	protected static final String TXT_ERRORE = "ERRORE";
	private static final String TXT_TITOLO = "Scambio Articoli";
	
	private static final String MSG_SCEGLI_OFFERTE_APERTE = "Scambia Articolo";
	private static final String MSG_CHECK_OFFERTE_SELEZIONATE = "Controlla eventuali offerte selezionate";
	private static final String MSG_CHECK_OFFERTE_IN_SCAMBIO = "Controlla eventuali offerte in scambio";
	
	private static final String [] TXT_VOCI = {
			MSG_SCEGLI_OFFERTE_APERTE,
			MSG_CHECK_OFFERTE_SELEZIONATE,
			MSG_CHECK_OFFERTE_IN_SCAMBIO
	};
	
	private GestioneBaratto gestoreBaratto;
	
	private GestioneOfferta gestoreOfferte;
	private GestioneFruitore gestoreFruitore;
	private GestioneParametri gestorePiazza;
	
	public ViewBaratto() {
	}
	
	public ViewBaratto(GestioneOfferta gestoreOfferte, GestioneFruitore gestoreFruitore) {
		this.gestoreOfferte = gestoreOfferte;
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
			case 2:
				gestioneOfferteSelezionate();
				break;
			case 3:
				gestioneOfferteInScambio();
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
		ViewOfferte viewOfferta = new ViewOfferte(gestoreFruitore, gestoreOfferte); //TODO: va creata a livello di classe (?)
		Offerta offertaA = new Offerta(), offertaB = new Offerta();
		try {
			System.out.println(MSG_SCEGLI_TUA_OFFERTA);
			offertaA = viewOfferta.getOffertaById(gestoreOfferte.getOfferteAperteByUtente(gestoreFruitore.getUsername()));
			
			System.out.println("---------------------------------");
			System.out.println(MSG_SCEGLI_OFFERTA_ALTRO_FRUITORE);
			
			offertaB = viewOfferta.getOffertaById(gestoreOfferte.getOfferteAperteByCategoriaNonDiPoprietaDiUsername(offertaA.getArticolo().getFoglia(), offertaA.getUsername()));

			gestoreBaratto.creaCollegamento(gestoreOfferte, offertaA, offertaB);
			
			gestoreBaratto.creaBaratto(offertaA, offertaB, gestorePiazza.getScadenza());
			
			showBaratto(gestoreBaratto.getBaratto());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	
	private void gestioneOfferteSelezionate() {
		ViewOfferte viewOfferta = new ViewOfferte(gestoreFruitore, gestoreOfferte);
		List<Offerta> listaOfferteSelezionate =  gestoreOfferte.getOfferteSelezionateByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteSelezionate.size() > 0) {
			try {
				System.out.println(MSG_SCEGLI_TUA_OFFERTA_SELEZIONATA);
				Offerta offertaSelezionata = viewOfferta.getOffertaById(listaOfferteSelezionate);
				Baratto baratto = gestoreBaratto.getBarattoByOffertaSelezionata(offertaSelezionata);
				Offerta offertaAccoppiata = gestoreOfferte.getOffertaById(baratto.getOffertaFruitorePromotore().getId());

				showBaratto(baratto);
				
				boolean scelta = InputDati.yesOrNo(MSG_ASK_FISSARE_APPUNTAMENTO);
				if(scelta) {
					Appuntamento appuntamento = creaAppuntamento();
					gestoreBaratto.creaScambio(gestoreOfferte, offertaAccoppiata, offertaSelezionata, appuntamento);
					gestoreBaratto.rimuoviBaratto(baratto);
					gestoreBaratto.creaBaratto(offertaAccoppiata, offertaSelezionata, gestorePiazza.getScadenza());
					System.out.println(MSG_SUCCESS_DATI_INSERITI);
				} else {
					System.out.printf(MSG_WARNING_FISSARE_APPUNTAMENTO_ENTRO_SCADENZA, baratto.getScadenza());
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		} else {
			System.out.println(MSG_OFFERTE_SELEZIONATE_ASSENTI);
		}
		
	}
	
	//TODO: cambiare i nomi "offerta1 e offerta2" con tipo offertaCorrente e offertaAltroFruitore bo ma io che cazzo ne so
	private void gestioneOfferteInScambio() {
		ViewOfferte viewOfferta = new ViewOfferte(gestoreFruitore, gestoreOfferte);
		List<Offerta> listaOfferteInScambio =  gestoreOfferte.getOfferteInScambioByUtente(gestoreFruitore.getUsername());
		ViewAppuntamento viewAppuntamento = new ViewAppuntamento();
		
		if(listaOfferteInScambio.size() > 0) {
			try {				
				System.out.println(MSG_SCEGLI_TUA_OFFERTA_IN_SCAMBIO);
				
				Offerta offertaInScambioFruitoreCorrente = viewOfferta.getOffertaById(listaOfferteInScambio);
				Baratto baratto = gestoreBaratto.getBarattoByOfferta(offertaInScambioFruitoreCorrente);
				
				int idOfferta2;
				
				if(baratto.getOffertaFruitorePromotore().getId() != offertaInScambioFruitoreCorrente.getId()) 
					idOfferta2 = baratto.getOffertaFruitorePromotore().getId();	
				else
					idOfferta2 = baratto.getOffertaFruitoreRichiesta().getId();	
				
				Offerta offertaAltroFruitore = gestoreOfferte.getOffertaById(idOfferta2);
				
				showBaratto(baratto);
				
				OffertaInScambio offertaInScambioAltroFruitore = (OffertaInScambio) offertaAltroFruitore.getStatoOfferta();
				
				if(offertaInScambioAltroFruitore.getAppuntamento().isValido()) {
					viewAppuntamento.showAppuntamento(offertaInScambioAltroFruitore.getAppuntamento());
					
					if(InputDati.yesOrNo(MSG_ASK_ACCETTARE_APPUNTAMENTO)) {
						gestoreBaratto.cambioOfferteChiuse(gestoreOfferte, offertaInScambioFruitoreCorrente, offertaAltroFruitore);
						gestoreBaratto.rimuoviBaratto(baratto);
						
						System.out.println(MSG_SUCCESS_BARATTO_CONCLUSO);
					} else {
						Appuntamento appuntamento = creaAppuntamento();
						
						while(gestoreBaratto.checkUguaglianzaAppuntamenti(appuntamento, offertaInScambioAltroFruitore.getAppuntamento())) {
							System.out.println(MSG_APPUNTAMENTO_INSERITO_UGUALE_REINSERISCI);
							appuntamento = creaAppuntamento();
						}
						
						OffertaInScambio tipoOfferta1 = (OffertaInScambio) offertaInScambioFruitoreCorrente.getStatoOfferta();
						
						gestoreBaratto.gestisciRifiutoAppuntamento(gestoreOfferte, tipoOfferta1, offertaInScambioAltroFruitore, appuntamento);
						gestoreBaratto.rimuoviBaratto(baratto);
						gestoreBaratto.creaBaratto(offertaAltroFruitore, offertaInScambioFruitoreCorrente, gestorePiazza.getScadenza());
					
						System.out.println(MSG_SUCCESS_DATI_INSERITI);
					}
				} else {
					System.out.println(MSG_ATTENDI_RISPOSTA_ALTRO_FRUITORE);
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		} else {
			System.out.println(MSG_OFFERTE_IN_SCAMBIO_ASSENTI);
		}
		
	}
	
	private LocalDate richiestaData(String msg) {
		ViewParametroGiorno viewParametroGiorno = new ViewParametroGiorno(gestorePiazza);
		boolean formatoDateValido = false;
		String dateTesto = null;
		LocalDate date = null;
		
		do {
			try {
				viewParametroGiorno.showGiorniPresenti();
				dateTesto = InputDati.leggiStringaNonVuota(msg);
				date = gestorePiazza.dateInput(dateTesto);
				formatoDateValido = false;
			} catch (DateTimeException e) {
				System.out.println(MSG_FORMATO_GIORNO_NON_VALIDO);
				formatoDateValido = true;
			}
		}while(formatoDateValido);
		
		return date;
	}
	
	private Appuntamento creaAppuntamento() {
		ViewParametroLuogo viewParametroLuogo = new ViewParametroLuogo(gestorePiazza);
		
		String luogo = viewParametroLuogo.scegliLuogo();
		
		LocalDate date = richiestaData(MSG_INSERISCI_DATA_D_M_YYYY);
		
		while(!gestorePiazza.checkValiditaGiornoSettimanaPiazzaFromLocalDate(date) || date.isBefore(LocalDate.now())) {
			
			date = richiestaData(MSG_GIORNO_SCELTO_NON_VALIDO);
		}
		
		ViewParametroIntervalloOrario viewParametroIntervalloOrario = new ViewParametroIntervalloOrario(gestorePiazza);

		LocalTime orario = viewParametroIntervalloOrario.scegliOrarioAppuntamento();
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
		Appuntamento appuntamento = new Appuntamento(luogo, date, orario);
		
		System.out.println();
		
		return appuntamento;
	}
	
	private void showBaratto(Baratto baratto) {
		StringBuffer sb = new StringBuffer();
		ViewOfferte viewOfferta = new ViewOfferte();
		
		sb.append("**************************************\n");
		sb.append("Baratto:\n"
				+ "->Scadenza: " + baratto.getScadenza().format(DateTimeFormatter.ofPattern("dd MMMM uuuu")) + "\n"
//				+ "-> Appuntamento\n"
//				+ "\t Luogo: " + baratto.getAppuntamento().getLuogo() + "\n"
//				+ "\t Data: " + baratto.getAppuntamento().getData() + "\n"
//				+ "\t Ora: " + baratto.getAppuntamento().getOra() + "\n\n"
 				);
		
		System.out.print(sb.toString());
		
		Offerta offertaA = baratto.getOffertaFruitorePromotore();
		Offerta offertaB = baratto.getOffertaFruitoreRichiesta();
		viewOfferta.showOfferta(offertaA);
		viewOfferta.showOfferta(offertaB);
	}
}
