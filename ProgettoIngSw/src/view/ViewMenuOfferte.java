package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import application.Categoria;
import controller.GestioneOfferta;
import it.unibs.fp.mylib.MyMenu;

public class ViewMenuOfferte {

	private static final String MSG_ERROR_FILE_INERENTI_IL_BARATTO = "Impossibile interagire con i file inerenti il baratto.";
	private static final String MSG_ERROR_FILE_INERENTI_IL_BARATTO_NON_TROVATI = "Impossibile interagire con i file inerenti il baratto. File non trovati";
		
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
	private String username;
	
	/**
	 * Postcondizione: gestoreOfferte != null
	 * @throws FileNotFoundException, IOException 
	 */
	public ViewMenuOfferte() throws FileNotFoundException, IOException {
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
	public ViewMenuOfferte(String username, GestioneOfferta gestoreOfferte) {
		this.username = username;
		this.gestoreOfferte = gestoreOfferte;
	}
	
	public void menu() throws IOException {
		MyMenu menuOfferte = new MyMenu(TXT_TITOLO, TXT_VOCI);
		ViewBaratto viewBaratto;
		ViewOfferta viewOfferta;
		
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuOfferte.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				ViewArticolo viewArticolo = new ViewArticolo(username, gestoreOfferte);
				viewArticolo.aggiungiArticolo();
				break;
			case 2:
				viewOfferta = new ViewOfferta(username, gestoreOfferte);
				viewOfferta.ritiraOfferta();				
				break;
			case 3:
				ViewGerarchia viewGerarchia = new ViewGerarchia();
				Categoria foglia = viewGerarchia.scegliFoglia();
				
				viewOfferta = new ViewOfferta(username, gestoreOfferte);
				viewOfferta.showOfferteAperteByCategoria(foglia);		
				break;
			case 4:
				viewOfferta = new ViewOfferta(username, gestoreOfferte);
				viewOfferta.showOfferteByName();			
				break;
			case 5:
				viewBaratto = new ViewBaratto(gestoreOfferte, username);	
				try {
					viewBaratto.menu();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				System.out.println(TXT_ERROR);
			}
		} while(!fine);
	}
}
