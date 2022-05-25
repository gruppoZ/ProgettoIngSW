package gestioneScambioArticoli;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gestioneOfferte.GestioneOfferta;
import gestioneOfferte.Offerta;
import gestioneOfferte.ViewOfferte;
import gestioneParametri.GestioneParametri;
import gestioneParametri.ViewParametroGiorno;
import gestioneParametri.ViewParametroIntervalloOrario;
import gestioneParametri.ViewParametroLuogo;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewBaratto {
	private static final String MSG_ERROR_SHOW_OFFERTE = "\nNon è stato possibile mostare le offerte";
	private static final String MSG_ERRORE_INIT_GESTIONE_BARATTO = "*** ERRORE inizializzazione Gestione Baratto ***";
	private static final String MSG_ERRORE_INIT_PARAMETRI = "*** ERRORE inizializzazione Parametri ***";
	private static final String MSG_BARATTI_ASSENTI = "Nessun baratto disponibile.";
	private static final String MSG_PROPRIETARIO_APPUNTAMENTO = "\nHai fissato il seguente appuntamento:";
	private static final String MSG_PROPOSTA_APPUNTAMENTO = "\nL'autore dell'altra offerta ha fissato il seguente appuntamento:";
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
	private static final String MSG_OFFERTE_IN_SCAMBIO_ASSENTI = "Non hai offerte in scambio.";
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
	private static final String MSG_PROPONI_BARATTO = "Proponi baratto";
	private static final String MSG_CHECK_OFFERTE_SELEZIONATE = "Controlla eventuali offerte selezionate";
	private static final String MSG_CHECK_OFFERTE_IN_SCAMBIO = "Controlla eventuali offerte in scambio";
	private static final String MSG_VISUALIZZA_APPUNTAMENTO_OFFERTA = "Visualizza l'appuntamento di un offerta in scambio";
	
	private static final String [] TXT_VOCI = {
			MSG_PROPONI_BARATTO,
			MSG_CHECK_OFFERTE_SELEZIONATE,
			MSG_CHECK_OFFERTE_IN_SCAMBIO,
			MSG_VISUALIZZA_APPUNTAMENTO_OFFERTA
	};
	
	private GestioneBaratto gestoreBaratto;
	private GestioneOfferta gestoreOfferte;
	private GestioneFruitore gestoreFruitore;
	private GestioneParametri gestorePiazza;
	private ViewOfferte viewOfferta;
	
	public ViewBaratto() {
	}
	
	public ViewBaratto(GestioneOfferta gestoreOfferte, GestioneFruitore gestoreFruitore) throws IOException {
		this.gestoreOfferte = gestoreOfferte;
		this.gestoreFruitore = gestoreFruitore;
		
		try {
			this.gestoreBaratto = new GestioneBaratto();
		} catch (IOException e) {
			throw new IOException(e.getMessage() + MSG_ERRORE_INIT_GESTIONE_BARATTO);
		}
		try {
			this.gestorePiazza = new GestioneParametri();
		} catch (IOException e) {
			throw new IOException(e.getMessage() + MSG_ERRORE_INIT_PARAMETRI);
		}
		
		this.viewOfferta = new ViewOfferte(gestoreFruitore, gestoreOfferte);	
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
			case 4:
				showAppuntamentoByOfferta();
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
	}
	
	/**
	 * Fa selezionare al fruitore due offerte, una sua e una di un altro fruitore
	 * la quale però appartiene alla stessa categoria foglia.
	 * Dopodichè cambia lo stato delle due offerte e crea un baratto.
	 */
	private void scambiaArticolo() {
		Offerta offertaA = new Offerta(), offertaB = new Offerta();
		try {
			System.out.println(MSG_SCEGLI_TUA_OFFERTA);
			offertaA = viewOfferta.getOffertaById(gestoreOfferte.getOfferteAperteByUtente(gestoreFruitore.getUsername()));
			
			System.out.println("---------------------------------");
			System.out.println(MSG_SCEGLI_OFFERTA_ALTRO_FRUITORE);
			
			offertaB = viewOfferta.getOffertaById(gestoreOfferte.getOfferteAperteByCategoriaNonDiPoprietaDiUsername(offertaA.getArticolo().getFoglia(), offertaA.getUsername()));

			gestoreBaratto.switchToOfferteAccoppiate(gestoreOfferte, offertaA, offertaB);
			
			gestoreBaratto.creaBaratto(offertaA, offertaB, gestorePiazza.getScadenza());
			
			showBaratto(gestoreBaratto.getBaratto());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	/**
	 * Permette di vedere tutte le offerte selezionate di un fruitore e 
	 * di creare un appuntamento che andrà salvato nel baratto relativo all'offerta selezionata
	 */
	private void gestioneOfferteSelezionate() {
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
					LocalDate dataScadenza = gestoreBaratto.getDataScadenza(gestorePiazza, appuntamento);
					
					gestoreBaratto.switchToOfferteInScambio(gestoreOfferte, offertaAccoppiata, offertaSelezionata);
					gestoreBaratto.aggiornaBaratto(baratto, offertaAccoppiata, offertaSelezionata, dataScadenza, appuntamento);

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
	
	/**
	 * Permette di vedere tutte le offerte in scambio di un fruitore e 
	 * di accettare un appuntamento o rifiutarlo e proporne un altro
	 */
	private void gestioneOfferteInScambio() {
		List<Offerta> listaOfferteInScambio =  gestoreOfferte.getOfferteInScambioByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteInScambio.size() > 0) {
			try {				
				System.out.println(MSG_SCEGLI_TUA_OFFERTA_IN_SCAMBIO);
				
				Offerta offertaInScambioFruitoreCorrente = viewOfferta.getOffertaById(listaOfferteInScambio);
				Baratto baratto = gestoreBaratto.getBarattoByOfferta(offertaInScambioFruitoreCorrente);
				Appuntamento appuntamento = baratto.getAppuntamento(); 
				
				int idOfferta2;
				
				if(baratto.getOffertaFruitorePromotore().getId() != offertaInScambioFruitoreCorrente.getId()) 
					idOfferta2 = baratto.getOffertaFruitorePromotore().getId();	
				else
					idOfferta2 = baratto.getOffertaFruitoreRichiesta().getId();	
				
				Offerta offertaAltroFruitore = gestoreOfferte.getOffertaById(idOfferta2);
				
				showBaratto(baratto);
				
				String autoreAppuntamento = appuntamento.getUsername();
				String usernameCorrente = offertaInScambioFruitoreCorrente.getUsername();
				
				if(!usernameCorrente.equals(autoreAppuntamento)) {
					if(InputDati.yesOrNo(MSG_ASK_ACCETTARE_APPUNTAMENTO)) {
						gestoreBaratto.gestisciChiusuraBaratto(gestoreOfferte, offertaAltroFruitore, offertaAltroFruitore, baratto);
						
						System.out.println(MSG_SUCCESS_BARATTO_CONCLUSO);
					} else {
						Appuntamento nuovoAppuntamento = creaAppuntamento();
						
						while(gestoreBaratto.checkUguaglianzaAppuntamenti(nuovoAppuntamento, appuntamento)) {
							System.out.println(MSG_APPUNTAMENTO_INSERITO_UGUALE_REINSERISCI);
							nuovoAppuntamento = creaAppuntamento();
						}
						LocalDate dataScadenza = gestoreBaratto.getDataScadenza(gestorePiazza, appuntamento);
						gestoreBaratto.aggiornaBaratto(baratto, dataScadenza, nuovoAppuntamento);
					
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
	
	/**
	 * Richiede di inserire tutti i parametri per la creazione di un appuntamento 
	 * @return appuntamento creato
	 */
	private Appuntamento creaAppuntamento() {
		ViewParametroLuogo viewParametroLuogo = new ViewParametroLuogo(gestorePiazza);
		
		String luogo = viewParametroLuogo.scegliLuogo();
		
		LocalDate date = richiestaData(MSG_INSERISCI_DATA_D_M_YYYY);
		
		while(!gestorePiazza.checkValiditaGiornoSettimanaPiazzaFromLocalDate(date) || date.isBefore(LocalDate.now())) {
			date = richiestaData(MSG_GIORNO_SCELTO_NON_VALIDO);
		}
		
		ViewParametroIntervalloOrario viewParametroIntervalloOrario = new ViewParametroIntervalloOrario(gestorePiazza);

		LocalTime orario = viewParametroIntervalloOrario.scegliOrarioAppuntamento();

		Appuntamento appuntamento = new Appuntamento(luogo, date, orario, gestoreFruitore.getUsername());
		
		return appuntamento;
	}
	
	private void showAppuntamentoByOfferta() {
		ViewAppuntamento viewAppuntamento = new ViewAppuntamento();
		String username = gestoreFruitore.getUsername();
		List<Offerta> listaOfferteScambio = gestoreOfferte.getOfferteInScambioByUtente(username);
		Offerta offerta = new Offerta();
		
		try {
			offerta = viewOfferta.getOffertaById(listaOfferteScambio);			
		} catch (NullPointerException e) {
			System.out.println(MSG_OFFERTE_IN_SCAMBIO_ASSENTI);
		}
		
		try {
			Baratto baratto = gestoreBaratto.getBarattoByOfferta(offerta);
			Appuntamento appuntamento = baratto.getAppuntamento();
			
			if(appuntamento.getUsername().equals(username))
				System.out.println(MSG_PROPRIETARIO_APPUNTAMENTO);
			else
				System.out.println(MSG_PROPOSTA_APPUNTAMENTO);
			
			viewAppuntamento.showAppuntamento(appuntamento);
		} catch (NullPointerException e) {
			System.out.println(MSG_BARATTI_ASSENTI);
		}
	}
	
	private void showBaratto(Baratto baratto) throws IOException {
		StringBuffer sb = new StringBuffer();
		
		try {
			ViewOfferte viewOfferta = new ViewOfferte();
			ViewAppuntamento viewAppuntamento = new ViewAppuntamento();
			
			sb.append("**************************************\n");
			sb.append("Baratto:\n"
					+ "->Scadenza: " + baratto.getScadenza().format(DateTimeFormatter.ofPattern("dd MMMM uuuu")) + "\n");
			
			System.out.print(sb.toString());
					
			Offerta offertaA = baratto.getOffertaFruitorePromotore();
			Offerta offertaB = baratto.getOffertaFruitoreRichiesta();
			viewOfferta.showOfferta(offertaA);
			viewOfferta.showOfferta(offertaB);
			
			viewAppuntamento.showAppuntamento(baratto.getAppuntamento());
		} catch (IOException e) {
			throw new IOException(e.getMessage() + MSG_ERROR_SHOW_OFFERTE);
		}
		
	}
}
