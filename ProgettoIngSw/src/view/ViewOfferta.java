package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Categoria;
import application.baratto.Offerta;
import controller.GestioneOfferta;
import it.unibs.fp.mylib.InputDati;

public class ViewOfferta {
	private static final String MSG_ERROR_RITIRO_OFFERTA = "Impossibile ritirare l'offerta.";
	private static final String MSG_SHOW_OFFERTE_IN_SCAMBIO = "Offerte IN SCAMBIO";
	private static final String MSG_SHOW_OFFERTE_CHIUSE = "Offerte CHIUSE";
	private static final String MSG_SHOW_OFFERTE_APERTE = "Offerte APERTE";
	
	private static final String MSG_ID_NON_VALIDO = "\nL'id selezionato non fa riferimento a nessun'offerta aperta del fruitore";
	private static final String MSG_RICHIESTA_ID = "\nInserire l'id dell'offerta da selezionare: ";
	private static final String MSG_OFFERTE_BY_UTENTE_INESISTENTI = "\nNon sono presenti offerte a tuo nome:";
	private static final String MSG_OFFERTE_BY_CATEGORIA_INESISTENTI = "\nNon sono presenti %s per la categoria selezionata. \n";
	private static final String MSG_OFFERTE_RITIRABILI_INESISTENTI = "\nNon ci sono offerte da ritirare";
	private static final String MSG_OFFERTA_RITIRATA = "\nL'offerta e' stata ritirata con successo";
	private static final String MSG_OFFERTE_INESISTENTI = "\nNon sono presenti offerte";
	private static final String MSG_OFFERTE_INESISTENTI_BY_ID = "\nNon sono presenti offerte con id selezionato";
	
	private GestioneOfferta gestoreOfferte;
	private String username;
	
	public ViewOfferta() throws FileNotFoundException, IOException {
		this.gestoreOfferte = new GestioneOfferta();
	}
	
	public ViewOfferta(String username, GestioneOfferta gestoreOfferta) {
		this.username = username;
		this.gestoreOfferte = gestoreOfferta;
	}
	
	public Offerta getOffertaById(List<Offerta> listaOfferte) throws NullPointerException, Exception {
		if(listaOfferte.size() == 0)
			throw new Exception(MSG_OFFERTE_INESISTENTI);
		
		try {
			showOfferte(listaOfferte); 
			
			String id = InputDati.leggiStringaNonVuota(MSG_RICHIESTA_ID);
			
			return gestoreOfferte.getOffertaById(id, listaOfferte);
		} catch (Exception e) {
			throw new NullPointerException(MSG_OFFERTE_INESISTENTI_BY_ID);
		}
	}
	
	/**
	 * Permette di ritirare un offerta selezionandola tramite l'id
	 * @throws IOException 
	 */
	public void ritiraOfferta() throws IOException {
		ArrayList<Offerta> listaOfferteAttiveByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByUtente(username);
		
		if(listaOfferteAttiveByUtente.size() > 0) {
			try {
				Offerta offerta = this.getOffertaById(listaOfferteAttiveByUtente);
				
				if(offerta != null) {
					try {
						gestoreOfferte.ritiraOfferta(offerta);;
					} catch (IOException e) {
						throw new IOException(MSG_ERROR_RITIRO_OFFERTA);
					}
					
					System.out.println(MSG_OFFERTA_RITIRATA);
				} else {
					System.out.println(MSG_ID_NON_VALIDO);
				}
				
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println(MSG_OFFERTE_RITIRABILI_INESISTENTI);
		}
		
	}
	
	private void showOfferte(List<Offerta> listaOfferte) throws Exception {
		if(listaOfferte.size() > 0) {
			for (Offerta offerta : listaOfferte) {
				showOfferta(offerta);
			}
		} else
			throw new Exception();
	}
	
	private void showOfferte(List<Offerta> listaOfferte, String msg) throws Exception{
		if(listaOfferte.size() > 0) {
			System.out.println(msg);
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
	 * @throws IOException 
	 */
	public void showOfferteAperteByCategoria(Categoria foglia) throws IOException { 
		if(foglia != null) {
			ArrayList<Offerta> listaOfferteAperteByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByCategoria(foglia);
			
			try {
				showOfferte(listaOfferteAperteByCategoria, MSG_SHOW_OFFERTE_APERTE);
			} catch (Exception e) {
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_APERTE);
			}
				
		}
	}
	
	public void showOfferteChiuseByCategoria(Categoria foglia) throws IOException { 
		if(foglia != null) {
			ArrayList<Offerta> listaOfferteChiuseByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteChiuseByCategoria(foglia);
			
			try {
				showOfferte(listaOfferteChiuseByCategoria, MSG_SHOW_OFFERTE_CHIUSE);
			} catch (Exception e) {
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_CHIUSE);
			}
				
		}
	}
	
	public void showOfferteInScambioByCategoria(Categoria foglia) throws IOException { 
		if(foglia != null) {
			ArrayList<Offerta> listaOfferteInScambioByCategoria = (ArrayList<Offerta>) gestoreOfferte.getOfferteInScambioByCategoria(foglia);
			
			try {
				showOfferte(listaOfferteInScambioByCategoria, MSG_SHOW_OFFERTE_IN_SCAMBIO);
			} catch (Exception e) {
				System.out.printf(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI, MSG_SHOW_OFFERTE_IN_SCAMBIO);
			}
		}
	}
	
	private void showOfferteByName(List<Offerta> listaOfferte) {
		try {
			showOfferte(listaOfferte);
		} catch (Exception e) {
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
		}
	}
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 */
	public void showAllOfferteByName() { 		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteByUtente(username);
			
		showOfferteByName(listaOfferteByUtente);
	}
	
	public void showOfferteAperteByName() {		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteAperteByUtente(username);
			
		showOfferteByName(listaOfferteByUtente);
	}
	
	public void showOfferteSelezionateByName() {		
		ArrayList<Offerta> listaSelezionateByUtente = (ArrayList<Offerta>) gestoreOfferte.getOfferteSelezionateByUtente(username);
			
		showOfferteByName(listaSelezionateByUtente);
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
