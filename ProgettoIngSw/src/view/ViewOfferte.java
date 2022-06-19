package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Categoria;
import application.baratto.Offerta;
import application.baratto.OffertaRitirata;
import controller.GestioneFruitore;
import controller.GestioneOfferta;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewOfferte {

	private static final String MSG_ERROR_FILE_INERENTI_IL_BARATTO = "Impossibile interagire con i file inerenti il baratto.";
	private static final String MSG_ERROR_FILE_INERENTI_IL_BARATTO_NON_TROVATI = "Impossibile interagire con i file inerenti il baratto. File non trovati";
	
	private static final String MSG_SHOW_OFFERTE_IN_SCAMBIO = "Offerte IN SCAMBIO";
	private static final String MSG_SHOW_OFFERTE_CHIUSE = "Offerte CHIUSE";
	private static final String MSG_SHOW_OFFERTE_APERTE = "Offerte APERTE";
	
	private static final String MSG_ID_NON_VALIDO = "\nL'id selezionato non fa riferimento a nessun'offerta aperta del fruitore";
	private static final String MSG_RICHIESTA_ID = "\nInserire l'id dell'offerta da selezionare: ";
	private static final String MSG_OFFERTE_BY_UTENTE_INESISTENTI = "\nNon sono presenti offerte a tuo nome:";
	private static final String MSG_OFFERTE_BY_CATEGORIA_INESISTENTI = "\nNon sono presenti %s per la categoria selezionata. \n";
	private static final String MSG_OFFERTE_RITIRABILI_INESISTENTI = "\nNon ci sono offerte da ritirare";
	private static final String MSG_OFFERTA_RITIRATA = "\nL'offerta e' stata ritirata con successo";
	private static final String MSG_OFFERTE_INESISTENTI = "\nNon sono presenti offerte con ID selezionato";
	
	//costanti per menu
	protected static final String TXT_ERROR = "ERRORE";
	private static final String TXT_TITOLO = "Gestisci Offerte";
		
	private static final String MSG_PUBBLICA_ARTICOLO = "Inserisci un articolo";
	private static final String MSG_RITIRA_OFFERTA = "Ritira un'offerta";
	private static final String MSG_OFFERTE_APERTE = "Visualizzare tutte le attuali Offerte aperte relative ad una categoria";
	private static final String MSG_OFFERTE_AUTORE = "Visualizzare tutte le tue Offerte";
	private static final String MSG_SCAMBIA_ARTICOLI = "Scambia Articoli";
	
	private static final String [] TXT_VOCI = {
			MSG_PUBBLICA_ARTICOLO,
			MSG_RITIRA_OFFERTA,
			MSG_OFFERTE_APERTE,
			MSG_OFFERTE_AUTORE,
			MSG_SCAMBIA_ARTICOLI			
	};	
	
	private GestioneOfferta gestoreOfferte;
	private GestioneFruitore gestoreFruitore;
	
	/**
	 * Postcondizione: gestoreOfferte != null
	 * @throws FileNotFoundException, IOException 
	 */
	public ViewOfferte() throws FileNotFoundException, IOException {
		try {
			gestoreOfferte = new GestioneOfferta();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(MSG_ERROR_FILE_INERENTI_IL_BARATTO_NON_TROVATI);
		} catch (IOException e) {
			throw new IOException(MSG_ERROR_FILE_INERENTI_IL_BARATTO);
		}
	}
	
	/**
	 * Precondizione: gestoreFruitore != null, gestoreOfferte != null
	 * Postcondizione: this.gestoreFruitore != null, this.gestoreOfferte != null
	 * 
	 * @param gestoreFruitore
	 * @param gestoreOfferte
	 */
	public ViewOfferte(GestioneFruitore gestoreFruitore, GestioneOfferta gestoreOfferte) {
		this.gestoreFruitore = gestoreFruitore;
		this.gestoreOfferte = gestoreOfferte;
	}
	
	public void menu() throws IOException {
		MyMenu menuOfferte = new MyMenu(TXT_TITOLO, TXT_VOCI);
		ViewBaratto viewScambio;
		
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuOfferte.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				ViewArticolo viewArticolo = new ViewArticolo(gestoreFruitore, gestoreOfferte);
				viewArticolo.aggiungiArticolo();
				break;
			case 2:
				ritiraOfferta();				
				break;
			case 3:
				ViewGerarchia viewGerarchia = new ViewGerarchia();
				Categoria foglia = viewGerarchia.scegliFoglia();
				showOfferteAperteByCategoria(foglia);		
				break;
			case 4:
				showOfferteByName();			
				break;
			case 5:
				viewScambio = new ViewBaratto(gestoreOfferte, gestoreFruitore);	
				viewScambio.menu();
				break;
			default:
				System.out.println(TXT_ERROR);
			}
		} while(!fine);
	}
	
	public Offerta getOffertaById(List<Offerta> listaOfferte) throws NullPointerException {
		try {
			showOfferte(listaOfferte); 
			
			String id = InputDati.leggiStringaNonVuota(MSG_RICHIESTA_ID);
			
			return gestoreOfferte.getOffertaById(id, listaOfferte);
		} catch (Exception e) {
			throw new NullPointerException(MSG_OFFERTE_INESISTENTI);
		}
	}
	
	/**
	 * Permette di ritirare un offerta selezionandola tramite l'id
	 * @throws IOException 
	 */
	public void ritiraOfferta() throws IOException {
		ArrayList<Offerta> listaOfferteAttiveByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteAttiveByUtente.size() > 0) {
			try {
				Offerta offerta = this.getOffertaById(listaOfferteAttiveByUtente);
				
				if(offerta != null) {
					try {
						gestoreOfferte.ritiraOfferta(offerta);;
					} catch (IOException e) {
						throw new IOException(MSG_ERROR_FILE_INERENTI_IL_BARATTO);
					}
					
					System.out.println(MSG_OFFERTA_RITIRATA);
				} else {
					System.out.println(MSG_ID_NON_VALIDO);
				}
				
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}	
		} else {
			System.out.println(MSG_OFFERTE_RITIRABILI_INESISTENTI);
		}
		
	}
	
	private void showOfferte(List<Offerta> listaOfferte) throws Exception{
		if(listaOfferte.size() > 0) {
			for (Offerta offerta : listaOfferte) {
				showOfferta(offerta);
			}
		} else
			throw new Exception();
	}
	
	/**
	 * Mostra se disponibili le offerte attive dopo aver scelto una Categoria Foglia.
	 * Se non presenti lo viene detto a video
	 * @param foglia
	 */
	public void showOfferteAperteByCategoria(Categoria foglia) { 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteAperteByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByCategoria(foglia);
			
			if(listaOfferteAperteByCategoria.size() > 0) {
				System.out.println(MSG_SHOW_OFFERTE_APERTE);
				for (Offerta offerta : listaOfferteAperteByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_APERTE);
		}
	}
	
	public void showOfferteChiuseByCategoria(Categoria foglia) { 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteChiuseByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteChiuseByCategoria(foglia);
			
			if(listaOfferteChiuseByCategoria.size() > 0) {
				System.out.println(MSG_SHOW_OFFERTE_CHIUSE);
				for (Offerta offerta : listaOfferteChiuseByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_CHIUSE);
		}
	}
	
	public void showOfferteInScambioByCategoria(Categoria foglia) { 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteInScambioByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteInScambioByCategoria(foglia);
			
			if(listaOfferteInScambioByCategoria.size() > 0) {
				System.out.println(MSG_SHOW_OFFERTE_IN_SCAMBIO);
				for (Offerta offerta : listaOfferteInScambioByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_IN_SCAMBIO);
		}
	}
	
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 */
	public void showOfferteByName() { 
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	public void showOfferteAperteByName() {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	public void showOfferteSelezionateByName() {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaSelezionateByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteSelezionateByUtente(username);
				
		if(listaSelezionateByUtente.size() > 0) {
			for (Offerta offerta : listaSelezionateByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	public void showOfferta(Offerta offerta) {
		StringBuffer sb = new StringBuffer();
		ViewArticolo viewArticolo = new ViewArticolo();
		sb.append("##############################\n");
		sb.append("Offerta ID: " + offerta.getId() + "\n"
				+ "->Stato Offerta: " + offerta.getStatoOfferta().getStato() + "\n"
				+ "->" );
		
		System.out.print(sb.toString());
		viewArticolo.showArticolo(offerta.getArticolo());
	}
}
