package gestioneOfferte;

import java.util.ArrayList;
import gestioneCategorie.Categoria;
import gestioneCategorie.ViewGerarchia;
import gestioneUtenti.GestioneFruitore;
import it.unibs.fp.mylib.InputDati;

public class ViewOfferte {

	private static final String MSG_ID_NON_VALIDO = "\nL'id selezionato non fa riferimento a nessun'offerta aperta del fruitore";
	private static final String MSG_RICHIESTA_ID = "\nInserire l'id dell'offerta da ritirare: ";
	private static final String MSG_OFFERTE_BY_UTENTE_INESISTENTI = "\nNon sono presenti offerte a tuo nome:";
	private static final String MSG_OFFERTE_BY_CATEGORIA_INESISTENTI = "\nNon sono presenti offerte per la categoria selezionata.";
	private static final String MSG_OFFERTE_RITIRABILI_INESISTENTI = "\nNon ci sono offerte da ritirare";
	private static final String MSG_OFFERTA_RITIRATA = "\nL'offerta e' stata ritirata con successo";
	
	private GestioneOfferta gestoreOfferta;
	
	public ViewOfferte() {
		gestoreOfferta = new GestioneOfferta();
	}
	
	/**
	 * Permette di ritirare un offerta selezionandola tramite l'id
	 * @param gestoreFruitore
	 */
	public void ritiraOfferta(GestioneFruitore gestoreFruitore) {
		ArrayList<Offerta> listaOfferteAttiveByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByUtente(gestoreFruitore.getUsername());
		
		if(listaOfferteAttiveByUtente.size() > 0) {
			showOfferteAperteByName(gestoreFruitore);
			int id = InputDati.leggiInteroNonNegativo(MSG_RICHIESTA_ID);
			
			Offerta offerta = gestoreOfferta.getOffertaById(id, listaOfferteAttiveByUtente);
			
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
	
	/**
	 * Mostra se disponibili le offerte attive dopo aver scelto una Categoria Foglia.
	 * Se non presenti lo viene detto a video
	 */
	public void showOfferteAperteByCategoria() {
		ViewGerarchia viewGerarchia = new ViewGerarchia();
		Categoria foglia = viewGerarchia.scegliFoglia();
		 
		if(foglia != null) {
			
			ArrayList<Offerta> listaOfferteAttiveByCategoria = (ArrayList<Offerta>) gestoreOfferta.getOfferteAperteByCategoria(foglia);
			
			if(listaOfferteAttiveByCategoria.size() > 0) {
				for (Offerta offerta : listaOfferteAttiveByCategoria) {
					showOfferta(offerta);
				}
			} else
				System.out.println(MSG_OFFERTE_BY_CATEGORIA_INESISTENTI);
		}
	}
	
	/**
	 * Un fruitore può vedere tutte le sue offerte
	 * @param gestoreFruitore
	 */
	public void showOfferteByName(GestioneFruitore gestoreFruitore) {
		String username = gestoreFruitore.getUsername();
		
		ArrayList<Offerta> listaOfferteByUtente = (ArrayList<Offerta>) gestoreOfferta.getOfferteByUtente(username);
				
		if(listaOfferteByUtente.size() > 0) {
			for (Offerta offerta : listaOfferteByUtente) {
				showOfferta(offerta);
			}
		} else
			System.out.println(MSG_OFFERTE_BY_UTENTE_INESISTENTI + username);
	}
	
	public void showOfferteAperteByName(GestioneFruitore gestoreFruitore) {
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
		
//		sb.append("Offerta di " + offerta.getUsername() +".\n"
//				+"->ID: " + offerta.getId() + "\n"
//				+ "->Stato Offerta: " + offerta.getTipoOfferta().getStato() + "\n"
//				+ "->" );
		
		System.out.print(sb.toString());
		viewArticolo.showArticolo(offerta.getArticolo());
	}
}
