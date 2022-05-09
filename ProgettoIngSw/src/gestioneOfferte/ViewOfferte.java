package gestioneOfferte;

import java.util.ArrayList;
import java.util.List;

import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneScambioArticoli.ViewScambio;
import gestioneUtenti.GestioneFruitore;
import gestioneUtenti.GestioneUtente;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class ViewOfferte {

	//costanti per menu
	protected static final String TXT_ERRORE = "ERRORE";
	private static final String TXT_TITOLO = "Gestisci Offerte";
		
	private static final String MSG_RITIRA_OFFERTA = "Ritira un'offerta";
	private static final String MSG_OFFERTE_APERTE = "Visualizzare tutte le attuali Offerte aperte relative ad una categoria";
	private static final String MSG_OFFERTE_AUTORE = "Visualizzare tutte le proprie Offerte aperte e ritirate ";
	private static final String MSG_SCAMBIA_ARTICOLI = "Scambia Articoli";
	
	private static final String [] TXT_VOCI = {
			MSG_RITIRA_OFFERTA,
			MSG_OFFERTE_APERTE,
			MSG_OFFERTE_AUTORE,
			MSG_SCAMBIA_ARTICOLI			
	};
	
	private static final String MSG_ID_NON_VALIDO = "\nL'id selezionato non fa riferimento a nessun'offerta aperta del fruitore";
	private static final String MSG_RICHIESTA_ID = "\nInserire l'id dell'offerta da selezionare: ";
	private static final String MSG_OFFERTE_BY_UTENTE_INESISTENTI = "\nNon sono presenti offerte a tuo nome:";
	private static final String MSG_OFFERTE_BY_CATEGORIA_INESISTENTI = "\nNon sono presenti offerte per la categoria selezionata.";
	private static final String MSG_OFFERTE_RITIRABILI_INESISTENTI = "\nNon ci sono offerte da ritirare";
	private static final String MSG_OFFERTA_RITIRATA = "\nL'offerta e' stata ritirata con successo";
	private static final String MSG_OFFERTE_INESISTENTI = "\nNon sono presenti offerte";
	
	
	private GestioneOfferta gestoreOfferta;
	private GestioneFruitore gestoreFruitore;
	
	public ViewOfferte() {
		gestoreOfferta = new GestioneOfferta();
	}
	public ViewOfferte(GestioneFruitore gestoreFruitore) {
		this.gestoreFruitore = gestoreFruitore;
		gestoreOfferta = new GestioneOfferta();
	}
	
	public void menu() {
		MyMenu menuOfferte = new MyMenu(TXT_TITOLO, TXT_VOCI);
		ViewScambio viewScambio;
		
		int scelta = 0;
		boolean fine = false;
		do {
			scelta = menuOfferte.scegli();
			switch(scelta) {
			case 0:
				fine = true;
				break;
			case 1:
				ritiraOfferta();				
				break;
			case 2:
				ViewGerarchia viewGerarchia = new ViewGerarchia();
				Categoria foglia = viewGerarchia.scegliFoglia();
				showOfferteAperteByCategoria(foglia);		
				break;
			case 3:
				showOfferteByName();			
				break;
			case 4:
				viewScambio = new ViewScambio(gestoreOfferta, gestoreFruitore);	
				viewScambio.menu();
				break;
			default:
				System.out.println(TXT_ERRORE);
			}
		} while(!fine);
	}
	
	public Offerta getOffertaById(List<Offerta> listaOfferte) {
		showOfferte(listaOfferte); //TODO: da mettere fuori questo show
		int id = InputDati.leggiInteroNonNegativo(MSG_RICHIESTA_ID);
		
		 return gestoreOfferta.getOffertaById(id, listaOfferte);
	}
	
	/**
	 * Permette di ritirare un offerta selezionandola tramite l'id
	 */
	public void ritiraOfferta() {
		ArrayList<Offerta> listaOfferteAttiveByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteAttiveByUtente.size() > 0) {
			
			Offerta offerta = this.getOffertaById(listaOfferteAttiveByUtente);
			
			if(offerta != null) {
				gestoreOfferta.gestisciCambiamentoStatoOfferta(offerta);
				System.out.println(MSG_OFFERTA_RITIRATA);
			} else {
				System.out.println(MSG_ID_NON_VALIDO);
			}
		} else {
			System.out.println(MSG_OFFERTE_RITIRABILI_INESISTENTI);
		}
		
	}
	
	public void showOfferte(List<Offerta> listaOfferte) {
		if(listaOfferte.size() > 0) {
			for (Offerta offerta : listaOfferte) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_INESISTENTI);
	}
	
	/**
	 * Mostra se disponibili le offerte attive dopo aver scelto una Categoria Foglia.
	 * Se non presenti lo viene detto a video
	 */
	public void showOfferteAperteByCategoria(Categoria foglia) { 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteAperteByCategoria = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByCategoria(foglia);
			
			if(listaOfferteAperteByCategoria.size() > 0) {
				for (Offerta offerta : listaOfferteAperteByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.println(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI);
		}
	}
	
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 */
	public void showOfferteByName() { 
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	public void showOfferteAperteByName() {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	protected void showOfferta(Offerta offerta) {
		StringBuffer sb = new StringBuffer();
		ViewArticolo viewArticolo = new ViewArticolo();
		
		sb.append("Offerta ID: " + offerta.getId() + "\n"
				+"->Autore: " + offerta.getUsername() + "\n"
				+ "->Stato Offerta: " + offerta.getTipoOfferta().getStato() + "\n"
				+ "->" );
		
		System.out.print(sb.toString());
		viewArticolo.showArticolo(offerta.getArticolo());
	}
}
